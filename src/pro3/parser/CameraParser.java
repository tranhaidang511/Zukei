package pro3.parser;
import java.util.Scanner;

import pro3.shape.Attribute;
import pro3.shape.Camera;

class CameraParser implements ShapeParser{
  CameraParser(){
  }
  @Override
  public Camera parse(Scanner s, int id){
    int x = s.nextInt();
    int y = s.nextInt();
    Camera ret = new Camera(id,x,y);

    if(s.hasNext("Attribute")){
      Attribute attr = AttributeParser.parse(s);
      ret.setAttribute(attr);
    }
    return ret;
  }
}
