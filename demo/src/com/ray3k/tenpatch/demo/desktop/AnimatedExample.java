package com.ray3k.tenpatch.demo.desktop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ray3k.tenpatch.TenPatchDrawable;

/**
 * This class demonstrates the use of an AnimatedTenPatchDrawable loaded from a
 * Texture atlas. Note that the drawable must be updated in order for the animation
 * to display properly.
 * @author Raymond
 * @see SkinExample
 */
public class AnimatedExample extends ApplicationAdapter {
    private Stage stage;
    private TextureAtlas textureAtlas;
    private TenPatchDrawable animatedTenPatchDrawable;

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        new LwjglApplication(new AnimatedExample(), config);
    }

    @Override
    public void create() {
        
        textureAtlas = new TextureAtlas(Gdx.files.internal("tenpatch.atlas"));
        animatedTenPatchDrawable = new TenPatchDrawable();
        animatedTenPatchDrawable.horizontalStretchAreas = new int[] {26, 39, 144, 157};
        animatedTenPatchDrawable.verticalStretchAreas = new int[] {24, 27, 109, 111};
        animatedTenPatchDrawable.setRegions(new Array<TextureRegion>(textureAtlas.findRegions("eye-animation")));
        animatedTenPatchDrawable.setFrameDuration(1/15f);
        
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        
        root.pad(100);
        Image image = new Image(animatedTenPatchDrawable);
        image.setScaling(Scaling.stretch);
        root.add(image).grow();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        float delta = Gdx.graphics.getDeltaTime();
        animatedTenPatchDrawable.update(delta);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        textureAtlas.dispose();
    }
}
