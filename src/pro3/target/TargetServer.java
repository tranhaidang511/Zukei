package pro3.target;
import java.awt.image.*;
import java.io.*;
import java.net.*;
import java.util.*;
import pro3.shape.Attribute;
import pro3.shape.ShapeManager;
import pro3.shape.OrderedShapeManager;

/** 
 *  socket通信を受け付けて内包するTargetで出力する
 */
public class TargetServer implements Target{
  /** ソケット通信で利用する受付ポート番号 */
  public static final int PORTNO = 30000;
  /** 実際に出力に利用するTarget */
  protected final Target targetimpl;
  /** 通信中のソケット一覧 */
  protected HashSet<SocketThread> setst;
  /** 図形集合の対応表 */
  protected HashMap<ShapeManager, ShapeManager> mapsmsm;
  /** 複数の受信先から受信した図形とサーバ独自の図形を統合した図形集合 */
  protected final ShapeManager smimpl;
  /** 受信によるものでなく、サーバ側で独自に描画をするときの利用する図形集合 */
  protected final ShapeManager smlocal;
  /** 着信受付用ソケット */
  protected final ServerSocket ss;

  /** 
   * 通信を受け付けて、その内容を{@link pro3.target.Target Target}で出力するサーバを生成する。<br>
   * 受付サーバの初期化まで行う
   * @param target 実際の出力先<br>
   */
  public TargetServer(Target target) throws IOException{
    this(target, new OrderedShapeManager());
  }

  /** 
   * 通信を受け付けて{@link pro3.target.Target Target}に内容を出力するサーバを生成する。
   * 受付サーバの初期化まで行う<br>
   * 内部で標準に使う{@link pro3.shape.ShapeManager ShapeManager}は
{@link pro3.shape.OrderedShapeManager OrderedShapeManager}だが、それ以外を使用する場合に用いる
   * @param target 実際の出力先
   * @param shapemanager 内部で利用する{@link pro3.shape.ShapeManager ShapeManager}
   */
  public TargetServer(Target target, ShapeManager shapemanager) 
         throws IOException{
    targetimpl = target;
    smimpl = shapemanager;
    smlocal = new ShapeManager();
    setst = new HashSet<SocketThread>();
    mapsmsm = new HashMap<ShapeManager, ShapeManager>();
    ss = new ServerSocket(PORTNO);
  }

  /** 
   * 受信待機を開始する
   */
  public void welcome(){
    while(true){
      Socket s = null;
      try {
        s = ss.accept();
        SocketThread st = new SocketThread(s, this);
        add(st);
        st.start();
      }catch(IOException e){
        try {
          if(s!=null){
            s.close();
          }
        }catch(IOException e2){
        }
      } 
    }
  }

  /** 
   * 表にソケットスレッドを追加する<br>
   * 通信を開始したソケットスレッドを新たに表に加える時に呼び出す
   * @param st 通信中のソケットスレッド
   */
  synchronized void add(SocketThread st){
    setst.add(st);
    mapsmsm.put(st.sm, new ShapeManager()); 
  }

  /** 
   * ソケットスレッドを表から削除する<br>
   * 通信終了のソケットスレッドを表から削除する時に呼び出す
   * @param st 通信終了したソケットスレッド
   */
  synchronized void remove(SocketThread st){
    setst.remove(st);
    mapsmsm.remove(st.sm);
    flush();
  }
  
  /** 図形集合を空にする
   *  @param sm 図形集合
   */
  @Override
  public void clear(ShapeManager sm){
    sm.clear();
  }

  /** 図形集合の内容を描画する
   *  @param sm 図形集合
   */
  @Override
  public synchronized void draw(ShapeManager sm){
    ShapeManager smcopy = mapsmsm.get(sm);
    if(smcopy != null){
      smcopy.clear();
      smcopy.copy(sm);
    }else{
      smlocal.clear(); 
      smlocal.copy(sm); 
    }
  }

  @Override
  public synchronized void draw(Target t, ShapeManager sm){
    predraw();
    t.drawCore(sm);
    postdraw();
  }

  @Override
  public void drawCore(final ShapeManager sm){
    draw(sm);
  }

  private void predraw(){
  }

  private void postdraw(){
  }

  @Override
  /**
   {@link this.#targetimpl}で定義されている描画対象領域の {@link BufferedImage}を返す
   */
  public BufferedImage getBufferedImage(){
    return targetimpl.getBufferedImage();
  }

  @Override
  public synchronized BufferedImage copyBufferedImage(){
    return targetimpl.copyBufferedImage();
  }
  @Override
  public synchronized BufferedImage copyBufferedImage3Channel(){
    return targetimpl.copyBufferedImage3Channel();
  }
  @Override
  public void finish(){
    targetimpl.finish();
  }

  /** 図形集合の内容を実際に装置に転写する
   */
  @Override
  public synchronized void flush(){
    smimpl.clear();
    smimpl.merge(smlocal);
    Set<ShapeManager>keys = mapsmsm.keySet();
    for(ShapeManager key: keys){
      smimpl.merge(mapsmsm.get(key));
    }
    targetimpl.draw(smimpl);
    targetimpl.flush();
  }
  
  @Override
  public void drawCircle(int id,int x, int y, int r, Attribute attr){
  }

  @Override
  public void drawRectangle(int id,int x0,int y0,int x1,int y1, 
                            Attribute attr){
  }
  @Override
  public void drawImage(int id,int x,int y,BufferedImage img,
                            Attribute attr){
  }
  @Override
  public void drawCamera(int id,int x,int y, Attribute attr){
  }

}
