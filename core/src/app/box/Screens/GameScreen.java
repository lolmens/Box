package app.box.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

import app.box.Controller;
import app.box.Listeners.Listener;
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

        stage = new Stage();
        /*//пока рано...
        WalkingControl walkingControl = new WalkingControl(new Vector2(250,300),controller);
        stage.addActor(walkingControl);*/

        ImageButton button_back = new ImageButton(controller.getManager().load_Style("back"));
        button_back.setSize(30, 20);// размер кнопки
        button_back.setPosition(2, Gdx.graphics.getHeight()-(button_back.getHeight()+2));//x,y
        button_back.addListener(new Listener(controller, 20));

        //Добавить кнопку возврата на сцену
        stage.addActor(button_back);

        //Добавить сцену в слушатели
        controller.multiplexer.addProcessor(stage);
        //Gdx.input.setInputProcessor(stage);
        world = new World(controller);



    }

    @Override
    public void render(float delta) {

        world.update(delta);
        stage.draw();
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        controller.multiplexer.removeProcessor(stage);
        world.dispose();
        stage.dispose();
    }
}
