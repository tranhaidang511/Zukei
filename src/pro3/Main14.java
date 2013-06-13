package pro3;

import pro3.target.*;
import pro3.shape.*;

/** 引数に合わせてカメラ像を配置するデモ。
 * 一番目の引数に整数値を与えると、その値に応じてカメラ像を配置する。
 * @param args 第1引数を整数値として受け取る
 */
public class Main14{
  public static void main(String[] args){
    int arg = Integer.parseInt(args[0]);
    System.out.println("seed number:"+arg);

    int x= (arg*7)%1280;
    int y= (arg*31)%980;
    int id = arg;
    Target target = new WindowTarget("Large Plane", 1600, 1200);
    ShapeManager sm = new OrderedShapeManager();
    Shape s = new Camera(id, x, y);
    sm.add(s);
    while(true){
      target.draw(sm);
      target.flush();
    }
  }
}
