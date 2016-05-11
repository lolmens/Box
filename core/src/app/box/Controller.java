package app.box;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;

import app.box.Obj.Objects;
import app.box.Obj.Player;
import app.box.Screens.Edit_menu;
import app.box.Screens.Main_menu;
import app.box.Screens.New_obj;

/**
 * Created by user on 29.03.16.
 */
public class Controller extends Game {
    private final AssetM manager = new AssetM(this);
    private TextureAtlas Atlas;
    public ArrayList<Objects> obj;
    public Skin skin_buttons;
    public Skin skin;
    private Player player;
    public InputMultiplexer multiplexer;
    private Edit_menu edit_menu;
    private New_obj new_obj;
    private float scaleWight, scaleHeight;

    @Override
    public void create() {
        multiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(multiplexer);
        scaleWight = Gdx.graphics.getWidth() / 800f;
        scaleHeight = Gdx.graphics.getHeight() / 480f;
        manager.load();
        Atlas = manager.get("buttons/buttons.atlas", TextureAtlas.class);//загрузка атласа с изображениями
        skin_buttons = new Skin(Atlas);
        if(scaleWight + scaleHeight / 2 <= 1)
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"), new TextureAtlas(Gdx.files.internal("ui/uiskin.atlas")));
        else
            skin = new Skin(Gdx.files.internal("ui/uiskinBigFront.json"), new TextureAtlas(Gdx.files.internal("ui/uiskin.atlas")));
        createStaff();
        /*System.out.println("Wight: " + Gdx.graphics.getWidth() + " Height: " + Gdx.graphics.getHeight());
        System.out.println("Wight scale: " + scaleWight + " Height scale: " + scaleHeight);*/
        this.setScreen(new Main_menu(this));

    }

    private void createStaff() {
        obj = new ArrayList<Objects>();
        player = new Player();
        obj.add(player);

        BlendingAttribute blendingAttribute;
        blendingAttribute = new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        blendingAttribute.opacity = 0.5f;

        Objects object = new Objects("Cylinder", new Texture(Gdx.files.internal("obj_img/default.png")));
        /*object.createModel(new ModelBuilder().createCylinder(10, 10, 10, 90, new Material(), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));
        object.setAttribute(blendingAttribute);
        object.setAttribute(ColorAttribute.createDiffuse(0.9f, 0.6f, 0.7f, 0.5f));
        object.setColor(new Color(0.9f, 0.6f, 0.7f, 0.5f));
        object.moving(15, 10, 5);
        object.rotation(45, 0, 45);
        obj.add(object);*/
        object = new Objects("X", new Texture(Gdx.files.internal("obj_img/default.png")));//red, x
        object.setVisible(false);
        object.createModel(new ModelBuilder().createArrow(new Vector3(-1, 0, 0), new Vector3(10, 0, 0), new Material(ColorAttribute.createDiffuse(Color.RED)), VertexAttributes.Usage.Position));
        object.setColor(Color.RED);
        obj.add(object);
        object = new Objects("Y", new Texture(Gdx.files.internal("obj_img/default.png")));//green, y
        object.setVisible(false);
        object.createModel(new ModelBuilder().createArrow(new Vector3(0, -1, 0), new Vector3(0, 10, 0), new Material(ColorAttribute.createDiffuse(Color.GREEN)), VertexAttributes.Usage.Position));
        object.setColor(Color.GREEN);
        obj.add(object);
        object = new Objects("z", new Texture(Gdx.files.internal("obj_img/default.png")));//blue, z
        object.setVisible(false);
        object.createModel(new ModelBuilder().createArrow(new Vector3(0, 0, -1), new Vector3(0, 0, 10), new Material(ColorAttribute.createDiffuse(Color.BLUE)), VertexAttributes.Usage.Position));
        object.setColor(Color.BLUE);
        obj.add(object);

        ModelLoader loader = new ObjLoader();
        object = new Objects("Samsung", new Texture("obj_img/default.png"));
        object.setVisible(true);
        object.createModel(loader.loadModel(Gdx.files.internal("models/samsunglogo.obj")), Color.BLUE);
        object.moving(0, 14, 0);
        object.rotation(0, 35, 0);
        obj.add(object);

    }

    public float getScaleH() {
        return scaleHeight;
    }

    public float getScaleW() {
        return scaleWight;
    }

    public AssetM getManager() {
        return manager;
    }

    public Edit_menu get_edit_menu() {
        return edit_menu;
    }

    public void set_edit_menu(Edit_menu edit_menu) {
        this.edit_menu = edit_menu;
    }

    public New_obj get_new_obj() {
        return new_obj;
    }

    public void set_new_obj(New_obj new_obj) {
        this.new_obj = new_obj;
    }

    @Override
    public void dispose() {
        getScreen().dispose();
        skin.dispose();
        skin_buttons.dispose();
        super.dispose();
    }

    //TODO: Дать выбор переходить или нет в коробку сразу после изменения параметров адаптера.

}
