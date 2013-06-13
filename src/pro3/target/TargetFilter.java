package pro3.target;
import pro3.shape.*;
import java.nio.*;
import java.awt.image.*;
import java.awt.*;

abstract public class TargetFilter implements Target{
  protected Target target;
  private SingleCamera camdev;
  private BufferedImage outimage;
  private ByteBuffer captureBuffer; //this is used for different definition 
                                    //between MacOS and other OS
  /** {@link Camera}の像に画像処理を加えるための枠組みを提供する抽象クラス
   */
  public TargetFilter(Target target){
    this.target = target;
    camdev = SingleCamera.create(Camera.WIDTH, Camera.HEIGHT);
    camdev.start();
    outimage = new BufferedImage(Camera.WIDTH, Camera.HEIGHT,
                                 BufferedImage.TYPE_INT_ARGB); 
    captureBuffer = ByteBuffer.allocateDirect(Camera.WIDTH*Camera.HEIGHT*3);
  }

  @Override
  public synchronized void clear(ShapeManager sm){
    target.clear(sm);
  }
  @Override
  public synchronized void draw(ShapeManager sm){
    predraw();
    target.draw(this,sm);
    postdraw();
  }

  @Override
  public synchronized void draw(Target t,ShapeManager sm){
    predraw();
    t.drawCore(sm);
    postdraw();
  }

  @Override
  public void drawCore(ShapeManager sm){
    sm.draw(this);
  }

  private void predraw(){
  }
  private void postdraw(){
  }

  @Override
  /**
   フィルタ対象の{@link Target}の描画領域の{@link BufferedImage}を返す。スレッドセーフではなく一般利用は非推奨
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
  public synchronized BufferedImage copyBufferedImage(){
    BufferedImage image = target.getBufferedImage();
    return new BufferedImage(image.getColorModel(),
                             (WritableRaster)image.getData(),
                             image.isAlphaPremultiplied(),null);
  }
  @Override
  public synchronized BufferedImage copyBufferedImage3Channel(){
    BufferedImage tmpimage = target.getBufferedImage();
    BufferedImage image = new BufferedImage(tmpimage.getWidth(), 
                                            tmpimage.getHeight(), 
                                            BufferedImage.TYPE_3BYTE_BGR);
    Graphics graphics = image.getGraphics();
    graphics.drawImage(tmpimage,0,0,null);
    return image;
  }
  public void finish(){
    target.finish();
  }
  public synchronized void flush(){
    target.flush();
  }
  public void drawCircle(int id,int x, int y, int r, Attribute attr){
    target.drawCircle(id, x, y, r, attr);
  }
  public void drawRectangle(int id, int x1, int y1, int x2, int y2, Attribute attr){
    target.drawRectangle(id, x1, y1, x2, y2, attr);
  }
  public void drawImage(int id, int x1, int y1, BufferedImage img, Attribute attr){
    target.drawImage(id, x1, y1, img, attr);
  }

  public void drawCamera(int id, int x1, int y1, Attribute attr){
    //the fastest way is the next line, but incompatible between OS
    //  ByteBuffer shot = getShot(); 
    //  if(shot == null){
    //    target.drawImage(id, x1, y1, outimage, attr);
    //    return;
    //  }
    //  ByteBuffer processedbuffer = process(shot); 

    // the following is slower than the above, but compatible for various OS
    getShot(captureBuffer);  
    ByteBuffer processedbuffer = process(captureBuffer);

    processedbuffer.rewind();
    IntBuffer ibf = processedbuffer.asIntBuffer();
    DataBufferInt db = (DataBufferInt) outimage.getRaster().getDataBuffer();
    ibf.get(db.getData());
    target.drawImage(id, x1, y1, outimage, attr);    
  }

  public ByteBuffer createCameraBuffer(){
    return ByteBuffer.allocateDirect(Camera.WIDTH*Camera.HEIGHT*3);
  }

  public ByteBuffer createOutputBuffer(){
    return ByteBuffer.allocateDirect(Camera.WIDTH*Camera.HEIGHT*4);
  }

  public ByteBuffer getShot(){
    return camdev.get();
  }

  public void getShot(ByteBuffer data){
    camdev.get(data);
  }

  /** 表示をする度に呼び出され、カメラで撮影した画像に処理を加える。
   */
  abstract protected ByteBuffer process(ByteBuffer input);
}