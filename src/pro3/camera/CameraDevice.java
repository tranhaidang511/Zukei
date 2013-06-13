package pro3.camera;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

/** カメラ装置を撮影操作するためのインタフェース */
public interface CameraDevice{
  /** 撮影装置を使用可能にする */
  public boolean start();
  /** 撮影装置を使用を止める */
  public boolean stop();
  /** 撮影装置のリソースを開放する */
  public boolean dispose();
  /** 撮影画像の幅を返す */
  public int getWidth();
  /** 撮影画像の高さを返す */
  public int getHeight();
  /** 撮影画像の幅、高さを設定する 
   * @param width 撮影画像の幅
   * @param height 撮影画像の高さ
   */
  public void setSize(int width, int height);
  /** 撮影画像の1フレームをコピーできる {@link BufferedImage} を用意して返す */
  public BufferedImage createBufferedImage();
  public ByteBuffer createByteBufferDirect();
  /** 撮影画像を返す 
   *  @param image 撮影画像をコピーする{@link BufferedImage} オブジェクト
   */
  public void getBufferedImage(BufferedImage image);
  public void getByteBuffer(ByteBuffer output);
  public ByteBuffer getRawByteBuffer();
}