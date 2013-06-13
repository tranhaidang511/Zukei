package pro3;

import pro3.shape.*;
import pro3.target.Target;
import pro3.target.WindowTarget;

/** synchronizedの有用性を見るためのデモ
 */
public class Main9{
  public static void main(String[] argv){
    final int width=1920;
    final int height=600;
    Target target = new WindowTarget("DisplayShapes",width,height);
    ShapeManager[] sm = new ShapeManager[8];

    for(int c=0;c<sm.length;c++){
      sm[c] = new ShapeManager();
    }
    for(int c=0;c<sm.length;c++){
      for(int i=0;i<height;i=i+16){
        Attribute attr = new Attribute();
        int ic = i+c*64;
        attr.setColor((ic%512)/2, (ic%512)/2, (511-ic%512)/2);
        attr.setFill(true);
        for(int j=0;j<width;j=j+2){
          Shape s = new Circle(i*width+j,j,i+(j%100),80);
          s.setAttribute(attr);
          sm[c].add(s);
        }
      }
    }
    while(true){
      for(ShapeManager sme: sm){
        target.draw(sme);
        target.flush();
        try{
          Thread.sleep(10);
        }catch(Exception e){
        }
      }
    }
  }
}
