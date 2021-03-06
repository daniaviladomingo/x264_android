#!/bin/bash

export CC=$TOOLCHAIN/bin/x86_64-linux-android21-clang
export CXX=$TOOLCHAIN/bin/x86_64-linux-android21-clang++

function build_x86_64
{
  ./configure \
  --prefix=./../prebuilt/x86_64 \
  --enable-static \
  --enable-pic \
  --disable-asm \
  --disable-opencl \
  --disable-cli \
  --host=x86_64-linux \
  --cross-prefix="$TOOLCHAIN"/bin/x86_64-linux-android- \
  --sysroot="$TOOLCHAIN"/sysroot \

  make clean
  make
  make install
}

build_x86_64
echo build_x86_64 finished
