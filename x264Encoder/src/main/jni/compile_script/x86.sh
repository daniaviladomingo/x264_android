#!/bin/bash
export NDK=/home/dani/Android/Sdk/ndk-bundle # your android ndk folder
export HOST_TAG=linux-x86_64 # adjust to your building host
export TOOLCHAIN=$NDK/toolchains/llvm/prebuilt/$HOST_TAG

export CC=$TOOLCHAIN/bin/i686-linux-android18-clang
export CXX=$TOOLCHAIN/bin/i686-linux-android18-clang++

function build_x86
{
  ./configure \
  --prefix=./../prebuilt/x86 \
  --enable-static \
  --enable-pic \
  --disable-asm \
  --disable-opencl \
  --disable-cli \
  --host=i686-linux \
  --cross-prefix=$TOOLCHAIN/bin/i686-linux-android- \
  --sysroot=$TOOLCHAIN/sysroot \

  make clean
  make
  make install
}

build_x86
echo build_x86 finished
