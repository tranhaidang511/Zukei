package pro3.shape;

/**
 * 図形の属性
 */
public class Attribute{
  /** 色の赤緑青情報 */
  private int[] color;
  /** 塗りつぶすかどうかのフラグ */
  private boolean fill;

  /** 属性を生成する
   */
  public Attribute(){
    color = new int[3];
    fill =  true;
  }

  /** 指定色を範囲内に収めて設定する 
   *  @param r 赤成分
   *  @param g 緑成分
   *  @param b 青成分
   */
  public Attribute setColor(int r, int g, int b){
    color[0]= r<0?0:(255<r?255:r);
    color[1]= g<0?0:(255<g?255:g);
    color[2]= b<0?0:(255<b?255:b);
    return this;
  }

  /** 塗りつぶしフラグを設定する
   */
  public Attribute setFill(boolean fill){
    this.fill = fill;
    return this;
  }

  /** 色を取得する */
  public int[] getColor(){
    return color.clone();
  }

  /** 塗りつぶしフラグを取得する */
  public boolean getFill(){
    return fill;
  }

}
