package com.ray3k.tenpatch.demo.desktop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ray3k.tenpatch.AnimatedTenPatchDrawable;

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
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        new LwjglApplication(new Demo(), config);
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
        textButton = new TextButton("Animated", skin);
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
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        float delta = Gdx.graphics.getDeltaTime();
        for (Entry<String, AnimatedTenPatchDrawable> entry: skin.getAll(AnimatedTenPatchDrawable.class)) {
            entry.value.update(delta);
        }
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
        skin.dispose();
    }
}
