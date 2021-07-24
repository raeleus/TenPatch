package com.ray3k.tenpatch.demo.desktop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ray3k.tenpatch.TenPatchDrawable;

public class ScaleTest extends ApplicationAdapter {
    private Stage stage;
    private Skin skin;
    TenPatchDrawable tenPatchDrawable;
    
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        new LwjglApplication(new ScaleTest(), config);
    }
    
    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal("tenpatch.json"));
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    
        tenPatchDrawable = skin.get("sand-ten", TenPatchDrawable.class);
        Table root = new Table();
        root.setBackground(tenPatchDrawable);
        root.setFillParent(true);
        stage.addActor(root);
    }
    
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
    
    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        float delta = Gdx.graphics.getDeltaTime();
        float scale = (float) Gdx.input.getX() / Gdx.graphics.getWidth() * 10;
        tenPatchDrawable.setScaleX(scale);
        tenPatchDrawable.setScaleY(scale);
        
        skin.get("sand-ten", TenPatchDrawable.class).update(delta);
        stage.act(delta);
        stage.draw();
    }
    
    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}