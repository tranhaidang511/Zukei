package pro3.target;
import pro3.shape.Attribute;
import pro3.shape.ShapeManager;
import pro3.shape.Camera;
import java.awt.image.*;
import java.util.Timer;
import java.util.TimerTask;
import com.googlecode.javacv.FrameRecorder;
import com.googlecode.javacv.OpenCVFrameRecorder;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import static com.googlecode.javacv.cpp.opencv_highgui.*;

/** ウィンドウに出力する図形をファイルに録画する
 */
public class TargetRecorder implements Target{
  /** 録画時に利用する一コマ用のバッファ */
  protected BufferedImage oneframe;
  protected FrameRecorder recorder;
  /** 録画像の幅 */
  protected final int width;
  /** 録画像の高さ */
  protected final int height;
  /** 録画対象の装置 */
  protected Target target;
  /** 録画時の形式変換で使う一コマ画像用のデータ */
  protected IplImage iplimage;
  /** 一秒あたりの記録枚数 */
  private final double FRAMERATE=10;
  /** 一定間隔で処理を行うタイマー */
  private Timer timer;
  /** マルチスレッドで録画を正常に終了させるためのフラグ */
  private volatile boolean isRecordable = false;
  
  /** ウィンドウに出力すると同時にファイルに録画を開始する。<br>
   * プログラム終了時に確実に録画終了処理を行うように、仮想マシンに終了処理を登録することも行う。
   * 録画像の幅、高さは録画対象の装置の大きさにより自動決定される。
   * 録画は一秒間に10コマで行われる。
   * @param filename 録画用ファイル名 後ろに自動的に".avi"が追加される
   * @param target  録画対象となる画像出力装置 
   * @see java.lang.Runtime#addShutdownHook
   */
  public TargetRecorder(String filename, Target target){
    BufferedImage tmpimage = target.getBufferedImage();
    width = tmpimage.getWidth();
    height = tmpimage.getHeight();
    this.target = target;
    recorder = null;
    try {
      recorder = FrameRecorder.createDefault(filename+".avi", width, height);
      //      recorder.setCodecID(CV_FOURCC('M', 'J', 'P', 'G'));
      //      recorder.setCodecID(CV_FOURCC('D', 'I', 'V', 'X'));
      recorder.setFrameRate(FRAMERATE);
      //      recorder.setPixelFormat(1);
      recorder.start();
      isRecordable = true;
    }catch(com.googlecode.javacv.FrameRecorder.Exception e){
      System.err.println(e);
    }
    iplimage = IplImage.create(width,height,8,3);
    timer = new Timer();
    timer.scheduleAtFixedRate(new Shot(), 100, (long)((1.0/FRAMERATE)*1000));
    setShutdownHookThread();
  }

  /** インスタンスが不要になったときに録画関係の終了処理を行う
   */
  protected void finalize(){
    if(recorder!=null){
      try {
        synchronized(recorder){
          timer.cancel();
          if(isRecordable){
            isRecordable = false;
            recorder.stop();
          }
        }
      }catch(com.googlecode.javacv.FrameRecorder.Exception e){
      }
    }
  }

  /** プログラムが終了時に、確実に録画関係の終了処理を行うための設定を行う。
   * プログラムが終了するとき、仕様ではfinalizeが呼ばれない場合がある。
   * 例えばウィンドウの閉じるボタンを押した場合はその時使われているインスタンスのfinalizeは呼ばれることなく、処理を中断させられてプログラムが終了する。
   * 確実に終了処理を行うためには、{@link Runtime#addShutdownHook Runtime#addShutdownHook}を利用して、終了処理を登録しておく必要がある。このメソッドではその登録を行う。
   */
  private void setShutdownHookThread(){
    Runtime.getRuntime().addShutdownHook(
      new Thread(new Runnable(){ public void run(){
        if(recorder!=null){
          try {
            synchronized(recorder){
              timer.cancel();
              if(isRecordable){
                isRecordable = false;
                recorder.stop();
              }
            }
          }catch(com.googlecode.javacv.FrameRecorder.Exception e){
          }
        }
      }
    }));
  }

  @Override
  public void clear(ShapeManager sm){
    target.clear(sm);
  }

  @Override
  public void draw(ShapeManager sm){
    target.draw(sm);
  }

  @Override
  public void draw(Target t, ShapeManager sm){
    predraw();
    t.drawCore(sm);
    postdraw();
  }

  @Override
  public void drawCore(ShapeManager sm){
    draw(sm);
  }

  private void predraw(){
  }
  private void postdraw(){
  }

  @Override
  /**
   描画領域の{@link BufferedImage}を返す。スレッドセーフではなく一般利用は非推奨
   @return 描画領域の{@link BufferedImage}
   */
  public BufferedImage getBufferedImage(){
    return target.getBufferedImage();
  }

  @Override
  /**
   描画領域の {@link BufferedImage} をコピーして返す
   @return 描画領域の{@link BufferedImage}のコピー
  */
  public BufferedImage copyBufferedImage(){
    return target.copyBufferedImage();
  }
  @Override
  public synchronized BufferedImage copyBufferedImage3Channel(){
    return target.copyBufferedImage3Channel();
  }

  @Override
  public void finish(){
    target.finish();
  }

  @Override
  public void flush(){
    target.flush();
  }

  @Override
  public void drawCircle(int id,int x, int y, int r, Attribute attr){
    target.drawCircle(id, x, y, r, attr);
  }

  @Override
  public void drawRectangle(int id, int x1, int y1, int x2, int y2, Attribute attr){
    target.drawRectangle(id, x1, y1, x2, y2, attr);
  }

  @Override
  public void drawImage(int id, int x1, int y1, BufferedImage img, Attribute attr){
    target.drawImage(id, x1, y1, img, attr);
  }

  @Override
  public void drawCamera(int id, int x, int y, Attribute attr){
    target.drawCamera(id, x, y, attr);
  }

  class Shot extends TimerTask{
    public void run(){
      BufferedImage image = target.copyBufferedImage3Channel();
      iplimage.copyFrom(image);
      try {
        synchronized(recorder){
          if(isRecordable){
            recorder.record(iplimage);
          }
        }
      }catch(com.googlecode.javacv.FrameRecorder.Exception e){
        System.err.print(e);
      }
    }
  }
}