package pro3.shape;
import pro3.target.Target;

/** 
 * 図形の抽象クラス
 */
abstract public class Shape{
  /** 識別子 */
  protected int id;
  protected int left,right,top,bottom;
  protected Shape(int l, int r, int t, int b){
    left = l; 
    right = r;
    top = t;
    bottom =b;
  }

  /** 識別子を設定する
   *  @param id 識別子
   */
   void setID(int id){
    this.id = id;
  }

  /** 属性を設定する
   *  @param attr 属性
   */
  abstract public void setAttribute(Attribute attr);

  /** 
   *  属性を取得する
   */
  abstract public Attribute getAttribute();

  /** 図形を出力する
   *  @param target 出力装置
   */
  abstract public void draw(Target target);
}
