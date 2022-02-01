package com.spaceinvaders.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ScreenUtils;

import sun.jvm.hotspot.gc.shared.Space;

/**
 *  Noah Newton
 *  Space Invaders Clone for CSC455
 *  Homework #1
 *  Credits for Images and Sounds
 *
 *
 *
 */

public class MainMenuScreen implements Screen {

    final SpaceInvaders game;
    OrthographicCamera camera;

    public MainMenuScreen(final SpaceInvaders space) {
        game = space;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800,400);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0.2f,1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Welcome to My Space Invaders Clone" , 100, 150);
        game.font.draw(game.batch, "Press space to play!!!", 100, 100);
        game.batch.end();

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            game.setScreen(new GameScreen(game));
            dispose();
        }

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
