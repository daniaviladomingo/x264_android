#!/bin/bash

git clone https://code.videolan.org/videolan/x264.git

export NDK=/home/dani/Android/Sdk/ndk-bundle # your android ndk folder
export HOST_TAG=linux-x86_64 # adjust to your building host
export TOOLCHAIN=$NDK/toolchains/llvm/prebuilt/$HOST_TAG

BUILD_SCRIPTS=build_abi_scripts

chmod +x $BUILD_SCRIPTS/arm64-v8a.sh
chmod +x $BUILD_SCRIPTS/armeabi-v7a.sh
chmod +x $BUILD_SCRIPTS/x86_64.sh
chmod +x $BUILD_SCRIPTS/x86.sh

cd x264 || exit

../$BUILD_SCRIPTS/./arm64-v8a.sh
../$BUILD_SCRIPTS/./armeabi-v7a.sh
../$BUILD_SCRIPTS/./x86_64.sh
../$BUILD_SCRIPTS/./x86.sh
