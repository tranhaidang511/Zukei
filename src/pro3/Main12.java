package pro3;

import pro3.shape.Attribute;
import pro3.shape.Circle;
import pro3.shape.Shape;
import pro3.shape.ShapeManager;
import pro3.target.*;

/** {@link Target}の像をファイルに録画するデモ
 */
public class Main12{
  public static void main(String[] argv){
    ShapeManager sm = new ShapeManager();
    Target target = new TargetRecorder("movie",
                      new WindowTarget("RecordingShapes",320,240));
    while(true){
      for(int i=0;i<256;i=i+50){
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
