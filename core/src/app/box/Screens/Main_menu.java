package app.box.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import app.box.Controller;
import app.box.Listeners.Listener;


/**
 * Created by user on 30.03.16.
 */
public class Main_menu implements Screen {

    private Stage stage;//сцена
    private OrthographicCamera camera;//камера
    private Controller controller;
    private Skin skin;

    public Main_menu(Controller controll) {
        this.controller = controll;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        stage = new Stage();
        stage = new Stage();
        skin = controller.skin;

    }

    @Override
    public void show() {
        // Таблица, которая содержит панель прокрутки
        Table container_one, container_two;
        container_one = new Table();
        container_two = new Table();

        container_one.setWidth(Gdx.graphics.getWidth() / 3 - 20);
        container_two.setWidth(Gdx.graphics.getWidth() / 3 - 20);

        container_one.setHeight(Gdx.graphics.getHeight() - 40);
        container_two.setHeight(Gdx.graphics.getHeight() - 40);

        container_one.setPosition(Gdx.graphics.getWidth() / 6, 20);
        container_two.setPosition(Gdx.graphics.getWidth() / 2.75f, 20);

        // Внутренняя таблица, которая используется в качестве импровизированного списка.
        Table innerContainer_one = new Table();
        Table innerContainer_two = new Table();

        // Cписок названий кнопок
        String[] buttons_list = {"save_m", "contact", "how", "box", "obj_m", "example"};
        String[] spaser_list = {" ", " ", " "};

        // Ну как сказать...
        for (int i = 0; i < buttons_list.length; i++) {
            Table table = new Table(skin);
            ImageButton imageButton = new ImageButton(controller.getManager().load_Style(buttons_list[i]));
            controller.getManager().load_Style((i <= buttons_list.length / 2 - 1) ? buttons_list[i] : buttons_list[buttons_list.length - i]);
            imageButton.setSize(200, 50);// размер кнопки
            imageButton.setPosition(0, 0);//x,y
            imageButton.addListener(new Listener(controller, i+1));
            if (i <= 2) {
                table.add(imageButton).expand().fill();//button
                table.add(new Label("", skin)).width(10).expandY().fillY();//spaser
                table.getCells().get(0).size(200, 50);
                innerContainer_one.add(table).expand().fill();
                innerContainer_one.row();
            }
            else if (i > 2)
            {
                table.add(new Label("", skin)).width(10).expandY().fillY();//spaser
                table.add(imageButton).expandY().fill();//button
                table.getCells().get(1).size(200, 50);
                innerContainer_two.add(table).expand().fill();
                innerContainer_two.row();
            }
        }
            //Создание панелей прокрутки
            ScrollPane scrollpane_one = new ScrollPane(innerContainer_one);
            ScrollPane scrollpane_two = new ScrollPane(innerContainer_two);

            //Добавиление панели прокрутки в контейнер
            container_one.add(scrollpane_one).fill().expand();
            container_two.add(scrollpane_two).fill().expand();

            //Добавить контейнер на сцену
            //stage.addActor(container_one);//in future
            stage.addActor(container_two);

            //Установка процессора (получает щелчки и прочее)
            //Gdx.input.setInputProcessor(stage);
            controller.multiplexer.addProcessor(stage);
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
        controller.multiplexer.removeProcessor(stage);
        stage.dispose();
    }


}
