/*
 * The MIT License
 *
 * Copyright 2019 Raymond Buckley.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.ray3k.tenpatch;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * An animated version of TenPatchDrawable. An Animation must be provided in
 * order and update must be called regularly to render properly. The same
 * stretch areas will be applied to all frames of the animation.
 * @author Raymond
 * @see TenPatchDrawable
 */
public class AnimatedTenPatchDrawable extends TenPatchDrawable {
    private Animation<TextureRegion> animation;
    private Array<TextureRegion> regions;
    private float frameDuration;

    /**
     * No-argument constructor necessary for loading via JSON.
     * horizontalStretchAreas and verticalStretchAreas must be set before this
     * drawable is drawn.
     * @see TenPatchDrawable#setHorizontalStretchAreas(int[])
     * @see TenPatchDrawable#setVerticalStretchAreas(int[])
     */
    public AnimatedTenPatchDrawable() {
        
    }

    /**
     * Create a duplicate AnimatedTenPatchDrawable.
     * @param other 
     */
    public AnimatedTenPatchDrawable(AnimatedTenPatchDrawable other) {
        super(other);
        setAnimation(other.getAnimation());
    }

    /**
     * Creates a TenPatchDrawable. All stretch values must be defined in pairs.
     * @param horizontalStretchAreas Pairs of values defining stretch areas from
     * the left of the graphic in ascending order in pixels. All values are
     * inclusive with 0 being the left-most pixel (i.e. (0,2) defines a 3 pixel
     * wide stretch area on the left of the graphic).
     * @param verticalStretchAreas Pairs of values defining stretch areas from
     * the bottom of the graphic in ascending order in pixels. All values are
     * inclusive with 0 being the bottom-most pixel (i.e. (0,2) defines a 3
     * pixel high stretch area on the bottom of the graphic).
     * @param tiling
     * @param animation An Animation of TextureRegions with at least one frame.
     */
    public AnimatedTenPatchDrawable(int[] horizontalStretchAreas, int[] verticalStretchAreas, boolean tiling, Animation<TextureRegion> animation) {
        super(horizontalStretchAreas, verticalStretchAreas, tiling, null);
        setAnimation(animation);
    }

    @Override @Deprecated
    public void setRegion(TextureRegion region) {
        super.setRegion(region);
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }

    /**
     * Set the animation of the AnimatedTenPatchDrawable.
     * @param animation An Animation of TextureRegions with at least one frame.
     */
    public void setAnimation(Animation<TextureRegion> animation) {
        this.animation = animation;
    }
    
    /**
     * Typical use will require calling update() for every frame in render()
     * @param delta
     * @see ApplicationListener#render() 
     */
    @Override
    public void update(float delta) {
        super.update(delta);
        
        if (animation == null && regions != null) {
            animation = new Animation<TextureRegion>(frameDuration, regions, Animation.PlayMode.LOOP);
        }
        
        if (animation != null) {
            TextureRegion region = animation.getKeyFrame(time);
            if (getRegion() == null || !getRegion().equals(region)) {
                float minWidth = getMinWidth();
                float minHeight = getMinHeight();
                super.setRegion(region);
                setMinWidth(minWidth);
                setMinHeight(minHeight);
            }
        }
    }
}
