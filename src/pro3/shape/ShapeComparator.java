package pro3.shape;
import java.util.*;

/**
 * Shapeの大小比較の方法を定義する
 */
public class ShapeComparator implements Comparator<Shape>{
  public int compare(Shape o1, Shape o2){
    return o1.id-o2.id;
    /*
    if(o1.id<o2.id){
      return -1;
    }else if(o2.id<o1.id){
      return 1;
    }else{
      return 0;
    }
    */
  }
}

