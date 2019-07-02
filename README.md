# README #

### TenPatch ###

Version 3.1.0

This project is an alternative to libGDX's 9patch implementation. It has the following features:

* Multiple stretch regions as seen with Android's implementation.
* An option for tiling stretch regions.
* Tiling offsets to achieve the appearance of a moving background.
* Tintable.
* Can be shrunk to size 0 without the artificats as seen in libGDX's 9patch.
* Loading via skin JSON.
* An editor via [Skin Composer](https://github.com/raeleus/skin-composer).

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
        compile 'com.github.raeleus.TenPatch:tenpatch:3.0'
    }
}
```

Please see the examples in the [demo project](https://github.com/raeleus/TenPatch/tree/master/demo/src/com/ray3k/tenpatch/demo/desktop).
To create and edit your own Ten Patches, see the [documentation in Skin Composer](https://github.com/raeleus/skin-composer/wiki/Ten-Patches).

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
