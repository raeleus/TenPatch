# CHANGES #

### Version 4.2.0 ###

* Added PlayMode option to control playback of animations.

### Version 4.1.0 ###

* Added autoUpdate option to TenPatchDrawable. No longer unnecessary to call update() manually unless you want to specify a delta different from Gdx.graphics.getDeltaTime().

### Version 4.0.0 ###

* Merged AnimatedTenPatchDrawable features into TenPatchDrawable and removed AnimatedTenPatchDrawable class.

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