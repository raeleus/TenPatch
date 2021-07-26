package com.ray3k.tenpatch.demo.desktop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ray3k.tenpatch.TenPatchDrawable;

/**
 * This class demonstrates how to create a TenPatchDrawable directly from a
 * TextureAtlas. See the tenpatch.atlas and associated PNG in the demo/assets
 * folder. It is advised to use a TextureAtlas at all times to prevent graphical
 * errors at the boundaries of the image. A cleaner technique is demonstrated in
 * the SkinExample class.
 * 
 * @author Raymond Buckley
 * @see SkinExample
 */
public class AtlasExample extends ApplicationAdapter {
    private Stage stage;
    private TextureAtlas textureAtlas;

    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        new Lwjgl3Application(new AtlasExample(), config);
    }

    @Override
    public void create() {
        textureAtlas = new TextureAtlas(Gdx.files.internal("tenpatch.atlas"));
        
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        
        root.pad(100);
        TenPatchDrawable tenPatch = new TenPatchDrawable(new int[] {28, 95}, new int[] {28, 112}, true, textureAtlas.findRegion("brick-wall"));
        Image image = new Image(tenPatch);
        image.setScaling(Scaling.stretch);
        root.add(image).grow();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.act(Gdx.graphics.getDeltaTime());
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
