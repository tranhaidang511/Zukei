package pro3.opencl;
import pro3.target.*;
import pro3.shape.Camera;
import java.nio.*;
import com.jogamp.opencl.CLBuffer;
import com.jogamp.opencl.CLCommandQueue;
import com.jogamp.opencl.CLKernel;
import com.jogamp.opencl.CLProgram;
import static com.jogamp.opencl.CLMemory.Mem.*;
import easycl.*;


public class TargetImageFilter extends TargetFilter{
  CLSetup cl;
  CLBuffer<ByteBuffer> InBuffer, OutBuffer;
  ByteBuffer inbuffer, outbuffer;
  CLCommandQueue queue;
  CLProgram program;
  CLKernel kernel;

  public TargetImageFilter(Target target){
    this(target, null, "afilter.cl", "Filter");
  }

  public TargetImageFilter(Target target, Object object, String kernelsource, String kernelname){
    super(target);
    cl = CLSetupCreator.createCLSetup();
    cl.initContext();
    InBuffer = cl.createByteBuffer(Camera.WIDTH*Camera.HEIGHT*3,READ_ONLY);
    inbuffer = InBuffer.getBuffer();
    OutBuffer = cl.createByteBuffer(Camera.WIDTH*Camera.HEIGHT*4,WRITE_ONLY);
    outbuffer = OutBuffer.getBuffer();
    queue = cl.createQueue();
    if(object==null){
      object=this;
    }
    program = cl.createProgramFromResource(object, kernelsource);
    kernel = program.createCLKernel(kernelname);
    kernel.setArg(0,Camera.WIDTH);
    kernel.setArg(1,Camera.HEIGHT);
    kernel.setArg(2,InBuffer);
    kernel.setArg(3,OutBuffer);
    kernel.setArg(4,100.1f);
  }

  protected ByteBuffer process(ByteBuffer oneshot){
    InBuffer = InBuffer.cloneWith(oneshot);
    inbuffer = InBuffer.getBuffer();

    /* 参考 OpenCLを使わない場合の単純変換  
    ByteBuffer out = ByteBuffer.allocate(Camera.WIDTH*Camera.HEIGHT*4);
    out.order(ByteOrder.LITTLE_ENDIAN);
    for(int i=0;i<inbuffer.limit()/3;i++){
      byte b = inbuffer.get();
      byte g = inbuffer.get();
      byte r = inbuffer.get();
      out.put(b);
      out.put(g);
      out.put(r);
      out.put((byte)0xff);
    }
    out.rewind();
    return out;
    */
    inbuffer.rewind();
    outbuffer.rewind();
    queue.putWriteBuffer(InBuffer,false)//InBufferのデータをカーネルプログラムへ
         .putBarrier()
         .put2DRangeKernel(kernel, 0, 0, Camera.WIDTH, Camera.HEIGHT,0,0)//演算
         .putBarrier()
         .putReadBuffer(OutBuffer, true);//OutBufferへデータコピー、終了まで待つ
    outbuffer.rewind();
    return outbuffer;
  }

  public void setParameter(float p){
    kernel.setArg(4,p);
  }
}