package pro3;

import java.io.*;
import pro3.target.*;
import pro3.shape.*;

/** 双方向通信のサーバプログラムのデモ
 */
public class Main13Server{
  public static void main(String[] args){
    try {
      TargetServer ts=new TargetServerBidirection(
                               new WindowTarget("test server",1600,1200));
      ts.welcome();
    }catch(IOException e){
    }
  } 
}