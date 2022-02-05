package com.spaceinvaders.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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
    Texture spaceshipImage;
    Texture alienImage;

    public MainMenuScreen(final SpaceInvaders space) {
        game = space;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800,400);

        spaceshipImage = new Texture(Gdx.files.internal("spaceship.png"));
        alienImage = new Texture(Gdx.files.internal("alien.png"));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "NOAH NEWTON'S  SPACE INVADERS" , 265, 250);
        game.font.setColor(Color.GREEN);
        game.font.draw(game.batch, "PRESS ANY BUTTON TO PLAY !!!", 280, 225);
        game.batch.draw(spaceshipImage, 800 / 2 - 64 / 2, 70);
        game.batch.draw(alienImage,200, 300);
        game.batch.draw(alienImage,266, 300);
        game.batch.draw(alienImage,332, 300);
        game.batch.draw(alienImage,398, 300);
        game.batch.draw(alienImage,464, 300);
        game.batch.draw(alienImage,530, 300);
        game.batch.end();

        if(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)){
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
