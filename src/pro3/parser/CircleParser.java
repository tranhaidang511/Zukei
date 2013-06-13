package pro3.parser;
import java.util.Scanner;

import pro3.shape.Attribute;
import pro3.shape.Circle;

class CircleParser implements ShapeParser{
  CircleParser(){
  }
  @Override
  public Circle parse(Scanner s, int id){
    int x = s.nextInt();
    int y = s.nextInt();
    int r = s.nextInt();
    Circle ret = new Circle(id,x,y,r);
    if(s.hasNext("Attribute")){
      Attribute attr = AttributeParser.parse(s);
      ret.setAttribute(attr);
    }
    return ret;
  }

}
