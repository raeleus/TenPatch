package com.ray3k.tenpatch.demo.desktop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ray3k.tenpatch.TenPatchDrawable;
import com.ray3k.tenpatch.TenPatchDrawable.CrushMode;

public class CrushTest extends ApplicationAdapter {
    private Stage stage;
    private Skin skin;
    
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(800, 800);
        new Lwjgl3Application(new CrushTest(), config);
    }
    
    @Override
    public void create() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("tenpatch.json"));
        Gdx.input.setInputProcessor(stage);
    
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
    
        Table table = new Table();
        root.add(table).grow().pad(100).minWidth(0);
        
        table.defaults().space(10);
        final Array<TenPatchDrawable> tenPatchDrawables = new Array<TenPatchDrawable>();
        TenPatchDrawable tenPatchDrawable = skin.get("sand-ten", TenPatchDrawable.class);
        tenPatchDrawables.add(tenPatchDrawable);
        Image image = new Image(tenPatchDrawable);
        table.add(image);
    
        tenPatchDrawable = skin.get("scroll-knob-ten", TenPatchDrawable.class);
        tenPatchDrawables.add(tenPatchDrawable);
        image = new Image(tenPatchDrawable);
        table.add(image).growY();
    
        table.row();
        tenPatchDrawable = skin.get("progress-bar-knob-ten", TenPatchDrawable.class);
        tenPatchDrawables.add(tenPatchDrawable);
        image = new Image(tenPatchDrawable);
        table.add(image).growX();
        
        table.row();
        tenPatchDrawable = skin.get("music-ten", TenPatchDrawable.class);
        tenPatchDrawables.add(tenPatchDrawable);
        image = new Image(tenPatchDrawable);
        table.add(image).growX();
    
        table.row();
        tenPatchDrawable = skin.get("skull-ten", TenPatchDrawable.class);
        tenPatchDrawables.add(tenPatchDrawable);
        image = new Image(tenPatchDrawable);
        table.add(image).growX();
        
        root.row();
        table = new Table();
        root.add(table).minWidth(0);
    
        table.defaults().space(10);
        ButtonGroup<TextButton> buttonGroup = new ButtonGroup<TextButton>();
        TextButton textButton = new TextButton("Shrink (default)", skin, "toggle");
        table.add(textButton);
        buttonGroup.add(textButton);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                for (TenPatchDrawable drawable : tenPatchDrawables) {
                    drawable.crushMode = CrushMode.SHRINK;
                }
            }
        });
    
        textButton = new TextButton("Crop", skin, "toggle");
        table.add(textButton);
        buttonGroup.add(textButton);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                for (TenPatchDrawable drawable : tenPatchDrawables) {
                    drawable.crushMode = CrushMode.CROP;
                }
            }
        });
    
        textButton = new TextButton("Crop Reversed", skin, "toggle");
        table.add(textButton);
        buttonGroup.add(textButton);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                for (TenPatchDrawable drawable : tenPatchDrawables) {
                    drawable.crushMode = CrushMode.CROP_REVERSED;
                }
            }
        });
    
        textButton = new TextButton("None", skin, "toggle");
        table.add(textButton);
        buttonGroup.add(textButton);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                for (TenPatchDrawable drawable : tenPatchDrawables) {
                    drawable.crushMode = CrushMode.NONE;
                }
            }
        });
    }
    
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
    
    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.act();
        stage.draw();
    }
    
    @Override
    public void dispose() {
        skin.dispose();
    }
}
