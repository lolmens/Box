package app.box.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;

import java.util.ArrayList;

import app.box.Controller;
import app.box.Listener;
import app.box.Obj.Objects;


/**
 * Created by user on 31.03.16.
 */
public class Edit_menu implements Screen {
    Controller controller;
    Skin skin;
    Stage stage;

    ArrayList<Objects> objects;
    //List list;

    public Edit_menu(Controller controller) {
        this.controller = controller;
        objects = controller.obj;
        //Gdx.input.setInputProcessor(stage);
        stage = new Stage();
        skin = controller.skin;
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
        Table container_one, container_two;
        // table that holds the scroll pane
        container_one = new Table();
        container_two = new Table();

        container_one.setWidth(Gdx.graphics.getWidth()/4);
        container_two.setWidth(Gdx.graphics.getWidth()/3);

        //inner table that is used as a makeshift list.
        Table innerContainer_one = new Table();
        Table innerContainer_two = new Table();
        //innerContainer.setBackground(new TextureRegionDrawable(new TextureRegion( new Texture(Gdx.files.internal("buttons.png")))));
        for (int i = 0;i<objects.size();i++) {
            Objects obj = objects.get(i);
            Table table = new Table(skin);
            table.add(new Image(obj.getTexture())).expandY().fillY();
            table.getCells().get(0).size(40, 40);
            table.add(new Label("", skin)).width(10f).expandY().fillY();// a spacer
            table.add(new Label(obj.getName(), skin)).expandY().fillY();
            table.getCells().get(2).size(container_one.getWidth() * 2 / 3, 40);
            table.addListener(new Listener(controller, 10));
            table.setName(i + "");
            innerContainer_one.add(table).expand().fill();
            innerContainer_one.row();
        }

        if (Gdx.graphics.getHeight() > 44*objects.size()){
            container_one.setHeight(43*objects.size());
            container_one.setPosition(20, Gdx.graphics.getHeight() - 44*objects.size()-20);
        }
        else {
            container_one.setHeight(Gdx.graphics.getHeight());
            container_one.setPosition(20, 20);
        }


        String[] buttonts = {"edit","coor","rot"};
        for (String button : buttonts){
            Table table = new Table(skin);
            ImageButton imageButton = new ImageButton(load_Style(button));
            imageButton.setSize(200, 50);// размер кнопки
            imageButton.setPosition(0, 0);//x,y
            imageButton.addListener(new Listener(controller, 20));
            table.add(imageButton).expandY().fillY();
            table.getCells().get(0).size(200,50);
            innerContainer_two.add(table).expand().fill();
            innerContainer_two.row();
        }
        container_two.setHeight(Gdx.graphics.getHeight());
        container_two.setPosition(Gdx.graphics.getWidth()/2, 20);


        // create the scrollpane
        ScrollPane scrollpane_one = new ScrollPane(innerContainer_one);
        ScrollPane scrollpane_two = new ScrollPane(innerContainer_two);

        //add the scroll pane to the container
        container_one.add(scrollpane_one).fill().expand();
        container_two.add(scrollpane_two).fill().expand();


        // add container to the stage
        stage.addActor(container_one);
        stage.addActor(container_two);

        // setup input processor (gets clicks and stuff)
        Gdx.input.setInputProcessor(stage);
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

    @Override
    public void dispose() {
    }

    private ImageButton.ImageButtonStyle load_Style(String name) {
        ImageButton.ImageButtonStyle button = new ImageButton.ImageButtonStyle();
        button.up = controller.skin_buttons.getDrawable("button_" + name + "_up");//кнопка не нажата
        button.over = controller.skin_buttons.getDrawable("button_" + name + "_up");//на кнопку навели
        button.down = controller.skin_buttons.getDrawable("button_" + name + "_down"); // кнопка нажата
        return button;
    }
}