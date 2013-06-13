package pro3;
import pro3.shape.Attribute;
import pro3.shape.Circle;
import pro3.shape.Shape;
import pro3.shape.OrderedShapeManager;
import pro3.shape.ShapeManager;
import pro3.target.*;

/** 同心円を描画するデモ */
public class Main4{
  /** メインメソッド */
  public static void main(String[] argv){
    //    ShapeManager sm = new ShapeManager();
    ShapeManager sm = new OrderedShapeManager();
    Target target;
    target = new WindowTarget("DisplayShapes");
    // target = new TextTarget(System.out);
    while(true){
      for(int i=256;0<=i;i=i-50){
        Shape s = new Circle(i,100,100,256-i);
        Attribute attr = new Attribute();
        attr.setColor(i, i, 255-i);
        attr.setFill(true);
        s.setAttribute(attr);
        sm.add(s);
        target.draw(sm);
        target.flush();
        try{
          Thread.sleep(100);
        }catch(InterruptedException e){
        }
      }
      target.clear(sm);
    }
  }
}
