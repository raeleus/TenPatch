# CHANGES #

### Version 3.1.1 ###

* Fixed negative offset speed resulting in tiling breaking bounds in the lower left.
* Switched to using floats for offsets for buttery smooth scrolling action.

### Version 3.1.0 ###

* Switched to semantic versioning.
* Added offset and offsetSpeed options to allow for a scrolling/animated background effect.
* Added color1, color2, color3, and color4 options to create gradients and psychedelic effects.
* Added HTML5/GWT support.

### Version 3 ###

* Downgraded libGDX version to 1.9.9.

### Version 2 ###

* The Ten Patch Color has accessor methods. Use this to change the color of the drawable on the fly.
* Resolved issues with blurry drawables when the image is packed in a linear/linear atlas.

### Version 1 ###

* Multiple stretch regions as seen with Android's implementation.
* An option for tiling stretch regions.
* Loading via skin JSON.