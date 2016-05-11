package app.box.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.StringBuilder;

import app.box.Color.AlphaImage;
import app.box.Controller;
import app.box.Listeners.GameScreenListener;
import app.box.Listeners.Listener;
import app.box.Obj.Objects;
import app.box.World;

/**
 * Created by user on 01.04.16.
 */
public class GameScreen implements Screen {
    private World world;
    private Controller controller;
    private Stage stage;
    private Skin skin;
    private StringBuilder stringBuilder;
    private Label FPS;
    public int selected = -1, selecting = -1;
    private GameScreenListener gameScreenListener;
    private ImageButton button_back;
    private float scaleWight, scaleHeight;

    TextField[] CoordinateTextFields;
    TextField[] RotationTextFields;
    Image image2;

    public GameScreen(Controller controller) {
        this.controller = controller;
        skin = controller.skin;

        stage = new Stage();

        scaleHeight = controller.getScaleH();
        scaleWight = controller.getScaleW();

        stringBuilder = new StringBuilder();
        FPS = new Label(" ", controller.skin);

        gameScreenListener = new GameScreenListener(this);
        controller.multiplexer.addProcessor(stage);//Добавить сцену в слушатели
        controller.multiplexer.addProcessor(gameScreenListener);
        world = new World(controller);//world тоже создаёт слушателя,его лсушатель должен быть последним!

        button_back = new ImageButton(controller.getManager().load_Style("back"));
        button_back.setSize(30 * scaleWight, 20 * scaleHeight);// размер кнопки
        button_back.setPosition(2 * scaleWight, Gdx.graphics.getHeight() - (button_back.getHeight() + 2 * scaleHeight));//x,y
        button_back.addListener(new Listener(controller, 20));
    }

    @Override
    public void show() {
        stage.clear();
        selected = -1;
        stage.addActor(FPS);
        //Добавить кнопку возврата на сцену
        stage.addActor(button_back);
    }

    public void setSelected(int value) {
        if (selected == value) return;
        if (selected >= 0) {
            show();
        }
        selected = value;
        if (selected >= 0) {
            createWindows(selected);
        }
    }

    public void createWindows(int objNumber) {
        Objects object = controller.obj.get(objNumber);
        Vector3 Translation = object.getMoving(new Vector3());
        Vector3 Rotation = object.getRotation();

        String[] texts_name = {"axisX", "axisY", "axisZ"};
        String[] texts_enter = {"Enter int", "Enter int", "Enter int"};

        CoordinateTextFields = new TextField[texts_name.length];//x,y,x
        RotationTextFields = new TextField[texts_name.length];

        int[] CoordinateForTextFields = {(int) Translation.x, (int) Translation.y, (int) Translation.z};
        int[] RotationForTextFields = {(int) Rotation.x, (int) Rotation.y, (int) Rotation.z};

        Pixmap whitePixmap = new Pixmap(2, 2, Pixmap.Format.RGB888);
        whitePixmap.setColor(Color.WHITE);
        whitePixmap.drawRectangle(0, 0, 2, 2);
        Texture whiteTexture = new Texture(whitePixmap);
        whiteTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        whitePixmap.dispose();

        Image image1 = new AlphaImage(whiteTexture, 5);
        image2 = new AlphaImage(whiteTexture, 5);
        image1.setColor(object.getColor());
        image2.setColor(object.getColor());

        final Slider slider = new Slider(0f, 10f, 1f, false, skin);
        slider.setAnimateDuration(0.3f);
        slider.setValue(image1.getColor().a * 10);

        final Label transparencyLabel = new Label("Transparency : " + image1.getColor().a, skin);
        Label hexLabel = new Label("Enter HEX value", skin);
        TextField Hex = new TextField("", skin);
        Hex.setMessageText("#...");

        Table table1 = new Table(skin), table2 = new Table(skin);

        ImageButton[] buttonsCancel = {new ImageButton(controller.getManager().load_Style("cancel")), new ImageButton(controller.getManager().load_Style("cancel")), new ImageButton(controller.getManager().load_Style("cancel"))};
        ImageButton buttonMove = new ImageButton(controller.getManager().load_Style("move"));
        ImageButton buttonTurn = new ImageButton(controller.getManager().load_Style("turn"));
        ImageButton buttonPaint = new ImageButton(controller.getManager().load_Style("paint"));
        buttonPaint.align(Align.left);

        Window MovingWindow = new Window("Moving", skin);
        MovingWindow.defaults().spaceBottom(10);
        for (int i = 0; i < texts_name.length; i++) {
            Label text = new Label(texts_name[i] + ":", skin);
            CoordinateTextFields[i] = new TextField("", skin);
            CoordinateTextFields[i].setMessageText(texts_enter[i]);
            CoordinateTextFields[i].setText(CoordinateForTextFields[i] + "");
            CoordinateTextFields[i].setAlignment(Align.center);
            MovingWindow.add(text);
            MovingWindow.add(CoordinateTextFields[i]).size(Gdx.graphics.getWidth() / 10, CoordinateTextFields[i].getHeight());
            MovingWindow.row();
        }
        MovingWindow.add(buttonMove).size(102 * scaleWight, 27 * scaleHeight);
        MovingWindow.add(buttonsCancel[0]).size(102 * scaleWight, 27 * scaleHeight);
        MovingWindow.setPosition(Gdx.graphics.getWidth() * 0.05f, Gdx.graphics.getHeight() * 0.9f - MovingWindow.getHeight());
        MovingWindow.pack();

        Window RotationWindow = new Window("Rotation", skin);
        RotationWindow.defaults().spaceBottom(10);
        for (int i = 0; i < texts_name.length; i++) {
            Label text = new Label(texts_name[i] + ":", skin);
            RotationTextFields[i] = new TextField("", skin);
            RotationTextFields[i].setMessageText(texts_enter[i]);
            RotationTextFields[i].setText(RotationForTextFields[i] + "");
            RotationTextFields[i].setAlignment(Align.center);
            RotationWindow.add(text);
            RotationWindow.add(RotationTextFields[i]).size(Gdx.graphics.getWidth() / 10, RotationTextFields[i].getHeight());
            RotationWindow.row();
        }
        RotationWindow.add(buttonTurn).size(102 * scaleWight, 27 * scaleHeight);
        RotationWindow.add(buttonsCancel[1]).size(102 * scaleWight, 27 * scaleHeight);
        RotationWindow.setPosition(Gdx.graphics.getWidth() - Gdx.graphics.getWidth() * 0.05f - RotationWindow.getWidth(), Gdx.graphics.getHeight() * 0.1f);
        RotationWindow.pack();


        table1.add(image1).size(50, 50).expandX().fillX();
        table1.row();
        table1.add(new Image()).pad(0, 2, 0, 2);
        table1.row();
        table1.add(image2).size(50, 50).expandX().fillX();
        table2.add(transparencyLabel);
        table2.row();
        table2.add(slider).minWidth(100).fillX();
        table2.row();
        table2.add(hexLabel).minWidth(100).fillX();
        table2.row();
        table2.add(Hex);

        Window ColorWindow = new Window("Color.", skin);
        ColorWindow.add(table1).expandX().fillX();
        ColorWindow.add(table2).expandX().fillX();
        ColorWindow.row();
        ColorWindow.add(buttonsCancel[2]).size(102 * scaleWight, 27 * scaleHeight);
        ColorWindow.add(buttonPaint).size(102 * scaleWight, 27 * scaleHeight);
        ColorWindow.pack();
        ColorWindow.setPosition(Gdx.graphics.getWidth() * 0.05f, Gdx.graphics.getHeight() * 0.1f);
        stage.addActor(MovingWindow);
        stage.addActor(RotationWindow);
        stage.addActor(ColorWindow);

        slider.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                image2.getColor().a = slider.getValue() / 10;
                transparencyLabel.setText("Transparency : " + slider.getValue() / 10);
            }
        });
        Hex.setTextFieldListener(new TextField.TextFieldListener() {
            public void keyTyped(TextField textField, char key) {
                if (key == '\n') textField.getOnscreenKeyboard().show(false);
                if (textField.getText().length() > 5)
                    if ((textField.getText().substring(0, 1).equals("#") & textField.getText().length() == 7) || (!textField.getText().substring(0, 1).equals("#") & textField.getText().length() == 6)) {
                        try {
                            Color hex_color = new Color(Color.valueOf(textField.getText()));
                            hex_color.a = image2.getColor().a;
                            image2.setColor(hex_color);
                        } catch (Exception e) {
                        }
                    }//TODO:Доделать edit Color

            }
        });
        for (ImageButton button : buttonsCancel)
            button.addListener(new Listener(controller, 47));
        buttonMove.addListener(new Listener(controller, 48));
        buttonPaint.addListener(new Listener(controller, 48));
        buttonTurn.addListener(new Listener(controller, 48));
    }

    public void applyChanges() {
        Objects object = controller.obj.get(selected);
        for (TextField field: CoordinateTextFields){
            field.getOnscreenKeyboard().show(false);
        }
        for (TextField field: RotationTextFields){
            field.getOnscreenKeyboard().show(false);
        }
        if (!object.getColor().equals(image2.getColor()))
            object.setColor(image2.getColor());
        try {
            Vector3 moving = new Vector3(Integer.parseInt(CoordinateTextFields[0].getText()), Integer.parseInt(CoordinateTextFields[1].getText()), Integer.parseInt(CoordinateTextFields[2].getText()));
            if (!object.getMoving(new Vector3()).equals(moving))
                object.moving(moving.x, moving.y, moving.z);
        } catch (Exception e) {
        }
        try {
            Vector3 rotation = new Vector3(Integer.parseInt(RotationTextFields[0].getText()), Integer.parseInt(RotationTextFields[1].getText()), Integer.parseInt(RotationTextFields[2].getText()));
            if (!object.getMoving(new Vector3()).equals(rotation))
                object.rotation(rotation.x, rotation.y, rotation.z);
        } catch (Exception e) {
        }
    }

    public int getObject(int ScrX, int ScrY) {
        return world.getObject(ScrX, ScrY);
    }

    @Override
    public void render(float delta) {
        world.update(delta);
        stringBuilder.setLength(0);
        stringBuilder.append(" FPS: ").append(Gdx.graphics.getFramesPerSecond());
        stringBuilder.append(" Selected: ").append(selected);
        FPS.setText(stringBuilder);
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
        controller.multiplexer.removeProcessor(gameScreenListener);
        world.dispose();
        stage.dispose();
    }
}
