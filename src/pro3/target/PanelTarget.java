package pro3.target;
import java.awt.*; 
import java.awt.image.*;
import javax.swing.*;

import pro3.shape.Attribute;
import pro3.shape.ShapeManager;
import pro3.shape.Camera;

/** 
 *  出力表示パネル
 */
public class PanelTarget extends JPanel implements Target{
  /** 描画操作に必要な情報を保持するオブジェクト（AWT で提供）*/
  private Graphics graphics;
  /** 描画用に確保された領域 */
  final BufferedImage image;
  /** パネルの幅 */
  final int width;
  /** パネルの高さ */
  final int height; 
  protected SingleCamera camdev;
  protected BufferedImage camimage;

  /** 
   *  出力表示パネルを生成する 
   *  @param width 幅
   *  @param height 高さ
   */
  public PanelTarget(int width, int height){
    this.width=width;
    this.height=height;
    image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    graphics = image.getGraphics();
    graphics.setColor(new Color(255,255,255));
    graphics.fillRect(0, 0, width, height);
    setPreferredSize(new Dimension(width,height));
    setBackground(new Color(0,0,0));
    camdev = SingleCamera.create(Camera.WIDTH, Camera.HEIGHT);
    camimage = camdev.createBufferedImage(); 
  }
  
  @Override
  public synchronized void paintComponent(Graphics g){
    super.paintComponent(g);
    g.drawImage(image, 0, 0, null);
  }

  /** 図形集合の内容を削除
   *  @param sm 図形集合
   */
  @Override
  public synchronized void clear(final ShapeManager sm){
    sm.clear();
  }

  @Override
  public synchronized void draw(final ShapeManager sm){
  /* What is happened, when ``synchronized'' is not used */ 
    predraw();
    sm.draw(this);
    postdraw();
  }

  public synchronized void draw(final Target t, final ShapeManager sm){
  /* What is happened, when ``synchronized'' is not used */ 
    predraw();
    t.drawCore(sm);
    postdraw();
  }

  public void drawCore(final ShapeManager sm){
    draw(sm);
  }

  private void predraw(){
    Color c = graphics.getColor();
    graphics.setColor(new Color(255,255,255,255));
    graphics.fillRect(0, 0, width, height);
    graphics.setColor(c);
  }

  private void postdraw(){
  }

  @Override
  /**
   描画領域の{@link BufferedImage}を返す。スレッドセーフではなく一般利用は非推奨
   @return 描画領域の{@link BufferedImage}
   */
  public BufferedImage getBufferedImage(){
    return image;
  }

  @Override
  /**
   描画領域の {@link BufferedImage} をコピーして返す
   @return 描画領域の{@link BufferedImage}のコピー
  */
  public synchronized BufferedImage copyBufferedImage(){
    return new BufferedImage(image.getColorModel(),
                             (WritableRaster)image.getData(),
                             image.isAlphaPremultiplied(),null);
  }
  @Override
  public synchronized BufferedImage copyBufferedImage3Channel(){
    BufferedImage image = new BufferedImage(width, height, 
                                            BufferedImage.TYPE_3BYTE_BGR);
    Graphics graphics = image.getGraphics();
    graphics.drawImage(this.image,0,0,this);  
    return image;
  }
  @Override
  public void finish(){
  }

  @Override
  public void flush(){
    repaint();
  }

  /** 
   *  色を設定する
   */
  private void setColor(Attribute attr){
    if(attr!=null){
      int c[]=attr.getColor();
      graphics.setColor(new Color(c[0],c[1],c[2]));
    }
  }

  /** 
   *  円を描画する
   */
  @Override
  public void drawCircle(int id,int x, int y, int r, Attribute attr){
    setColor(attr);
    if(attr!=null && attr.getFill()){
      graphics.fillArc(x-r,y-r,r*2,r*2,0,360);
    }else{
      graphics.drawArc(x-r,y-r,r*2,r*2,0,360);
    }

  }

  /** 
   *  矩形を描画する
   */
  @Override
  public void drawRectangle(int id,int x0,int y0,int x1,int y1, 
      Attribute attr){
    setColor(attr);
    if(attr != null && attr.getFill()){
      graphics.fillRect(x0, y0, x1, y1); 
    }else{
      graphics.drawRect(x0, y0, x1, y1);
    }
  }

  /** 
   *  画像図形を描画する
   */
  @Override
  public void drawImage(int id, int x, int y, BufferedImage img, 
                        Attribute attr){
    setColor(attr);
    if(attr != null){
      if(attr.getFill()){
        graphics.fillRect(x, y, img.getWidth(), img.getHeight()); 
        graphics.drawImage(img,x,y,this);
        graphics.drawRect(x, y, img.getWidth(), img.getHeight()); 
      }else{
        graphics.drawImage(img,x,y,this);
        graphics.drawRect(x, y, img.getWidth(), img.getHeight()); 
      }
    }else{
      graphics.drawImage(img,x,y,this);
    } 
  }

  /** 
   *  カメラの画像図形を描画する
   */
  public void drawCamera(int id, int x, int y, Attribute attr){
    camdev.get(camimage);
    drawImage(id, x, y, camimage, attr);
  }

}
