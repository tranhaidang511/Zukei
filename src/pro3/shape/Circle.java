package pro3.shape;
import pro3.target.Target;

/** 
 * 円
 */
public class Circle extends Shape{
  /** 中心のx座標 */
  private int x;
  /** 中心のy座標 */
  private int y;
  /** 半径 */
  private int r;
  /** 属性 */
  private Attribute attr;

  /** 円を生成する
   *  @param id 識別子
   *  @param x  中心のx座標
   *  @param y  中心のy座標
   *  @param r  半径
   */
  public Circle(int id, int x, int y, int r){
    super(x-r,x+r,y-r,y+r);
    this.id=id;
    this.x =x; 
    this.y = y;
    this.r = r;
  }

  /** 属性を設定する
   *  @param a 属性
   */
  @Override
  public void setAttribute(Attribute a){
    attr = a;
  }

  /** 属性を取得する */
  @Override
  public Attribute getAttribute(){
    return attr;
  }

  /** この円を出力する
   *  @param target 出力先
   */
  @Override
  public void draw(Target target){
    target.drawCircle(id, x, y, r, attr);
  }
}
