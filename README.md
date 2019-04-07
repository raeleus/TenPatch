# README #

### TenPatch ###

Version 1

This project is an alternative to libGDX's 9patch implementation. It aims to have the following features:

* Multiple stretch regions as seen with Android's implementation.
* An option for tiling stretch regions.
* Loading via skin JSON.
* An editor via Skin Composer

### How to Use ###

Add the following to your root build.gradle:

```
allprojects {
    ...
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Add the dependency to the core project:

```
project(":core") {
    apply plugin: "java"


    dependencies {
        ...
        compile 'com.github.raeleus:TenPatch:v1.0'
    }
}
```

Please see the examples in the [demo project](https://github.com/raeleus/TenPatch/tree/master/demo/src/com/ray3k/tenpatch/demo/desktop).

### Contact ###

* This project is maintained by Raymond "Raeleus" Buckley
* http://ray3k.wordpress.com
* raymond.ray3k (at) gmail.com

### License ###
MIT License

Copyright (c) 2019 Raymond Buckley

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
