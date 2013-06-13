package pro3;

import java.util.*;

import pro3.parser.MainParser;
import pro3.shape.ShapeManager;
import pro3.target.Target;
import pro3.target.WindowTarget;

/** 
 * clear draw flushと描画の関係を理解するためのデモ
 */
public class Main8{
  public static void main(String[] argv){
    String data = 
      "shape 0 Circle 10 10 40 Attribute 100 40 60 true\n"+
      "shape 1 Circle 100 50 30 Attribute 100 70 30 true\n"+
      "shape 2 Circle 200 100 20 Attribute 100 120 10 true\n"+
      "target clear\n"+
      "shape 0 Circle 10 200 40 Attribute 100 140 60 true\n"+
      "shape 0 Circle 30 200 40 Attribute 220 140 180 true\n"+
      "target draw\n"+
      "target flush\n";

    ShapeManager sm = new ShapeManager();
    Target target;
    target = new WindowTarget("DisplayShapes");
//    target = new TextTarget(System.out);
    MainParser mp = new MainParser(target,sm);
    Scanner s = new Scanner(data);
    mp.parse(s);
//    target.finish();
  }
}
