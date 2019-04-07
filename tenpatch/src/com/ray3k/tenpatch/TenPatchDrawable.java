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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * TenPatchDrawable is an alternative to libGDX's implementation of 9-patch. The
 * NinePatch class is limited in functionality: it can only define one
 * stretchable area per axis. Real 9-patches allow for multiple stretch areas as
 * seen in the Android OS. TenPatch addresses this issue by allowing users to
 * pass in multiple values for stretch areas. Stretching is then divided among
 * the multiple patches based on their ratio. It also adds a tiling option,
 * increasing functionality in UI across the board.
 * 
 * Unfortunately, the NinePatch class is deep-rooted in libGDX. Full replacement
 * would require modifications to TextureAtlas, TexturePacker, and more. Thus,
 * TenPatchDrawable will not be directly loaded from a TextureAtlas or from a
 * 9-patch image file. Typical use would require loading from a Skin JSON.
 * Specifying stretch regions and options will be made simple by using the
 * associated editor in Skin Composer.
 * 
 * @author Raymond Buckley
 * @see <a href="https://github.com/raeleus/skin-composer">Skin Composer</a>
 */
public class TenPatchDrawable extends TextureRegionDrawable {
    static private final Color temp = new Color();
    private final Color color = new Color(1, 1, 1, 1);
    public int[] horizontalStretchAreas;
    public int[] verticalStretchAreas;
    public boolean tiling;
    
    /**
     * No-argument constructor necessary for loading via JSON.
     * horizontalStretchAreas and verticalStretchAreas must be set before this
     * drawable is drawn.
     * @see TenPatchDrawable#setHorizontalStretchAreas(int[])
     * @see TenPatchDrawable#setVerticalStretchAreas(int[])
     */
    public TenPatchDrawable() {
        
    }
    
    /**
     * Create a duplicate TenPatchDrawable.
     * @param other 
     */
    public TenPatchDrawable(TenPatchDrawable other) {
        this(other.horizontalStretchAreas, other.verticalStretchAreas, other.tiling, other.getRegion());
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
     * @param region 
     */
    public TenPatchDrawable(int[] horizontalStretchAreas, int[] verticalStretchAreas, boolean tiling, TextureRegion region) {
        this.horizontalStretchAreas = horizontalStretchAreas;
        this.verticalStretchAreas = verticalStretchAreas;
        this.tiling = tiling;
        setRegion(region);
    }

    public static class InvalidPatchException extends RuntimeException {
        
    }
    
    /**
     * Draws the TenPatch to the specified batch. The defined stretch areas
     * will adapt to the width and height by either tiling or stretching/shrinking.
     * Minimum width is defined as the total width of all the non-stretching
     * areas. If width is brought below this value, the non-stretching areas
     * will shrink to accommodate. The same applies to height.
     * @param batch
     * @param x
     * @param y
     * @param width
     * @param height 
     */
    @Override
    public void draw(Batch batch, float x, float y, float width, float height) {
        float previousValue = 0;
        for (float value : horizontalStretchAreas) {
            if (value < previousValue || value >= getRegion().getRegionWidth()) {
                throw new InvalidPatchException();
            }
        }
        
        Color batchColor = batch.getColor();
        temp.set(batchColor);
        batch.setColor(batchColor.mul(color));

        //properties from the texture region
        TextureRegion region = getRegion();
        Texture texture = region.getTexture();
        float w = region.getRegionWidth();
        float h = region.getRegionHeight();
        float u = region.getU();
        float u2 = region.getU2();
        float v = region.getV2();
        float v2 = region.getV();

        //values to pass to batch.draw()
        float drawWidth;
        float drawHeight;
        float drawU;
        float drawV;
        float drawU2;
        float drawV2;

        //calculated values
        float extraWidth = MathUtils.floor(width) - w;
        float extraHeight = MathUtils.floor(height) - h;
        float originX = 0;
        float originY = 0;
        float totalWidthStretch = 0f;

        for (int i = 0; i < horizontalStretchAreas.length; i += 2) {
            totalWidthStretch += horizontalStretchAreas[i + 1] - horizontalStretchAreas[i] + 1;
        }
        float totalHeightStretch = 0f;
        for (int i = 0; i < verticalStretchAreas.length; i += 2) {
            totalHeightStretch += verticalStretchAreas[i + 1] - verticalStretchAreas[i] + 1;
        }

        int yIndex = 0;
        float texY1 = 0;
        while (yIndex <= verticalStretchAreas.length) {
            float texY2 = yIndex < verticalStretchAreas.length ? verticalStretchAreas[yIndex] : h;

            originX = 0;
            int xIndex = 0;
            float texX1 = 0;
            //row of vertically non-stretching pixels
            drawHeight = height > h - totalHeightStretch ? texY2 - texY1 : (texY2 - texY1) * height / (h - totalHeightStretch);
            drawHeight = Math.max(drawHeight, 0);
            while (xIndex <= horizontalStretchAreas.length) {
                //cell of horizontally non-stretching pixels
                float texX2 = xIndex < horizontalStretchAreas.length ? horizontalStretchAreas[xIndex] : w;

                drawWidth = width > w - totalWidthStretch ? texX2 - texX1 : (texX2 - texX1) * width / (w - totalWidthStretch);
                drawWidth = Math.max(drawWidth, 0);
                drawU = u + (u2 - u) * texX1 / w;
                drawV = v + (v2 - v) * texY1 / h;
                drawU2 = u + (u2 - u) * texX2 / w;
                drawV2 = v + (v2 - v) * texY2 / h;
                drawPatches(batch, texture, x, y, originX, originY, drawWidth, drawHeight, drawU, drawV, drawU2, drawV2, texX1, texX2, texY1, texY2, true, true);

                originX += drawWidth;
                xIndex++;

                //cell of horizontally stretching pixels
                if (xIndex < horizontalStretchAreas.length) {
                    texX1 = texX2;
                    texX2 = xIndex < horizontalStretchAreas.length ? horizontalStretchAreas[xIndex] + 1 : w;

                    drawWidth = texX2 - texX1 + extraWidth * (texX2 - texX1) / totalWidthStretch;
                    drawWidth = Math.max(drawWidth, 0);
                    drawU = u + (u2 - u) * texX1 / w;
                    drawV = v + (v2 - v) * texY1 / h;
                    drawU2 = u + (u2 - u) * texX2 / w;
                    drawV2 = v + (v2 - v) * texY2 / h;
                    drawPatches(batch, texture, x, y, originX, originY, drawWidth, drawHeight, drawU, drawV, drawU2, drawV2, texX1, texX2, texY1, texY2, false, true);

                    originX += drawWidth;
                    xIndex++;
                }
                texX1 = texX2;
            }
            originY += drawHeight;
            yIndex++;

            if (yIndex < verticalStretchAreas.length) {
                texY1 = texY2;
                texY2 = yIndex < verticalStretchAreas.length ? verticalStretchAreas[yIndex] + 1 : h;
                xIndex = 0;
                texX1 = 0;
                originX = 0;
                //row of vertically stretching cells
                drawHeight = texY2 - texY1 + extraHeight * (texY2 - texY1) / totalHeightStretch;
                drawHeight = Math.max(drawHeight, 0);
                while (xIndex <= horizontalStretchAreas.length) {
                    float texX2 = xIndex < horizontalStretchAreas.length ? horizontalStretchAreas[xIndex] : w;

                    //cell of horizontally non-stretching pixels
                    drawWidth = width > w - totalWidthStretch ? texX2 - texX1 : (texX2 - texX1) * width / (w - totalWidthStretch);
                    drawWidth = Math.max(drawWidth, 0);
                    drawU = u + (u2 - u) * texX1 / w;
                    drawV = v + (v2 - v) * texY1 / h;
                    drawU2 = u + (u2 - u) * texX2 / w;
                    drawV2 = v + (v2 - v) * texY2 / h;
                    drawPatches(batch, texture, x, y, originX, originY, drawWidth, drawHeight, drawU, drawV, drawU2, drawV2, texX1, texX2, texY1, texY2, true, false);

                    originX += drawWidth;
                    xIndex++;

                    //cell of horizontally stretching cells
                    if (xIndex < horizontalStretchAreas.length) {
                        texX1 = texX2;
                        texX2 = xIndex < horizontalStretchAreas.length ? horizontalStretchAreas[xIndex] + 1 : w;

                        drawWidth = texX2 - texX1 + extraWidth * (texX2 - texX1) / totalWidthStretch;
                        drawWidth = Math.max(drawWidth, 0);
                        drawU = u + (u2 - u) * texX1 / w;
                        drawV = v + (v2 - v) * texY1 / h;
                        drawU2 = u + (u2 - u) * texX2 / w;
                        drawV2 = v + (v2 - v) * texY2 / h;
                        drawPatches(batch, texture, x, y, originX, originY, drawWidth, drawHeight, drawU, drawV, drawU2, drawV2, texX1, texX2, texY1, texY2, false, false);

                        originX += drawWidth;
                        xIndex++;
                    }
                    texX1 = texX2;
                }
                originY += drawHeight;
                yIndex++;
            }
            texY1 = texY2;
        }

        batch.setColor(temp);
    }
    
    /**
     * Simplifies drawing calls in draw method.
     * @see TenPatchDrawable#draw(Batch, float, float, float, float)
     * @param batch
     * @param texture
     * @param x
     * @param y
     * @param originX
     * @param originY
     * @param drawWidth
     * @param drawHeight
     * @param drawU
     * @param drawV
     * @param drawU2
     * @param drawV2
     * @param texX1
     * @param texX2
     * @param texY1
     * @param texY2
     * @param squeezeX
     * @param squeezeY 
     */
    private void drawPatches(Batch batch, Texture texture, float x, float y, float originX, float originY, float drawWidth, float drawHeight, float drawU, float drawV, float drawU2, float drawV2, float texX1, float texX2, float texY1, float texY2, boolean squeezeX, boolean squeezeY) {
        if (!tiling) {
            batch.draw(texture, x + originX, y + originY, drawWidth, drawHeight, drawU, drawV, drawU2, drawV2);
        } else {
            int i, j;
            for (j = 0; j + texY2 - texY1 <= drawHeight && texY2 - texY1 > 0; j += texY2 - texY1) {
                for (i = 0; i + texX2 - texX1 <= drawWidth && texX2 - texX1 > 0; i += texX2 - texX1) {
                    batch.draw(texture, x + originX + i, y + originY + j, texX2 - texX1, texY2 - texY1, drawU, drawV, drawU2, drawV2);
                }
                
                if (i < drawWidth) {
                    batch.draw(texture, x + originX + i, y + originY + j, drawWidth - i, texY2 - texY1, drawU, drawV, squeezeX ? drawU2 : drawU + (drawU2 - drawU) * (drawWidth - i) / (texX2 - texX1), drawV2);
                }
            }
            
            if (j < drawHeight) {
                for (i = 0; i + texX2 - texX1 <= drawWidth && texX2 - texX1 > 0; i += texX2 - texX1) {
                    batch.draw(texture, x + originX + i, y + originY + j, texX2 - texX1, drawHeight - j, drawU, drawV, drawU2, squeezeY ? drawV2 : drawV + (drawV2 - drawV) * (drawHeight - j) / (texY2 - texY1));
                }
                
                if (i < drawWidth) {
                    batch.draw(texture, x + originX + i, y + originY + j, drawWidth - i, drawHeight - j, drawU, drawV, squeezeX ? drawU2 : drawU + (drawU2 - drawU) * (drawWidth - i) / (texX2 - texX1), squeezeY ? drawV2 : drawV + (drawV2 - drawV) * (drawHeight - j) / (texY2 - texY1));
                }
            }
        }
    }
    
    /**
     * Not supported.
     * @see TenPatchDrawable#draw(Batch, float, float, float, float)
     * @param batch
     * @param x
     * @param y
     * @param originX
     * @param originY
     * @param width
     * @param height
     * @param scaleX
     * @param scaleY
     * @param rotation
     * @deprecated
     */
    @Deprecated
    public void draw(Batch batch, float x, float y, float originX, float originY, float width, float height, float scaleX,
            float scaleY, float rotation) {
        throw new UnsupportedOperationException();
    }
    
    /** Creates a new drawable that renders the same as this drawable tinted the specified color. */
    public TenPatchDrawable tint(Color tint) {
        TenPatchDrawable drawable = new TenPatchDrawable(this);
        drawable.color.set(tint);
        drawable.setLeftWidth(getLeftWidth());
        drawable.setRightWidth(getRightWidth());
        drawable.setTopHeight(getTopHeight());
        drawable.setBottomHeight(getBottomHeight());
        return drawable;
    }

    public int[] getHorizontalStretchAreas() {
        return horizontalStretchAreas;
    }

    /**
     * Specifies the horizontal stretch areas. All values must be specified in
     * pairs.
     * @param horizontalStretchAreas Pairs of values defining stretch areas from
     * the left of the graphic in ascending order in pixels. All values are
     * inclusive with 0 being the left-most pixel (i.e. (0,2) defines a 3 pixel
     * wide stretch area on the left of the graphic).
     */
    public void setHorizontalStretchAreas(int[] horizontalStretchAreas) {
        this.horizontalStretchAreas = horizontalStretchAreas;
    }

    public int[] getVerticalStretchAreas() {
        return verticalStretchAreas;
    }

    /**
     * Specifies the vertical stretch areas. All values must be specified in
     * pairs.
     * @param verticalStretchAreas Pairs of values defining stretch areas from
     * the bottom of the graphic in ascending order in pixels. All values are
     * inclusive with 0 being the bottom-most pixel (i.e. (0,2) defines a 3
     * pixel high stretch area on the bottom of the graphic).
     */
    public void setVerticalStretchAreas(int[] verticalStretchAreas) {
        this.verticalStretchAreas = verticalStretchAreas;
    }

    public boolean isTiling() {
        return tiling;
    }

    public void setTiling(boolean tiling) {
        this.tiling = tiling;
    }
}
