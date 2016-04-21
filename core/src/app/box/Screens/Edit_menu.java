package app.box.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

import app.box.Controller;
import app.box.Listeners.Listener;
import app.box.Obj.Objects;


/**
 * Created by user on 31.03.16.
 */
public class Edit_menu implements Screen {

    private Controller controller;
    private Skin skin;
    private Stage stage;
    private ArrayList<Objects> objects;
    public Table table_with_background = null;
    private Drawable background = new TextureRegionDrawable(new TextureRegion(new Texture("blue.png")));
    private TextField[] textFields = new TextField[4];//x,y,z,w

    public Edit_menu(Controller controller) {
        this.controller = controller;
        controller.set_edit_menu(this);
        objects = controller.obj;
        skin = controller.skin;
        stage = new Stage();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1);    //sets up the clear color (background color) of the screen.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);  //instructs openGL to actually clear the screen to the newly set clear color.
        //System.out.println(list.getSelected().toString());
        stage.draw();
        stage.act(delta);

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        //таблица, которая содержит панель прокрутки
        Table container_one, container_two;
        container_one = new Table();
        container_two = new Table();
        container_one.setWidth(Gdx.graphics.getWidth() / 4);
        container_two.setWidth(Gdx.graphics.getWidth() / 2 - 20);

        // Внутренняя таблица, которая используется в качестве импровизированного списка.
        Table innerContainer_one = new Table();
        Table innerContainer_two = new Table();
        //innerContainer.setBackground(new TextureRegionDrawable(new TextureRegion( new Texture(Gdx.files.internal("buttons.png")))));
        //заполняем таблицу используя объекты и их содержимое
        byte objects_size = 0;
        for (int i = 0; i < objects.size(); i++) {
            if (!objects.get(i).isVisible()) continue;
            objects_size++;
            Objects obj = objects.get(i);
            Table table = new Table(skin);
            table.add(new Image(obj.getTexture())).expandY().fillY();
            table.getCells().get(0).size(40, 40);
            table.add(new Label("", skin)).width(10f).expandY().fillY();//разделитель
            table.add(new Label(obj.getName(), skin)).expandY().fillY();
            table.getCells().get(2).size(container_one.getWidth() * 2 / 3, 40);
            table.addListener(new Listener(controller, 10));
            table.setName(i + "");
            innerContainer_one.add(table).expand().fill();
            innerContainer_one.row();
        }

        if (Gdx.graphics.getHeight() > 44 * objects_size) {
            container_one.setHeight(43 * objects_size);
            container_one.setPosition(20, Gdx.graphics.getHeight() - 44 * objects_size - 20);
        } else {
            container_one.setHeight(Gdx.graphics.getHeight());
            container_one.setPosition(20, 20);
        }


        String[] buttons = {"edit", "coor", "rot", "col"};
        String[] texts = {
                "It allows you to change the name of the object.",
                "It allows you to change the coordinates of the object in space.",
                "It allows you to rotate an object.",
                "It allows you to change the color and transparency of the object."
        };
        for (int i = 0; i < buttons.length; i++) {
            Table table = new Table(skin);
            ImageButton imageButton = new ImageButton(controller.getManager().load_Style(buttons[i]));
            imageButton.setSize(200, 52);// размер кнопки
            imageButton.setPosition(0, 0);//x,y
            imageButton.addListener(new Listener(controller, 25 + i));
            Label label = new Label(texts[i], skin);
            label.setWrap(true);
            if (i % 2 == 0) {
                table.add(imageButton).expandY().fill();//button
                table.add(new Label("", skin)).width(10).expandY().fillY();//spaser
                table.add(label).expandX().fillX();//text
                table.getCells().get(0).size(200, 52);
            } else {
                table.add(label).expandX().fillX();//text
                table.add(new Label("", skin)).width(10).expandY().fillY();//spaser
                table.add(imageButton).expandY().fill();//button
                table.getCells().get(2).size(200, 52);
            }
            //table.getCells().get(2).size(500,50);
            innerContainer_two.add(table).expand().fill();
            innerContainer_two.row();
        }
        //Пятая "линия"
        Table fife_line = new Table(skin);
        ImageButton imageButton_add = new ImageButton(controller.getManager().load_Style("add"));
        ImageButton imageButton_remove = new ImageButton(controller.getManager().load_Style("remove"));
        imageButton_add.setSize(97, 52);
        imageButton_remove.setSize(152, 52);
        imageButton_remove.addListener(new Listener(controller, 30));
        Label label = new Label("It allows you to add or remove objects.", skin);
        label.setWrap(true);
        Label spaser = new Label("", skin);

        fife_line.add(imageButton_add).expandY().fill();
        fife_line.add(spaser).width(10).expandY().fillY();//spaser
        fife_line.add(imageButton_remove).expandY().fill();
        fife_line.add(spaser).width(10).expandY().fillY();//spaser
        fife_line.add(label).expandX().fillX();

        fife_line.getCells().get(0).size(97, 52);
        fife_line.getCells().get(2).size(152, 52);
        innerContainer_two.add(fife_line).expand().fill();
        innerContainer_two.row();

        container_two.setHeight(Gdx.graphics.getHeight() - 40);
        container_two.setPosition(Gdx.graphics.getWidth() / 2, 20);


        //Создание панелей прокрутки
        ScrollPane scrollpane_one = new ScrollPane(innerContainer_one);
        ScrollPane scrollpane_two = new ScrollPane(innerContainer_two);

        //Добавиление панели прокрутки в контейнер
        container_one.add(scrollpane_one).fill().expand();
        container_two.add(scrollpane_two).fill().expand();


        //Добавить контейнер на сцену
        stage.addActor(container_one);
        stage.addActor(container_two);

        //Создание кнопки назад
        ImageButton button_back = new ImageButton(controller.getManager().load_Style("back"));
        button_back.setSize(30, 20);// размер кнопки
        button_back.setPosition(2, Gdx.graphics.getHeight() - (button_back.getHeight() + 2));//x,y
        button_back.addListener(new Listener(controller, 20));

        //Добавить кнопку возврата на сцену
        stage.addActor(button_back);

        //Установка процессора (получает щелчки и прочее)
        //Gdx.input.setInputProcessor(stage);
        controller.multiplexer.addProcessor(stage);

    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
    //public void update (int id){}

    public Drawable getBackground() {
        return background;
    }

    public void update() {
        table_with_background = null;
        stage.clear();
        show();
    }

    public void rotation() {
        //System.out.println("Rotation");
        Window window = new Window("Edit rotation", skin);
        //window.getTitleTable().add(new TextButton("X", skin)).height(window.getPadTop());
        window.defaults().spaceBottom(10);
        //window.row().fill().expandX();
        String[] texts_name = {"axisX", "axisY", "axisZ", "degrees"};
        String[] texts_enter = {"Enter int", "Enter int", "Enter int", "Enter int"};
        for (int i = 0; i < texts_name.length; i++) {
            Label text = new Label(texts_name[i] + ":", skin);
            textFields[i] = new TextField("", skin);
            textFields[i].setMessageText(texts_enter[i]);
            textFields[i].setAlignment(Align.center);
            window.add(text);
            window.add(textFields[i]);
            window.row();
        }
        ImageButton button_cansel = new ImageButton(controller.getManager().load_Style("cansel"));
        button_cansel.addListener(new Listener(controller, 40));
        ImageButton button_turn = new ImageButton(controller.getManager().load_Style("turn"));
        button_turn.addListener(new Listener(controller, 41));
        window.add(button_turn);
        window.add(button_cansel);
        window.setPosition(Gdx.graphics.getWidth() / 2 - window.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        window.pack();
        stage.addActor(window);
    }
    public boolean edit_rotation() {
        Objects objects = controller.obj.get(Integer.parseInt(table_with_background.getName()));
        try {
            objects.rotation(Integer.parseInt(textFields[0].getText()), Integer.parseInt(textFields[1].getText()), Integer.parseInt(textFields[2].getText()), Integer.parseInt(textFields[3].getText()));
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    public void moving() {
        Window window = new Window("Moving", skin);
        window.defaults().spaceBottom(10);
        String[] texts_name = {"axisX", "axisY", "axisZ"};
        String[] texts_enter = {"Enter int", "Enter int", "Enter int"};
        for (int i = 0; i < texts_name.length; i++) {
            Label text = new Label(texts_name[i] + ":", skin);
            textFields[i] = new TextField("", skin);
            textFields[i].setMessageText(texts_enter[i]);
            textFields[i].setAlignment(Align.center);
            window.add(text);
            window.add(textFields[i]);
            window.row();
        }
        ImageButton button_cansel = new ImageButton(controller.getManager().load_Style("cansel"));
        button_cansel.addListener(new Listener(controller, 40));
        ImageButton button_turn = new ImageButton(controller.getManager().load_Style("move"));
        button_turn.addListener(new Listener(controller, 42));
        window.add(button_turn);
        window.add(button_cansel);
        window.setPosition(Gdx.graphics.getWidth() / 2 - window.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        window.pack();
        stage.addActor(window);
    }
    public boolean edit_moving() {
        Objects objects = controller.obj.get(Integer.parseInt(table_with_background.getName()));
        try {
            objects.moving(Integer.parseInt(textFields[0].getText()), Integer.parseInt(textFields[1].getText()), Integer.parseInt(textFields[2].getText()));
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    public void remove(){
        Label text = new Label("Are you sure that you want to delete "+objects.get(Integer.parseInt(table_with_background.getName())).getName()+"? \n"+
                " There does not provide the ability to recover...", skin);
        ImageButton button_cansel = new ImageButton(controller.getManager().load_Style("cansel"));
        button_cansel.addListener(new Listener(controller, 40));
        ImageButton button_ok = new ImageButton(controller.getManager().load_Style("ok"));
        button_ok.addListener(new Listener(controller, 43));
        button_ok.align(Align.left);

        Window window = new Window("Remove?", skin);
        window.setPosition(Gdx.graphics.getWidth() / 2 - window.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        window.row();
        window.add(text);
        window.row();
        window.add(button_ok).align(Align.right);
        window.add(new Label(" ", skin));
        window.add(button_cansel).align(Align.left);
        window.row();
        window.setPosition(Gdx.graphics.getWidth() / 2 - window.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        window.pack();
        stage.addActor(window);
    }
    public void remove_confirm(){
        objects.get(Integer.parseInt(table_with_background.getName())).destroy();
        objects.remove(Integer.parseInt(table_with_background.getName()));
    }
    public void error_window(String error_msg) {
        Label text = new Label(error_msg, skin);
        ImageButton button = new ImageButton(controller.getManager().load_Style("ok"));
        button.addListener(new Listener(controller, 40));
        Window window = new Window("Error", skin);
        window.setPosition(Gdx.graphics.getWidth() / 2 - window.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        window.add(text);
        window.row();
        window.add(button);
        window.pack();
        stage.addActor(window);
    }

    @Override
    public void dispose() {
        controller.multiplexer.removeProcessor(stage);
        controller.set_edit_menu(null);
        stage.dispose();
    }


}