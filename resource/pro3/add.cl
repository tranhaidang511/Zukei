
//__kernel void Add(__global const float* a, __global const float* b, __global float* c, int numElements) {
__kernel void Add(__constant float* a, __constant float* b, __global float* c, int numElements) {
  // get index into global data array
  int i = get_global_id(0);
  
  if(numElements<i) return;
  
  c[i] = a[i]+b[i];
}
