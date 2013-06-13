package pro3.parser;
import java.util.*;

import pro3.shape.Shape;

interface ShapeParser{
  public Shape parse(Scanner s, int id);
}