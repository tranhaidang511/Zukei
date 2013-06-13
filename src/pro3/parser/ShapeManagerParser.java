package pro3.parser;
import java.util.*;

import pro3.shape.ShapeManager;

class ShapeManagerParser implements MetaParser{
  private final ShapeManager shapemanager;
  private final HashMap<String,ShapeParser> map;

  ShapeManagerParser(ShapeManager sm){
    shapemanager = sm;
    map = new HashMap<String,ShapeParser>();
    map.put("Circle", new CircleParser());
    map.put("Image", new ImageParser());
    map.put("Camera", new CameraParser());
  }
  
  @Override
  public void parse(Scanner s){
    int id = s.nextInt();
    ShapeParser sp = map.get(s.next());
    shapemanager.put(sp.parse(s,id));
  }

  class ClearParser implements MetaParser{
    @Override
    public void parse(Scanner s){
      shapemanager.clear();
    }
  }
}
