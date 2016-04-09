package app.box.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import app.box.Controller;
import app.box.WalkingControl;
import app.box.World;

/**
 * Created by user on 01.04.16.
 */
public class GameScreen implements Screen {
    private World world;
    private Controller controller;
    Stage stage;

    public GameScreen(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void show() {
        world = new World(controller);

        stage = new Stage();
        stage.addActor(new WalkingControl(new Vector2(50,300),controller));
        //Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {

        world.update(delta);
        stage.draw();
        stage.act(delta);
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
