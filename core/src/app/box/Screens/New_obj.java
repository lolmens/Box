package app.box.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.sun.org.apache.bcel.internal.generic.NEW;

import java.util.ArrayList;


import app.box.Color.AlphaImage;
import app.box.Controller;
import app.box.Listeners.Listener;
import app.box.Obj.Objects;

/**
 * Created by user on 26.04.16.
 */
public class New_obj implements Screen {
    private Controller controller;
    private Stage stage;
    private Skin skin;
    public Table table_with_background = null;
    private Texture background = new Texture("background.png");
    private ArrayList<ArrayList<String>> staff;
    private Table coordinate, color, rotation, buttons, container_right, container_left, staffRight;
    private Image image;
    private Slider sliderX, sliderY, sliderZ;
    private TextField textFieldHex;
    private TextField FieldWidth, FieldHeight, FieldDept, FieldRadius, FieldDivisionU, FieldDivisionV, FieldDivisions;
    private float scaleWight, scaleHeight;

    public New_obj(Controller controller) {
        this.controller = controller;
        stage = new Stage();
        skin = controller.skin;
        controller.set_new_obj(this);
        controller.multiplexer.addProcessor(stage);
        scaleHeight = controller.getScaleH();
        scaleWight = controller.getScaleW();
    }

    @Override
    public void show() {
        stage.clear();
        if (container_left == null) {
            container_left = new Table(skin);
            container_left.setWidth(Gdx.graphics.getWidth() / 4.5f);
            container_left.setHeight(Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 4.5f);
            container_left.setPosition(Gdx.graphics.getWidth() * 0.5f / 7, Gdx.graphics.getHeight() * 0.5f / 3.5f);
            //Создание панели прокрутки
            ScrollPane scrollpane = new ScrollPane(create_staff());
            //Добавиление панели прокрутки в контейнер
            container_left.add(scrollpane).fill().expand();
        }
        stage.addActor(container_left);
        if (container_right == null) {
            container_right = new Table(skin);
            container_right.setWidth(Gdx.graphics.getWidth() * 4f / 7f);
            container_right.setHeight(Gdx.graphics.getHeight() - Gdx.graphics.getHeight() * 1f / 4.5f);
            container_right.setPosition(Gdx.graphics.getWidth() * 2.8f / 7f, Gdx.graphics.getHeight() * 0.5f / 3.5f);
            coordinate = createRightContainerCoordinate();
            color = createRightContainerColor();
            staffRight = new Table(skin);
            rotation = createRightContainerRotation();
            buttons = createRightContainerButtons();
        } else {
            container_right.clear();
            staffRight.clear();
            coordinate = createRightContainerCoordinate();
        }
        staffRight.add(coordinate).fill().expand().row();
        staffRight.add(color).fill().expand().row();
        staffRight.add(rotation).fill().expand().row();
        staffRight.add(buttons).fill().expand().row();

        ScrollPane scroll = new ScrollPane(staffRight);

        container_right.add(scroll).fill().expand();
        stage.addActor(container_right);

        //Создание кнопки назад
        ImageButton button_back = new ImageButton(controller.getManager().load_Style("back"));
        button_back.setSize(30 * scaleWight, 20 * scaleHeight);// размер кнопки
        button_back.setPosition(2 * scaleWight, Gdx.graphics.getHeight() - (button_back.getHeight() + 2 * scaleHeight));//x,y
        button_back.addListener(new Listener(controller, 21));

        //Добавить кнопку возврата на сцену
        stage.addActor(button_back);


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


    public Table createRightContainerCoordinate() {
        ArrayList<String> options = staff.get(Integer.parseInt(table_with_background.getName()));
        Table table = new Table(skin);
        table.add(new Label("Coordinate:", skin)).row();
        int i = 0;
        for (String option : options) {
            if (option.equals("width")) {
                i++;
                table.add(new Label(option + ": ", skin));
                FieldWidth = new TextField("10", skin);
                FieldWidth.setMessageText("int");
                if (i % 3 == 0)
                    table.add(FieldWidth).size(FieldWidth.getWidth() / 4, FieldWidth.getHeight()).expand().fill().row();
                else
                    table.add(FieldWidth).size(FieldWidth.getWidth() / 4, FieldWidth.getHeight()).expand().fill();
                continue;
            } else if (option.equals("height")) {
                i++;
                table.add(new Label(option + ": ", skin));
                FieldHeight = new TextField("10", skin);
                FieldHeight.setMessageText("int");
                if (i % 3 == 0)
                    table.add(FieldHeight).size(FieldHeight.getWidth() / 4, FieldHeight.getHeight()).expand().fill().row();
                else
                    table.add(FieldHeight).size(FieldHeight.getWidth() / 4, FieldHeight.getHeight()).expand().fill();
                continue;
            } else if (option.equals("dept")) {
                i++;
                table.add(new Label(option + ": ", skin));
                FieldDept = new TextField("10", skin);
                FieldDept.setMessageText("int");
                if (i % 3 == 0)
                    table.add(FieldDept).size(FieldDept.getWidth() / 4, FieldDept.getHeight()).expand().fill().row();
                else
                    table.add(FieldDept).size(FieldDept.getWidth() / 4, FieldDept.getHeight()).expand().fill();
                continue;
            } else if (option.equals("radius")) {
                i++;
                table.add(new Label(option + ": ", skin));
                FieldRadius = new TextField("5", skin);
                FieldRadius.setMessageText("int");
                if (i % 3 == 0)
                    table.add(FieldRadius).size(FieldRadius.getWidth() / 4, FieldRadius.getHeight()).expand().fill().row();
                else
                    table.add(FieldRadius).size(FieldRadius.getWidth() / 4, FieldRadius.getHeight()).expand().fill();
                continue;
            } else if (option.equals("divisionU")) {
                i++;
                table.add(new Label(option + ": ", skin));
                FieldDivisionU = new TextField("10", skin);
                FieldDivisionU.setMessageText("int");
                if (i % 3 == 0)
                    table.add(FieldDivisionU).size(FieldDivisionU.getWidth() / 4, FieldDivisionU.getHeight()).expand().fill().row();
                else
                    table.add(FieldDivisionU).size(FieldDivisionU.getWidth() / 4, FieldDivisionU.getHeight()).expand().fill();
                continue;
            } else if (option.equals("divisionV")) {
                i++;
                table.add(new Label(option + ": ", skin));
                FieldDivisionV = new TextField("10", skin);
                FieldDivisionV.setMessageText("int");
                if (i % 3 == 0)
                    table.add(FieldDivisionV).size(FieldDivisionV.getWidth() / 4, FieldDivisionV.getHeight()).expand().fill().row();
                else
                    table.add(FieldDivisionV).size(FieldDivisionV.getWidth() / 4, FieldDivisionV.getHeight()).expand().fill();
                continue;
            } else if (option.equals("divisions")) {
                i++;
                table.add(new Label(option + ": ", skin));
                FieldDivisions = new TextField("10", skin);
                FieldDivisions.setMessageText("int");
                if (i % 3 == 0)
                    table.add(FieldDivisions).size(FieldDivisions.getWidth() / 4, FieldDivisions.getHeight()).expand().fill().row();
                else
                    table.add(FieldDivisions).size(FieldDivisions.getWidth() / 4, FieldDivisions.getHeight()).expand().fill();
                continue;
            }
        }
        table.pack();
        return table;
    }

    public Table createRightContainerColor() {
        Texture whiteTexture;
        Pixmap whitePixmap = new Pixmap(2, 2, Pixmap.Format.RGB888);
        whitePixmap.setColor(Color.WHITE);
        whitePixmap.drawRectangle(0, 0, 2, 2);
        whiteTexture = new Texture(whitePixmap);
        whiteTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        whitePixmap.dispose();
        image = new AlphaImage(whiteTexture, 5);
        final Color color = new Color(Color.valueOf("#00FF33"));
        color.a = 0.5f;
        image.setColor(color);
        Label label_color = new Label("Color:", skin);
        Label label_hex = new Label("Hex:", skin);
        Label label = new Label("", skin);
        textFieldHex = new TextField("#00FF33", skin);
        textFieldHex.setMessageText("#...");
        ImageButton button_random = new ImageButton(controller.getManager().load_Style("random"));
        button_random.addListener(new Listener(controller, 35));
        final Slider slider = new Slider(0f, 10f, 1f, false, skin);
        slider.setAnimateDuration(0.3f);
        slider.setValue(color.a * 10);
        final Label transparencyLabel = new Label("Transparensy : " + color.a, skin);

        Table table = new Table(skin);
        table.add(label_color).expand().fill();
        table.add(image).size(label_color.getWidth(), label_color.getHeight()).expand().fill().row();
        table.add(button_random).size(102 * scaleWight, 27 * scaleHeight).expand().fill();
        table.add(label_hex).expand().fill();
        table.add(textFieldHex).size(label_hex.getWidth() * 3, textFieldHex.getHeight()).expand().fill().row();
        table.add(label).size(label.getWidth(), 5).fill().expand().row();
        table.add(transparencyLabel).expand().fill();
        table.add(label);
        table.add(slider).minWidth(100).fillX().row();
        table.pack();

        slider.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                image.getColor().a = slider.getValue() / 10;
                transparencyLabel.setText("Transparensy : " + slider.getValue() / 10);
            }
        });

        textFieldHex.setTextFieldListener(new TextField.TextFieldListener() {
            public void keyTyped(TextField textField, char key) {
                if (key == '\n') textField.getOnscreenKeyboard().show(false);
                if (textField.getText().length() > 4)
                    if ((textField.getText().substring(0, 1).equals("#") & textField.getText().length() == 7) || (!textField.getText().substring(0, 1).equals("#") & textField.getText().length() == 6)) {
                        try {
                            Color hex_color = new Color(Color.valueOf(textField.getText()));
                            hex_color.a = image.getColor().a;
                            image.setColor(hex_color);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else ;

            }
        });

        return table;
    }

    public Table createRightContainerRotation() {
        final Label labelName, labelX, labelY, labelZ;
        labelName = new Label("Change rotation: ", skin);//TODO: translate
        labelX = new Label("to X: " + 0, skin);
        labelY = new Label("to Y: " + 0, skin);
        labelZ = new Label("to Z: " + 0, skin);
        sliderX = new Slider(0f, 360f, 1f, false, skin);
        sliderY = new Slider(0f, 360f, 1f, false, skin);
        sliderZ = new Slider(0f, 360f, 1f, false, skin);
        Table table = new Table(skin);
        table.add(labelName).expand().fill().row();
        table.add(labelX).expand().fill();
        table.add(sliderX).expand().fill().row();
        table.add(labelY).expand().fill();
        table.add(sliderY).expand().fill().row();
        table.add(labelZ).expand().fill();
        table.add(sliderZ).expand().fill().row();
        table.pack();

        sliderX.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                labelX.setText("to X: " + sliderX.getValue());
            }
        });
        sliderY.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                labelY.setText("to Y: " + sliderY.getValue());
            }
        });
        sliderZ.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                labelZ.setText("to Z: " + sliderZ.getValue());
            }
        });
        return table;
    }

    public Table createRightContainerButtons() {
        ImageButton create = new ImageButton(controller.getManager().load_Style("create"));
        create.addListener(new Listener(controller, 45));
        Table table = new Table(skin);
        table.add(create).size(create.getWidth(), create.getHeight()).fill().expand();
        table.pack();
        return table;
    }

    public void randomColor() {//TODO: alpha, static public String RandomHexColor(){}
        String[] strings = {"00", "33", "66", "99", "CC", "FF"};
        String hex = "";
        for (int i = 0; i < 3; i++) {
            hex += strings[(int) (Math.random() * strings.length)];
        }
        Color hex_color = new Color(Color.valueOf(hex));
        hex_color.a = image.getColor().a;
        image.setColor(hex_color);
        textFieldHex.setText("#" + hex);
    }

    private Table create_staff() {
        ArrayList<ArrayList<String>> staff = new ArrayList<ArrayList<String>>();
        ArrayList<String> data = new ArrayList<String>();
        {
            data.add(0, "Box");//name
            data.add(1, "box");//name texture
            data.add("width");
            data.add("height");
            data.add("dept");
            data.add("divisionU");
            data.add("divisionV");

        }
        staff.add(data);
        data = new ArrayList<String>();
        {
            data.add(0, "Sphere");
            data.add(1, "sphere");
            data.add("width");
            data.add("height");
            data.add("dept");
        }
        staff.add(data);
        data = new ArrayList<String>();
        {
            data.add(0, "Cylinder");
            data.add(1, "cylinder");
            data.add("width");
            data.add("height");
            data.add("dept");
            data.add("divisions");
        }
        staff.add(data);
        data = new ArrayList<String>();
        {
            data.add(0, "Cone");
            data.add(1, "cone");
            data.add("width");
            data.add("height");
            data.add("dept");
            data.add("divisions");
        }
        staff.add(data);
        data = new ArrayList<String>();
        {
            data.add(0, "Capsule");
            data.add(1, "capsule");
            data.add("radius");
            data.add("height");
            data.add("divisions");
        }
        staff.add(data);
        this.staff = staff;
        Table tables = new Table(skin);
        int name = 0;
        for (ArrayList<String> options : staff) {
            Label label = new Label(options.get(0), skin);
            Image image = new Image(getTexture(options.get(1)));
            Table table = new Table(skin);
            table.add(image).size(50*scaleWight, 50*scaleHeight).expand().fill();
            table.add(new Label(" ", skin)).size(3, 50).expand().fill();
            table.add(label).size(90, 50).expand().fill();
            table.setName(name + "");
            //table.setBackground(getBackground());
            table.pack();
            if (table_with_background == null) {
                table_with_background = table;
                //table_with_background.setBackground(getBackground());
            }
            table.addListener(new Listener(controller, 11));
            tables.add(table).expand().fill().row();
            name++;
        }
        tables.pack();
        return tables;
    }

    private Texture getTexture(String name) {
        return new Texture(Gdx.files.internal("obj_img/" + "default" + ".png"));
    }

    public Drawable getBackground() {
        return new TextureRegionDrawable(new TextureRegion(background));
    }

    public void create() {
        ArrayList<String> options = staff.get(Integer.parseInt(table_with_background.getName()));//name and etc.
        Color color = image.getColor();
        Objects object = new Objects(options.get(0), new Texture(Gdx.files.internal("obj_img/default.png")));
        Model model;//TODO: private boolean fieldIsAvable(){}
        Material material = new Material();
        model = new ModelBuilder().createXYZCoordinates(30, material, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        switch (Integer.parseInt(table_with_background.getName())) {
            case 0://box
                model = new ModelBuilder().createBox(Integer.parseInt(FieldWidth.getText()), Integer.parseInt(FieldHeight.getText()), Integer.parseInt(FieldDept.getText()), material, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
                break;
            case 1://sphere
                model = new ModelBuilder().createSphere(Integer.parseInt(FieldWidth.getText()), Integer.parseInt(FieldHeight.getText()), Integer.parseInt(FieldDept.getText()), Integer.parseInt(FieldDivisionU.getText()), Integer.parseInt(FieldDivisionV.getText()), material, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
                break;
            case 2://cylinder
                model = new ModelBuilder().createCylinder(Integer.parseInt(FieldWidth.getText()), Integer.parseInt(FieldHeight.getText()), Integer.parseInt(FieldDept.getText()), Integer.parseInt(FieldDivisions.getText()), material, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
                break;
            case 3://cone
                model = new ModelBuilder().createCone(Integer.parseInt(FieldWidth.getText()), Integer.parseInt(FieldHeight.getText()), Integer.parseInt(FieldDept.getText()), Integer.parseInt(FieldDivisions.getText()), material, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
                break;
            case 4://capsule
                if (Integer.parseInt(FieldHeight.getText()) < 2f * Integer.parseInt(FieldRadius.getText())) {//Height must be at least twice the radius (one diameter)
                    System.out.println(Integer.parseInt(FieldHeight.getText())+":"+2f * Integer.parseInt(FieldRadius.getText()));
                    error_window("Height must be at least twice the radius");
                    return;
                } else
                    model = new ModelBuilder().createCapsule(Integer.parseInt(FieldRadius.getText()), Integer.parseInt(FieldHeight.getText()), Integer.parseInt(FieldDivisions.getText()), material, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
                break;

        }
        object.createModel(model, color);
        object.rotation(sliderX.getValue(), sliderY.getValue(), sliderZ.getValue());
        controller.obj.add(object);
        controller.setScreen(new Edit_menu(controller));
        dispose();
    }

    public void error_window(String error_msg) {
        Label text = new Label(error_msg, skin);
        ImageButton button = new ImageButton(controller.getManager().load_Style("ok"));
        button.addListener(new Listener(controller, 46));
        Window window = new Window("Error", skin);
        window.setPosition(Gdx.graphics.getWidth() / 2 - window.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        window.add(text);
        window.row();
        window.add(button).size(102 * scaleWight, 27 * scaleHeight);
        window.pack();
        stage.addActor(window);
    }

    public void RemoveErrorWindow() {
        String StringWidth="", StringHeight="", StringDept="", StringRadius="", StringDivisionU="", StringDivisionV="", StringDivisions="";
        if (FieldWidth != null) StringWidth = FieldWidth.getText();
        if (FieldHeight != null)  StringHeight = FieldHeight.getText();
        if (FieldDept != null)  StringDept = FieldDept.getText();
        if (FieldRadius != null)  StringRadius = FieldRadius.getText();
        if (FieldDivisionU != null)  StringDivisionU = FieldDivisionU.getText();
        if (FieldDivisionV != null)  StringDivisionV = FieldDivisionV.getText();
        if (FieldDivisions != null)  StringDivisions = FieldDivisions.getText();
        show();
        if (FieldWidth != null) FieldWidth.setText(StringWidth);
        if (FieldHeight != null)  FieldHeight.setText(StringHeight);
        if (FieldDept != null)  FieldDept.setText(StringDept);
        if (FieldRadius != null)  FieldRadius.setText(StringRadius);
        if (FieldDivisionU != null)  FieldDivisionU.setText(StringDivisionU);
        if (FieldDivisionV != null)  FieldDivisionV.setText(StringDivisionV);
        if (FieldDivisions != null)  FieldDivisions.setText(StringDivisions);
    }

    @Override
    public void dispose() {
        controller.multiplexer.removeProcessor(stage);
        stage.dispose();
        controller.set_new_obj(null);
    }
}
