package app.box.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
    Table container;
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

        // table that holds the scroll pane
        container = new Table();
        container.setWidth(Gdx.graphics.getWidth()/4);


        //inner table that is used as a makeshift list.
        Table innerContainer = new Table();
        //innerContainer.setBackground(new TextureRegionDrawable(new TextureRegion( new Texture(Gdx.files.internal("buttons.png")))));
        for (int i = 0;i<objects.size();i++) {
            Objects obj = objects.get(i);
            Table table = new Table(skin);
            table.add(new Image(obj.getTexture())).expandY().fillY();
            table.getCells().get(0).size(40, 40);
            table.add(new Label("", skin)).width(10f).expandY().fillY();// a spacer
            table.add(new Label(obj.getName(), skin)).expandY().fillY();
            table.getCells().get(2).size(container.getWidth() * 2 / 3, 40);
            table.addListener(new Listener(controller, 11));
            innerContainer.add(table).expand().fill();
            innerContainer.row();
        }

        if (Gdx.graphics.getHeight() > 44*objects.size()){
            container.setHeight(43*objects.size());
            container.setPosition(20, Gdx.graphics.getHeight() - 44*objects.size()-20);
        }
        else {
            container.setHeight(Gdx.graphics.getHeight());
            container.setPosition(20, 20);
        }




        // create the scrollpane
        ScrollPane scrollpane = new ScrollPane(innerContainer);

        //add the scroll pane to the container
        container.add(scrollpane).fill().expand();


        // add container to the stage
        stage.addActor(container);

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
}