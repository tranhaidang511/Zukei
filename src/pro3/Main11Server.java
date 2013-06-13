package pro3;

import java.io.*;
import pro3.target.*;
import pro3.shape.*;

/** クライアントからの通信を受けて描画するデモ
 */
public class Main11Server{
  public static void main(String[] args){
    TargetServer ts;
    try {
      ts=new TargetServer(new WindowTarget("test server",640,480));
      ts.welcome();
    }catch(IOException e){
    }
  } 
}
