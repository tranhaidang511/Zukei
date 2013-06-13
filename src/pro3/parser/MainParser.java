package pro3.parser;
import java.util.Scanner;
import java.util.HashMap;

import pro3.shape.ShapeManager;
import pro3.target.Target;

public class MainParser implements MetaParser{
  private Target target;
  private ShapeManager shapemanager;
  private HashMap<String,MetaParser> map;
  public MainParser(Target tgt, ShapeManager sm){
    target = tgt;
    shapemanager = sm; 
    map = new HashMap<String,MetaParser>();
    map.put("shape",new ShapeManagerParser(shapemanager));
    map.put("target",new TargetParser(target,shapemanager));
  }

  @Override
  public void parse(Scanner s){
    MetaParser mp;
    while(s.hasNext()){
      mp = map.get(s.next());
      mp.parse(s);
    }
  }

}
