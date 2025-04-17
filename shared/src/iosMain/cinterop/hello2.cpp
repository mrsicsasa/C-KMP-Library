#include "hello2.h"

extern "C"{
const char* hello_from_c() {
    return "Hello world from C";
}
}