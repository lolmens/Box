package app.box.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;


import java.util.ArrayList;

import app.box.Controller;
import app.box.Listeners.Listener;
import app.box.Obj.Objects;

/**
 * Created by user on 10.05.16.
 */
public class Example implements Screen {
    private Controller controller;
    private Skin skin;
    private Stage stage;
    private float scaleWight, scaleHeight;
    private ImageButton button_back;

    public Example(Controller controller) {
        this.controller = controller;
        skin = controller.skin;
        stage = new Stage();
        controller.multiplexer.addProcessor(stage);
        scaleWight = controller.getScaleW();
        scaleHeight = controller.getScaleH();
        button_back = new ImageButton(controller.getManager().load_Style("back"));
        button_back.setSize(30 * scaleWight, 20 * scaleHeight);// размер кнопки
        button_back.setPosition(2 * scaleWight, Gdx.graphics.getHeight() - (button_back.getHeight() + 2 * scaleHeight));//x,y
        button_back.addListener(new Listener(controller, 20));
    }

    @Override
    public void show() {
        stage.addActor(button_back);
        Table container = new Table();
        container.setWidth(Gdx.graphics.getWidth() / 4);
        Table table = createTable();
        ScrollPane scrollpane = new ScrollPane(table);
        container.add(scrollpane).left().top();
        container.setHeight(table.getHeight());
        container.setPosition(20 * scaleWight, Gdx.graphics.getHeight() - container.getHeight() - 20 * scaleHeight);
        container.addListener(new Listener(controller, 12));
        stage.addActor(container);
    }
    public void set(){
        ModelLoader loader = new ObjLoader();
        Objects object = new Objects("Samsung", new Texture("obj_img/default.png"));
        object.setVisible(true);
        object.createModel(loader.loadModel(Gdx.files.internal("models/samsunglogo.obj")), Color.BLUE);
        object.moving(0, 14, 0);
        object.rotation(0, 35, 0);
        controller.obj.add(object);
    }
    public Table createTable() {
        ArrayList<example> examples = new ArrayList<example>();
        examples.add(new example("Samsung ", new Texture("obj_img/default.png"), "logo"));

        Table table = new Table(skin);
        for (example example : examples) {
            table.add(new Image(example.img)).size(40 * scaleWight, 40 * scaleHeight).expandY().fillY();
            table.add(new Label("", skin)).width(10f).expandY().fillY();//разделитель
            table.add(new Label(example.labelName, skin)).expandY().fillY();
            table.add(new Label(example.labelText, skin)).expandY().fillY();
        }

        if (Gdx.graphics.getHeight() > 44 * scaleHeight * examples.size()) {
            table.setHeight(43 * scaleHeight * examples.size());
            table.setPosition(20 * scaleWight, Gdx.graphics.getHeight() - 44 * scaleHeight * examples.size() - 20 * scaleHeight);
        } else {
            table.setHeight(Gdx.graphics.getHeight() - 40 * scaleHeight);
        }
        stage.addActor(table);
        return table;
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
        stage.dispose();
    }

    class example {
        example(String name, Texture img, String text) {
            this.img = img;
            labelName = name;
            labelText = text;
        }

        public Texture img;
        public String labelName;
        public String labelText;
    }
}
