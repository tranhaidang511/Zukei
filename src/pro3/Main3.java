package pro3;
import pro3.shape.Attribute;
import pro3.shape.Circle;
import pro3.shape.Image;
import pro3.shape.Shape;
import pro3.shape.ShapeManager;
import pro3.target.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;

/** 画像ファイルを画像図形に利用して表示するデモ */
public class Main3{
  /** 画像ファイル名 */
  private final static String IMAGEFILENAME = "sweet.png";
  /** メインメソッド */
  public static void main(String[] argv){
    ShapeManager sm = new ShapeManager();
    Target target;
    target = new WindowTarget("DisplayShapes", 300, 300);
//    target = new TextTarget(System.out);
    File imagefile=null;
    BufferedImage bimage = null;
    try {
      imagefile = new File(IMAGEFILENAME);
      bimage = ImageIO.read(imagefile);
    }catch(IOException e){
      System.err.println(e);
    }

    while(true){
      for(int i=0;i<256;i=i+50){
        for(int ii=0;ii<256;ii=ii+50){
          Shape s = new Image(i*256+ii,ii,i,bimage);
          Attribute attr = new Attribute();
          attr.setColor(ii, i, 255-i);
          attr.setFill(true);
          s.setAttribute(attr);
          sm.add(s);
          target.draw(sm);
          target.flush();
          try{
            Thread.sleep(200);
          }catch(InterruptedException e){
          }
        }
      }
      target.clear(sm);
    }
  }
}
