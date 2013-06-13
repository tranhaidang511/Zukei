package pro3.target;
import java.awt.*; 
import java.awt.image.*;
import java.awt.event.*; 
import javax.swing.*;

import pro3.shape.Attribute;
import pro3.shape.ShapeManager;

/** 
 *  出力表示ウィンドウ 
 */
public class WindowTarget extends JFrame implements Target{
  /** 描画対象領域 */
  PanelTarget panel;

  /** 出力表示ウィンドウを生成する．
   *  @param s ウィンドウのタイトル
   *  @param width  ウィンドウの幅
   *  @param height ウィンドウの高さ
   */
  public WindowTarget(String s, int width,  int height){
    super(s);
    panel = new PanelTarget(width,height);
    add(panel);
    pack();
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        setBackground(new Color(0,0,0));
        setVisible(true);
      }
    });
  }

  /** 出力表示ウィンドウを生成する．
   *  @param s ウィンドウのタイトル
   */
  public WindowTarget(String s){
    this(s,320,240);
  }
  
  /** 図形集合の内容を削除
   *  @param sm 図形集合
   */
  @Override
  public void clear(ShapeManager sm){
    panel.clear(sm);
  }

 /** 描画領域のバッファに描画する
  *  @param sm 図形集合
  */
  @Override
  public void draw(ShapeManager sm){
    predraw();
    panel.draw(sm);
    postdraw();
  }

  @Override
  public void draw(Target t, ShapeManager sm){
    predraw();
    panel.draw(t,sm);
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
   描画領域の{@link BufferedImage}を返す。スレッドセーフではなく一般利用は非推奨
   @return 描画領域の{@link BufferedImage}
   */
  public BufferedImage getBufferedImage(){
    return panel.image;
  }

  @Override
  /**
   描画領域の {@link BufferedImage} をコピーして返す
   @return 描画領域の{@link BufferedImage}のコピー
  */
  public synchronized BufferedImage copyBufferedImage(){
    return new BufferedImage(panel.image.getColorModel(),
                             (WritableRaster)panel.image.getData(),
                             panel.image.isAlphaPremultiplied(),null);
  }
  @Override
  public synchronized BufferedImage copyBufferedImage3Channel(){
    BufferedImage image = new BufferedImage(panel.width, panel.height, 
                                            BufferedImage.TYPE_3BYTE_BGR);
    Graphics graphics = image.getGraphics();
    graphics.drawImage(panel.image,0,0,this);  
    return image;
  }

  /** 終了作業としてウィンドウを閉じる */
  @Override
  public void finish(){
    Toolkit.getDefaultToolkit().getSystemEventQueue()
      .postEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
  }

  /**
   *  描画領域を強制的に再描画する
   */
  @Override
  public void flush(){
    panel.flush();
  }
  
  /** 
   *  円を描画する
   */
  @Override
  public void drawCircle(int id,int x, int y, int r, Attribute attr){
    panel.drawCircle(id, x, y, r, attr);
  }

  /** 
   *  矩形を描画する
   */
  @Override
  public void drawRectangle(int id,int x,int y,int w,int h, 
      Attribute attr){
    panel.drawRectangle(id, x, y, w, h, attr);
  }

  /** 
   *  画像図形を描画する
   */
  @Override
  public void drawImage(int id,int x,int y, BufferedImage img,
      Attribute attr){
    panel.drawImage(id, x, y, img, attr);
  }

  @Override
  public void drawCamera(int id,int x,int y, Attribute attr){
    panel.drawCamera(id, x, y, attr);
  }
}
