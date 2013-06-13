package pro3;

import pro3.shape.*;
import pro3.target.*;
import pro3.opencl.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

/** カメラ画像の図形に○○するデモ
 */
public class Main15{
  private final static String IMAGEFILENAME = "house.png";

  public static void main(String[] args){
    File imagefile=null;
    BufferedImage bimage = null;
    Attribute attr;
    Shape s;
    try {
      imagefile = new File(IMAGEFILENAME);
      bimage = ImageIO.read(imagefile);
    }catch(IOException e){
      System.err.println(e);
    }
    Target target = new TargetFilterMasking(
                          new WindowTarget("DisplayShapes",320,240),
                          bimage);
    ShapeManager sm = new OrderedShapeManager();

    s = new Camera(10, 0, 0);
    sm.add(s);
    attr = new Attribute();
    attr.setColor(100,100,230);
    attr.setFill(true);
    s = new Rectangle(2,0,0,320,240);
    s.setAttribute(attr);
    sm.add(s);
    attr = new Attribute();
    attr.setColor(255,255,0);
    attr.setFill(true);
    int i=0;
    while(true){
      s = new Circle(5, 
		     (int)(300.0*Math.cos((i/640.0-0.9)*Math.PI))+200,
		     (int)(-200.0*Math.sin((i/640.0+0.2)*Math.PI))+240,40);
      s.setAttribute(attr);
      sm.put(s);
      target.draw(sm);
      target.flush();
      i = (i+2)%320;
    }
  }
}