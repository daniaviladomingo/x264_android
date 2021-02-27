#!/bin/bash

export CC=$TOOLCHAIN/bin/aarch64-linux-android21-clang
export CXX=$TOOLCHAIN/bin/aarch64-linux-android21-clang++

function build_arm64-v8a
{
  ./configure \
  --prefix=./../prebuilt/arm64-v8a \
  --enable-static \
  --enable-pic \
  --disable-asm \
  --disable-opencl \
  --disable-cli \
  --host=aarch64-linux \
  --cross-prefix="$TOOLCHAIN"/bin/aarch64-linux-android- \
  --sysroot="$TOOLCHAIN"/sysroot \

  make clean
  make
  make install
}

build_arm64-v8a
echo build_arm64-v8a finished

