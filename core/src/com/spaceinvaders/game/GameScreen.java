package com.spaceinvaders.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.Iterator;
import sun.jvm.hotspot.gc.shared.Space;

/**
 *  Noah Newton
 *  Space Invaders Clone for CSC455
 *  Homework #1
 *  Credits for Images and Sounds
 *
 *  explode.mp3 - Arcade space shooter dead notification
 *      (https://mixkit.co/free-sound-effects/game-over/) under the Mixkit License
 *      (https://mixkit.co/license/#sfxFree)
 *
 *  gameover.mp3 - Arcade retro game over
 *      (https://mixkit.co/free-sound-effects/game-over/) under the Mixkit License
 *      (https://mixkit.co/license/#sfxFree)
 *
 *  space.mp3 -
 *
 *
 *  alien.png - 6 Space Invaders
 *      (https://www.pngfind.com/mpng/iRxihw_600-x-600-6-space-invaders-alien-sprites/)
 *      under personal use
 *
 *  laser.png -
 *      under the Attribution-NonCommercial 4.0 International
 *      (https://creativecommons.org/licenses/by-nc/4.0/legalcode)
 *
 *  spaceship.png -
 *      under the Attribution-NonCommercial 4.0 International
 *      (https://creativecommons.org/licenses/by-nc/4.0/legalcode)
 */

public class GameScreen implements Screen {
    final SpaceInvaders game;
    Texture spaceshipImage;
    Texture alienImage;
    Texture laserImage;
    Sound explode;
    Sound gameOver;
    Music spaceTheme;
    OrthographicCamera camera;
    Rectangle spaceship;
    Array<Rectangle> aliens;
    Array<Rectangle> lasers;
    long lastAlienTime;
    long lastLaserTime;
    int points;

    public GameScreen(final SpaceInvaders space) {
        this.game = space;
        // load the images for spaceship and aliens 64x64 for spaceship and aliens then
        //      7x20 for the laser
        spaceshipImage = new Texture(Gdx.files.internal("spaceship.png"));
        alienImage = new Texture(Gdx.files.internal("alien.png"));
        laserImage = new Texture(Gdx.files.internal("laser.png"));

        // load sound effects eventually
        explode = Gdx.audio.newSound(Gdx.files.internal("explode.mp3"));
        spaceTheme = Gdx.audio.newMusic(Gdx.files.internal("space.mp3"));
        gameOver = Gdx.audio.newSound(Gdx.files.internal("gameover.mp3"));
        spaceTheme.setLooping(true);

        // create the camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800,480);

        // create rectangle to logically represent the spaceship
        spaceship = new Rectangle();
        spaceship.x = 800 / 2 - 64 / 2;
        spaceship.y = 20;

        spaceship.width = 64;
        spaceship.height = 64;

        //add loading the assets and create camera, spritebatch, rectangle for ship, spawn of enemies

        // instantiate alien array
        aliens = new Array<Rectangle>();
        // instantiate laser array
        lasers = new Array<Rectangle>();
        spawnAliens();


    }

    private void spawnAliens() {
        float space = 200;
        for(int i = 0; i < 6; i++) {
            Rectangle alien = new Rectangle();
            alien.x = space;
            space = alien.x + 66;
            alien.y = 480;

            alien.width = 64;
            alien.height = 64;
            aliens.add(alien);
        }
        lastAlienTime = TimeUtils.nanoTime();
    }

    private void spawnLaser() {
        Rectangle laser = new Rectangle();
        laser.x = spaceship.x + 28;
        laser.y = 104;

        laser.width = 7;
        laser.height = 20;
        lasers.add(laser);
        lastLaserTime = TimeUtils.nanoTime();
    }

    @Override
    public void show() {
        // music playback
        spaceTheme.play();
    }

    @Override
    public void render(float delta) {
        // set screen to black
        ScreenUtils.clear(Color.BLACK);

        // update camera
        camera.update();

        //coordinate the SpriteBatch with camera
        game.batch.setProjectionMatrix(camera.combined);

        // begin new batch and draw spaceship
        game.batch.begin();
        // draw points/aliens killed
        game.font.draw(game.batch, "Points: " + points, 10, 470);
        // draw laser
        game.batch.draw(spaceshipImage, spaceship.x, spaceship.y);
        // draw aliens
        for(Rectangle alien : aliens){
            game.batch.draw(alienImage, alien.x, alien.y);
        }
        for(Rectangle laser: lasers) {
            game.batch.draw(laserImage, laser.x, laser.y);
        }
        game.batch.end();

        // process user input
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            spaceship.x = touchPos.x - 64 / 2;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            spaceship.x -= 200 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            spaceship.x += 200 * Gdx.graphics.getDeltaTime();
        }

        // up for shooting laser
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if(TimeUtils.nanoTime() - lastLaserTime > 0.25 * 1000000000) {
                spawnLaser();
            }
        }

        // make sure spaceships stays on screen
        if(spaceship.x < 0) { spaceship.x = 0; }
        if(spaceship.x > 800 - 64){ spaceship.x = 800 -64; }

        // spawn new aliens check point total whether or not to speed up
        if(points < 21){
            if(TimeUtils.nanoTime() - lastAlienTime > 2 * 1000000000) { spawnAliens(); }
        }
        else {
            if(TimeUtils.nanoTime() - lastAlienTime > 1.5 * 1000000000) { spawnAliens(); }
        }

        // move laser up screen and detect collisions. if collision detected removes laser and alien
        Iterator<Rectangle> iterLaser = lasers.iterator();
        while(iterLaser.hasNext()) {
            Rectangle laser = iterLaser.next();
            laser.y += 400 * Gdx.graphics.getDeltaTime();
            Iterator<Rectangle> iterAlien = aliens.iterator();
            while(iterAlien.hasNext()) {
                Rectangle alien = iterAlien.next();
                if(laser.overlaps(alien) && !lasers.isEmpty()){
                    iterAlien.remove();
                    points++;
                    iterLaser.remove();
                    explode.play();
                }
            }

        }

        // move the aliens down screen, later make sure they die
        Iterator<Rectangle> iter = aliens.iterator();
        while(iter.hasNext()) {
            Rectangle alien = iter.next();
            // check points to see if speed needs to be increased
            if(points < 21) {
                alien.y -= 20 * Gdx.graphics.getDeltaTime();
            } else
                alien.y -= 30 * Gdx.graphics.getDeltaTime();

            if(alien.y < 0 || alien.overlaps(spaceship)){
                gameOver.play();
                spaceTheme.pause();
                game.setScreen(new GameOverScreen(game));
            }
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
        spaceshipImage.dispose();
        alienImage.dispose();
        explode.dispose();
        gameOver.dispose();
        spaceTheme.dispose();
    }
}
