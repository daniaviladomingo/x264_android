#!/bin/bash

export CC=$TOOLCHAIN/bin/armv7a-linux-androideabi18-clang # c compiler path
export CXX=$TOOLCHAIN/bin/armv7a-linux-androideabi18-clang++ # c++ compiler path

function build_armeabi-v7a
{
  ./configure \
  --prefix=./../prebuilt/armeabi-v7a \
  --enable-static \
  --enable-pic \
  --disable-asm \
  --disable-opencl \
  --disable-cli \
  --host=arm-linux \
  --cross-prefix="$TOOLCHAIN"/bin/arm-linux-androideabi- \
  --sysroot="$TOOLCHAIN"/sysroot \

  make clean
  make
  make install
}

build_armeabi-v7a
echo build_armeabi-v7a finished
