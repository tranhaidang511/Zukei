package pro3.camera;

import com.googlecode.javacv.*;
import com.googlecode.javacv.cpp.opencv_core.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.nio.ByteBuffer;

/** 撮影装置としてOpenCVライブラリ経由でweb cameraを使うクラス */
public class CameraJavaCV implements CameraDevice{
  private FrameGrabber framegrabber;
  private BufferedImage bimg;
  private volatile boolean captureready;

  /** 撮影画像の大きさは標準の640x480 としてdevice id=0 のカメラに対応するインスタンスを作成
   */
  public CameraJavaCV(){
    this(0);
  }

  /** 撮影画像の大きさは標準の640x480 としてdevice のカメラに対応するインスタンスを作成
   * @param device カメラのデバイスID
   */
  public CameraJavaCV(int device){
    framegrabber = new OpenCVFrameGrabber(device);
    setSize(640,480);
    setShutdownHookThread();
  }

  /** 撮影画像の大きさを指定してdevice id=0のカメラに対応するインスタンスを作成*/
  public CameraJavaCV(int width, int height){
    this(0,width,height);
  }

  /** 撮影画像の大きさを指定してdevice のカメラに対応するインスタンスを作成
   *  @param device カメラのデバイスID
   *  @param width  撮影画像の幅
   *  @param height 撮影画像の高さ
   */
  public CameraJavaCV(int device, int width, int height){
    framegrabber = new OpenCVFrameGrabber(device);
    setSize(width, height);
    setShutdownHookThread();
  }

  private void setShutdownHookThread(){
    Runtime.getRuntime().addShutdownHook(
      new Thread(new Runnable(){ public void run(){
        if(framegrabber!=null){
          stop();
        }
      }
    }));
  }

  /** 撮影装置を使用可能にする */
  synchronized public boolean start(){
    try {
      framegrabber.start();
    }catch(com.googlecode.javacv.FrameGrabber.Exception e){
      return false;
    }
    captureready = true;
    return true;
  }

  /** 撮影装置を使用を止める dispose()と実質同じ */
  synchronized public boolean stop(){
    captureready = false;
    try {
      framegrabber.stop();
    }catch(com.googlecode.javacv.FrameGrabber.Exception e){
      return false;
    }
    return true;
  }

  /** 撮影装置のリソースを開放する */
  synchronized public boolean dispose(){
    captureready = false;
    try {
      framegrabber.release();
    }catch(com.googlecode.javacv.FrameGrabber.Exception e){
      return false;
    }
    return true;
  }

  /** 撮影画像の幅を返す */
  public int getWidth(){
    return framegrabber.getImageWidth();
  }

  /** 撮影画像の高さを返す */
  public int getHeight(){
    return framegrabber.getImageHeight();
  }

  /** 撮影画像の幅、高さを設定する
   * @param width 撮影画像の幅
   * @param height 撮影画像の高さ
   */
  public void setSize(int width, int height){
    framegrabber.setImageWidth(width); 
    framegrabber.setImageHeight(height); 
    bimg = new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
  }

  /** 撮影画像の1フレームをコピーできる {@link BufferedImage} を用意して返す */
  public BufferedImage createBufferedImage(){
    return new BufferedImage(framegrabber.getImageWidth(),
                             framegrabber.getImageHeight(),
                             BufferedImage.TYPE_3BYTE_BGR);
  }

  /** 撮影画像の1フレームをコピーできる {@link ByteBuffer} を用意して返す */
  public ByteBuffer createByteBufferDirect(){
    return ByteBuffer.allocateDirect(framegrabber.getImageWidth()*
                                     framegrabber.getImageHeight()*3);
  }

  /** 撮影画像を返す 
   *  @param img 撮影画像をコピーする{@link BufferedImage} オブジェクト
   */
  synchronized public void getBufferedImage(BufferedImage img){
    if(!captureready){
      return;
    }
    try {
      framegrabber.grab().copyTo(img);
    }catch(com.googlecode.javacv.FrameGrabber.Exception e){
    }
  } 

  /** 撮影画像を返す 
   *  @return 1フレーム分のデータ
   *  OSによりデータの形式が異なるので利用には注意
   */
  synchronized public ByteBuffer getRawByteBuffer(){
    if(!captureready){
      return null;
    }
    IplImage img = null;
    try {
      img = framegrabber.grab();
      //      bbuffer.rewind();
      //      bbuffer.put(img.getByteBuffer());
    }catch(com.googlecode.javacv.FrameGrabber.Exception e){
    }
    return img.getByteBuffer();
  } 

  /** 撮影画像を返す 
   *  @param output 1フレーム分のデータをコピーする{@link ByteBuffer}オブジェクト
   *  OSに依存せずBGRの順番で1画素を表現する形式でコピーをする
   */
  synchronized public void getByteBuffer(ByteBuffer output){
    if(!captureready){
	return; 
    }
    BufferedImage bi = new BufferedImage(framegrabber.getImageWidth(),
					 framegrabber.getImageHeight(),
					 BufferedImage.TYPE_3BYTE_BGR);
    try {
      framegrabber.grab().copyTo(bi);
    }catch(com.googlecode.javacv.FrameGrabber.Exception e){
    }
    //    return img.getByteBuffer();
    DataBufferByte dbb = (DataBufferByte)bi.getRaster().getDataBuffer();
    output.rewind();
    output.put(dbb.getData());
  } 
  /**
   * このクラス内にあらかじめ用意してある{@link BufferedImage}に1フレーム分の撮影画像をコピーして、そのオブジェクトを返す
   * データの操作に注意が必要
   */
  synchronized public BufferedImage getShotAsBufferedImage(){
    if(!captureready){
      return bimg;
    }
    try {
      framegrabber.grab().copyTo(bimg);
    }catch(com.googlecode.javacv.FrameGrabber.Exception e){
    }
    return bimg;
  } 
}
