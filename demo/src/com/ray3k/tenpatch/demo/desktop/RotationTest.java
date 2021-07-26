package com.ray3k.tenpatch.demo.desktop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class RotationTest extends ApplicationAdapter {
    private Stage stage;
    private Skin skin;
    private WidgetGroup group;
    private Image image;
    
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        new LwjglApplication(new RotationTest(), config);
    }
    
    @Override
    public void create() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("tenpatch.json"));
        Gdx.input.setInputProcessor(stage);
    
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
    
        group = new WidgetGroup();
        group.setTransform(true);
    
        image = new Image(skin, "sand-ten");
        group.addActor(image);
        group.setOrigin(image.getWidth() / 2, image.getHeight() / 2);
        root.add(group).size(image.getWidth(), image.getHeight());
    }
    
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
    
    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    
        group.setRotation((float) Gdx.input.getX() / Gdx.graphics.getWidth() * 360);
        
        stage.act();
        stage.draw();
    }
    
    @Override
    public void dispose() {
        skin.dispose();
    }
}
