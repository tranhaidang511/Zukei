package pro3.shape;
import pro3.target.Target;

/** 
 * 矩形
 */
public class Rectangle extends Shape{
  /** 属性 */
  private Attribute attr;
  /** 左上隅のx座標 */
  private int x;
  /** 左上隅のy座標 */
  private int y;
  /** 幅 */
  private int w;
  /** 高さ */
  private int h;

  /** 矩形を生成する．
   *  @param id 識別子
   *  @param x  左上隅のx座標
   *  @param y  左上隅のy座標
   *  @param w  幅
   *  @param h  高さ
   */
  public Rectangle(int id, int x, int y, int w, int h){
    super(x,x+w-1,y,y+h-1);
    this.id = id;
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
  }
  
  /** 属性を設定する．
   *  @param a 属性
   */
  @Override
  public void setAttribute(Attribute a){
    attr=a;
  }

  /**
   * 属性を取得する
   */
  @Override
  public Attribute getAttribute(){
    return attr;
  }

  /** この矩形を出力する．
   *  @param target 出力先
   */
  @Override
  public void draw(Target target){
    target.drawRectangle(id, x, y, w, h, attr);
  }
}
