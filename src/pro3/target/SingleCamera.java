package pro3.target;

import pro3.camera.*;
import java.awt.image.*;
import java.nio.*;

/** 撮影装置のインスタンスを複数のターゲットで共有するためのクラス */
class SingleCamera{
  static private CameraDevice cameradev=null;
  private SingleCamera(){};
  static private boolean isStarted=false;

  /** {@link SingleCamera}のインスタンスを作る 
   * @param width 撮影画像の幅
   * @param height 撮影画像の高さ
   */
  static SingleCamera create(int width, int height){
    if(cameradev == null){
      cameradev = new CameraJavaCV(width, height);
    }
    return new SingleCamera();
  }

  /** 撮影装置を撮影可能状態にする
   * @return 撮影可能状態になれば true を返す
   */
  public boolean start(){
    if(isStarted){
      return true;
    }
    isStarted = true;
    return cameradev.start();
  }

  /** 撮影装置を撮影停止状態にする
   */
  public boolean stop(){
    if(isStarted){
      isStarted =false;
      return cameradev.stop();
    }
    return false;
  }

  /** 撮影画像の幅を返す
   * @return 幅
   */
  public int getWidth(){
    return cameradev.getWidth();
  }

  /** 撮影画像の高さを返す
   * @return 高さ
   */
  public int getHeight(){
    return cameradev.getHeight();
  }

  /** 撮影画像の1フレーム分のデータをコピーするために適した{@link BufferedImage}
   *を用意して返す
   */
  public BufferedImage createBufferedImage(){
    return cameradev.createBufferedImage();
  }

  /** 撮影画像の1フレーム分のデータを返す 
   * @param img 1フレーム分のデータがコピーされる {@link BufferedImage}
   * getメソッドの中で一番使用を推奨
   */
  public void get(BufferedImage img){
    start();
    cameradev.getBufferedImage(img);
  } 

  /** 撮影画像の1フレーム分のデータを返す 
   * @param img 1フレーム分のデータがコピーされる {@link ByteBuffer}
   */
  public void get(ByteBuffer buffer){
    start();
    cameradev.getByteBuffer(buffer);
  } 

  /** 撮影画像の1フレーム分のデータを返す 
   * OS依存のデータ形式なため、使用は非推奨 
   */
  public ByteBuffer get(){
    start();
    return cameradev.getRawByteBuffer();
  } 
}