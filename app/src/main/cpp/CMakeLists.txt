cmake_minimum_required(VERSION 3.4.1)
add_library(
    HelloNameJni
    SHARED
    HelloNameJni.cpp)

find_library(
    HelloNameJni
    log)

target_link_libraries(
    HelloNameJni
    ${log-lib})