package app.box.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import app.box.Controller;
import app.box.World;

/**
 * Created by user on 01.04.16.
 */
public class GameScreen implements Screen {
    private World world;
    private Controller controller;

    public GameScreen(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void show() {
        world = new World(controller);
    }

    @Override
    public void render(float delta) {
        world.update(delta);
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
