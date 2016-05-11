package app.box.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import app.box.Controller;

/**
 * Created by user on 10.05.16.
 */
public class Example implements Screen {
    private Controller controller;
    private Skin skin;
    private Stage stage;
    private float scaleWight, scaleHeight;

    public Example(Controller controller) {
        this.controller = controller;
        skin = controller.skin;
        stage = new Stage();
        controller.multiplexer.addProcessor(stage);
        scaleWight = controller.getScaleW();
        scaleHeight = controller.getScaleH();

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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
