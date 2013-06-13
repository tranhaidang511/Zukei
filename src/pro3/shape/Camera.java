package pro3.shape;

import pro3.target.Target;

/** カメラで撮影された画像の矩形図形
 */
public class Camera extends Shape{
  public static final int WIDTH=320;
  public static final int HEIGHT=240;
  private int x,y;
  private Attribute attr;
  public Camera(int id, int x, int y){
    super(x,x+WIDTH,y,y+HEIGHT);
    this.id=id;
    this.x =x; 
    this.y = y;
  }

  @Override
  public void setAttribute(Attribute a){
    attr = a;
  }

  @Override
  public Attribute getAttribute(){
    return attr;
  }

/** カメラ像を出力する
 */
  @Override
  public void draw(Target target){
    target.drawCamera(id, x, y, attr);
  }
}
