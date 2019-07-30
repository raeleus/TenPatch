package com.ray3k.tenpatch.demo.desktop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ray3k.tenpatch.TenPatchDrawable;

/**
 * This class demonstrates the use of a TenPatchDrawable animation with a specified PlayMode.
 * @author Raymond
 * @see SkinExample
 */
public class PlayModeExample extends ApplicationAdapter {
    private Stage stage;
    private Skin skin;

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        new LwjglApplication(new PlayModeExample(), config);
    }

    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal("tenpatch.json"));
        
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        
        root.pad(100);
        root.defaults().space(10);
        final TenPatchDrawable animation = skin.get("loading-animation", TenPatchDrawable.class);
        Image image = new Image(animation);
        image.setScaling(Scaling.none);
        root.add(image);
        
        root.row();
        final SelectBox<String> selectBox = new SelectBox<String>(skin);
        selectBox.setItems("Normal", "Reversed", "Loop", "Loop Reversed", "Loop Ping Pong", "Loop Random");
        selectBox.setSelectedIndex(2);
        root.add(selectBox);
        selectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                switch (selectBox.getSelectedIndex()) {
                    case 0:
                        animation.playMode = TenPatchDrawable.PlayMode.NORMAL;
                        break;
                    case 1:
                        animation.playMode = TenPatchDrawable.PlayMode.REVERSED;
                        break;
                    case 2:
                        animation.playMode = TenPatchDrawable.PlayMode.LOOP;
                        break;
                    case 3:
                        animation.playMode = TenPatchDrawable.PlayMode.LOOP_REVERSED;
                        break;
                    case 4:
                        animation.playMode = TenPatchDrawable.PlayMode.LOOP_PINGPONG;
                        break;
                    case 5:
                        animation.playMode = TenPatchDrawable.PlayMode.LOOP_RANDOM;
                        break;
                }
            }
        });
        
        root.row();
        TextButton textButton = new TextButton("Play Again", skin);
        root.add(textButton);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                animation.setTime(0);
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
