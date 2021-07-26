package com.ray3k.tenpatch.demo.desktop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ray3k.tenpatch.TenPatchDrawable;

public class GradientTest extends ApplicationAdapter {
    private Stage stage;
    private Skin skin;
    
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        new Lwjgl3Application(new GradientTest(), config);
    }
    
    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal("tenpatch.json"));
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
    
        TenPatchDrawable tenPatchDrawable = skin.get("white-ten", TenPatchDrawable.class);
        tenPatchDrawable.setColors(Color.WHITE, Color.RED, Color.GREEN, Color.BLUE);
        
        Image image = new Image(tenPatchDrawable);
        image.setScaling(Scaling.stretch);
        image.setColor(Color.YELLOW);
        root.add(image).grow();
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
        
        stage.act(delta);
        stage.draw();
    }
    
    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}