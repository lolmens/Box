package app.box.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

import app.box.Controller;
import app.box.Listener;


/**
 * Created by user on 30.03.16.
 */
public class Main_menu implements Screen {

    private Stage stage;//сцена
    private OrthographicCamera camera;//камера
    private Controller controller;

    public Main_menu(Controller controll) {
        this.controller = controll;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        float x = Gdx.graphics.getWidth() / 160;
        float y = Gdx.graphics.getHeight() / 96;

        ImageButton button_Box = new ImageButton(load_Style("box"));
        button_Box.setSize(20 * x, 10 * y);// размер кнопки
        button_Box.setPosition(138 * x, 72 * y);//x,y
        button_Box.addListener(new Listener(controller, 4));

        ImageButton button_ed_m = new ImageButton(load_Style("obj_m"));
        button_ed_m.setSize(44 * x, 10 * y);// размер кнопки
        button_ed_m.setPosition(112 * x, 41 * y);//x,y
        button_ed_m.addListener(new Listener(controller, 5));

        ImageButton button_save = new ImageButton(load_Style("save_m"));
        button_save.setSize(70 * x, 10 * y);// размер кнопки
        button_save.setPosition(86 * x, 15 * y);//x,y
        button_save.addListener(new Listener(controller, 6));

        ImageButton button_exm = new ImageButton(load_Style("example"));
        button_exm.setSize(20 * x, 10 * y);// размер кнопки
        button_exm.setPosition(4 * x, 15 * y);//x,y
        button_exm.addListener(new Listener(controller, 3));

        ImageButton button_cont = new ImageButton(load_Style("contact"));
        button_cont.setSize(44 * x, 10 * y);// размер кнопки
        button_cont.setPosition(4 * x, 41 * y);//x,y
        button_cont.addListener(new Listener(controller, 2));

        ImageButton button_how = new ImageButton(load_Style("how"));
        button_how.setSize(70 * x, 10 * y);// размер кнопки
        button_how.setPosition(4 * x, 72 * y);//x,y
        button_how.addListener(new Listener(controller, 1));




        stage.addActor(button_Box);
        stage.addActor(button_ed_m);
        stage.addActor(button_save);
        stage.addActor(button_exm);
        stage.addActor(button_cont);
        stage.addActor(button_how);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1);//***
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
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


    private ImageButton.ImageButtonStyle load_Style(String name) {
        ImageButton.ImageButtonStyle button = new ImageButton.ImageButtonStyle();
        button.up = controller.skin_buttons.getDrawable("button_" + name + "_up");//кнопка не нажата
        button.over = controller.skin_buttons.getDrawable("button_" + name + "_over");//на кнопку навели
        button.down = controller.skin_buttons.getDrawable("button_" + name + "_down"); // кнопка нажата
        return button;
    }

}
