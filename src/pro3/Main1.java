package pro3;
import pro3.shape.*;
import pro3.target.*;

/** 動く自動車を描画するデモ */
public class Main1{
  /** メインメソッド */
  public static void main(String[] argv){
    ShapeManager sm = new ShapeManager();
    Target target;
    target = new WindowTarget("DisplayShapes");
    // target = new TextTarget(System.out);
    Attribute bodycolor = new Attribute();
    Attribute tirecolor = new Attribute();
    Attribute lampcolor = new Attribute();
    Attribute windowcolor = new Attribute();
    Attribute black = new Attribute();
    bodycolor.setColor(255, 0, 0);
    tirecolor.setColor(0, 0, 0);
    lampcolor.setColor(255, 255, 0);
    int lampi=0;
    windowcolor.setColor(0, 255, 255);
    black.setColor(0, 0, 0);
    black.setFill(false);
    while(true){
      for(int i=-190;i<330;i++){
        lampi = (lampi+4)%256;
        lampcolor.setColor(lampi, lampi, 0);
        target.clear(sm);
        Shape s;
        s = new Rectangle(0, 118+i, 125, 72, 25);
        s.setAttribute(bodycolor);
        sm.put(s);
        s = new Rectangle(1, 118+i, 95, 50, 30);
        s.setAttribute(bodycolor);
        sm.put(s);
        s = new Rectangle(2, 138+i, 100, 30, 28);
        s.setAttribute(windowcolor);
        sm.put(s);
        s = new Circle(3, 185+i, 130, 4);
        s.setAttribute(lampcolor);
        sm.put(s);
        s = new Circle(4, 170+i,150,15);
        s.setAttribute(tirecolor);
        sm.put(s);
        s = new Circle(5, 135+i,150,15);
        s.setAttribute(tirecolor);
        sm.put(s);
        s = new Rectangle(10, 40+i,120,60,5);
        s.setAttribute(black);
        sm.put(s);
        s = new Rectangle(11, 30+i,140,60,5);
        s.setAttribute(black);
        sm.put(s);
        s = new Rectangle(12, 45+i,150,60,5);
        s.setAttribute(black);
        sm.put(s);
        target.draw(sm);
        target.flush();
        try{
          Thread.sleep(10);
        }catch(InterruptedException e){
        }
      }
    }
  }
}
