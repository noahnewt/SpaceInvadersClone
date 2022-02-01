package com.spaceinvaders.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 *  Noah Newton
 *  Space Invaders Clone for CSC455
 *  Homework #1
 *  Credits for Images and Sounds
 *
 *
 *
 */

public class GameOverScreen implements Screen {

    final SpaceInvaders game;
    OrthographicCamera camera;

    public GameOverScreen(final SpaceInvaders space){
        game = space;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800,400);
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
        game.font.draw(game.batch, "G A M E     O V E R" , 100, 150);
        game.font.draw(game.batch, "Press any button to play!!!", 100, 100);
        game.batch.end();

        if(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)){
            game.setScreen(new MainMenuScreen(game));
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
