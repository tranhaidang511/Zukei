#pragma OPENCL EXTENSION cl_khr_byte_addressable_store : enable

// OpenCL Kernel Function 
__kernel void Masking(const int width, const int height, 
                         __global const uchar* in,
                         __global uchar* out, 
                         __global const uchar* mask){
  // get index of global data array
  int i = get_global_id(0);
  int inaddr = i*3;
  int moaddr = i*4;
  if((mask[moaddr+0]& ~mask[moaddr+1] & ~mask[moaddr+2]) ==0xff){
    out[moaddr] = in[inaddr];
    out[moaddr+1] = in[inaddr+1];
    out[moaddr+2] = in[inaddr+2];
    out[moaddr+3] = 255;
  }else{
    out[moaddr] = mask[moaddr];
    out[moaddr+1] = mask[moaddr+1];
    out[moaddr+2] = mask[moaddr+2];
    out[moaddr+3] = mask[moaddr+3];
  }
}  
