package pro3;

import java.io.*;
import pro3.target.*;
import pro3.shape.*;

/** 受信データによる描画を録画しつつ送信も行う双方向通信のサーバプログラムのデモ
 */
public class Main26Server{
  public static void main(String[] args){
    try {
      TargetServer ts=new TargetServerBidirection(
                        new TargetRecorder("movie",
                          new WindowTarget("test server",640,480)));
      ts.welcome();
    }catch(IOException e){
    }
  } 
}