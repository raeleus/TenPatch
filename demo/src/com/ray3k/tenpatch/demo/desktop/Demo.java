package com.ray3k.tenpatch.demo.desktop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ray3k.tenpatch.TenPatchDrawable;

/**
 * This class showcases the different capabilities of the TenPatchDrawable.
 * Click the buttons to flip through the options.
 * @author Raymond
 * @see SkinExample
 */
public class Demo extends ApplicationAdapter {
    private Stage stage;
    private Skin skin;
    private ProgressBar progressBar;

    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        new Lwjgl3Application(new Demo(), config);
    }

    @Override
    public void create() {
        
        skin = new Skin(Gdx.files.internal("tenpatch.json"));
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        stage.addListener(new InputListener() {
            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                
                if (progressBar != null) {
                    progressBar.setValue(x / stage.getWidth() * 100);
                }
                
                return false;
            }
            
        });
        
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        
        root.pad(10);
        final Table tableLeft = new Table();
        tableLeft.pad(5);
        
        Table tableRight = new Table();
        SplitPane splitPane = new SplitPane(tableLeft, tableRight, false, skin);
        root.add(splitPane).grow();
        
        tableRight.pad(5);
        tableRight.defaults().fillX();
        TextButton textButton = new TextButton("Tiled", skin);
        tableRight.add(textButton);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                tableLeft.clear();
                tableLeft.setBackground((Drawable) null);
                
                Image image = new Image(skin, "brick-wall-ten");
                image.setScaling(Scaling.stretch);
                tableLeft.add(image).grow();
            }
        });
        
        tableRight.row();
        textButton = new TextButton("Multiple Stretch", skin);
        tableRight.add(textButton);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                tableLeft.clear();
                tableLeft.setBackground(skin.getDrawable("black"));
                
                tableLeft.defaults().growX();
                Image image = new Image(skin, "music-ten");
                image.setScaling(Scaling.stretch);
                tableLeft.add(image).growX();
                
                tableLeft.row();
                image = new Image(skin, "skull-ten");
                image.setScaling(Scaling.stretch);
                tableLeft.add(image).growX();
            }
        });
        
        tableRight.row();
        textButton = new TextButton("Tinted", skin);
        tableRight.add(textButton);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                tableLeft.clear();
                tableLeft.setBackground((Drawable) null);
                
                Image image = new Image(skin, "brick-wall-ten-tinted");
                image.setScaling(Scaling.stretch);
                tableLeft.add(image).grow();
            }
        });
        
        tableRight.row();
        textButton = new TextButton("Animation", skin);
        tableRight.add(textButton);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                tableLeft.clear();
                tableLeft.setBackground((Drawable) null);
                
                Image image = new Image(skin, "eye-animation");
                image.setScaling(Scaling.stretch);
                tableLeft.add(image).grow();
            }
        });
        
        tableRight.row();
        textButton = new TextButton("Textured Window", skin);
        tableRight.add(textButton);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                tableLeft.clear();
                tableLeft.setBackground((Drawable) null);
                
                Image image = new Image(skin, "window-ten");
                image.setScaling(Scaling.stretch);
                tableLeft.add(image).grow();
            }
        });
        
        tableRight.row();
        textButton = new TextButton("Progress Bar", skin);
        tableRight.add(textButton);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                tableLeft.clear();
                tableLeft.setBackground((Drawable) null);
                
                progressBar = new ProgressBar(0, 100, 1, false, skin);
                tableLeft.add(progressBar).growX();
            }
        });
    
        tableRight.row();
        textButton = new TextButton("Scrolling Tiles", skin);
        tableRight.add(textButton);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                tableLeft.clear();
                tableLeft.setBackground((Drawable) null);
            
                TenPatchDrawable tenPatchDrawable = skin.get("sand-ten", TenPatchDrawable.class);
                tenPatchDrawable.setOffsetSpeed(20);
                Image image = new Image(tenPatchDrawable);
                image.setScaling(Scaling.stretch);
                tableLeft.add(image).grow();
            }
        });
    
        tableRight.row();
        textButton = new TextButton("Scrolling Animation", skin);
        tableRight.add(textButton);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                tableLeft.clear();
                tableLeft.setBackground((Drawable) null);
    
                Image image = new Image(skin, "eye-animation-scrolling");
                image.setScaling(Scaling.stretch);
                tableLeft.add(image).grow();
            }
        });
    
        tableRight.row();
        textButton = new TextButton("Gradient", skin);
        tableRight.add(textButton);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                tableLeft.clear();
                tableLeft.setBackground((Drawable) null);
                
                TenPatchDrawable tenPatchDrawable = skin.get("white-ten", TenPatchDrawable.class);
                tenPatchDrawable.setColors(Color.PURPLE, Color.BLUE, Color.BLUE, Color.PURPLE);
                Image image = new Image(tenPatchDrawable);
                image.setScaling(Scaling.stretch);
                tableLeft.add(image).grow();
            }
        });
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
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
        skin.dispose();
    }
}
