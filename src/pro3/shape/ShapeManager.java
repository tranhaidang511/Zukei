package pro3.shape;
import java.util.*;
import pro3.target.Target;

/**
 * 図形の集合
 */
public class ShapeManager{
  /** 図形のリスト */
  AbstractCollection<Shape> data;

  /** 図形の集合を生成する（空集合）
   */
  public ShapeManager(){
    data = new ArrayList<Shape>(); 
  }

  /** 図形の集合を空集合にする
   */
  public synchronized void clear(){
    data.clear();
  }

  /** 図形の集合に図形を追加する
   *  もし既に同じ識別子を持つ図形があれば置き換える
   *  @param in 追加する図形
   */
  public synchronized void put(Shape in){
    if(!replace(in)){
      add(in);
    }
  }

  /** 図形の集合に図形を追加する
   *  @param in 追加する図形
   */
  public synchronized void add(Shape in){
    data.add(in);
  }

  /** 図形の集合中の図形を置き換える
   *  @param in 置き換え先の図形
   *  @return もともと図形がリスト中にあったら true, なければ false
   */
  public synchronized boolean replace(Shape in){
    for(Shape s:data){
      if(s.id == in.id){
        data.remove(s);
        data.add(in);
        return true;
      }
    }
    return false;
  }

  /** 図形の集合から図形を削除する
   *  @param id 削除する図形の識別子
   *  @return もともと図形がリスト中にあったら true, なければ false
   */
  public synchronized boolean remove(int id){
    for(Shape s:data){
      if(s.id == id){
        data.remove(s);
        return true;
      }
    }
    return false;
  }

  public synchronized void copy(ShapeManager in){
    data.clear();
    for(Shape s: in.data){
      data.add(s);
    }
  }

  public synchronized void merge(ShapeManager in){
    for(Shape si:in.data){
      put(si);
    }
  }

  /** 集合内の図形を出力する．
   *  @param target 出力装置
   */
  public synchronized void draw(Target target){
    for(Shape s:data){
      s.draw(target); 
    }
  }
}
