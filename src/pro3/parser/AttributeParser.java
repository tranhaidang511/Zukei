package pro3.parser;
import java.util.*;

import pro3.shape.Attribute;

class AttributeParser{
  public static Attribute parse(Scanner s){
    Attribute ret = new Attribute();
    s.next();
    int r = s.nextInt();
    int g = s.nextInt();
    int b = s.nextInt();
    boolean f = s.nextBoolean();
    ret.setColor(r, g, b);
    ret.setFill(f);
    return ret;
  }
}
