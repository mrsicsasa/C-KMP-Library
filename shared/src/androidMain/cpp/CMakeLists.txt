cmake_minimum_required(VERSION 3.22.1)
project("native-lib")

add_library(
        native-lib
        SHARED
        native-lib.cpp
        ../../../../cpp/hello.cpp
)

target_include_directories(
        native-lib
        PRIVATE
        ../../../../cpp
)

find_library(
        log-lib
        log
)

target_link_libraries(
        native-lib
        ${log-lib}
)
