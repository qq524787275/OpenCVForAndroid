cmake_minimum_required(VERSION 3.4.1)
set(OpenCV_DIR ../OpenCV-android-sdk/sdk/native/jni)
find_package(OpenCV REQUIRED)
#opencv3.0以上添加下面这句
#include_directories(${OpenCV_INCLUDE_DIRS})
add_library(
             native-lib
             SHARED
             src/main/cpp/native-lib.cpp )
find_library(
              log-lib
              log )
target_link_libraries(
                       native-lib
                       ${log-lib}
                       ${OpenCV_LIBS})