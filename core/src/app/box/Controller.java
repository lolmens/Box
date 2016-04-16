package app.box;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;

import app.box.Obj.Objects;
import app.box.Obj.Player;
import app.box.Screens.Main_menu;

/**
 * Created by user on 29.03.16.
 */
public class Controller extends Game {
    private final AssetM manager = new AssetM(this);
    private TextureAtlas Atlas;
    public ArrayList<Objects> obj;
    public Skin skin_buttons;
    public Skin skin;
    Player player;
    public InputMultiplexer multiplexer;
    private Vector3 go;
    @Override
    public void create() {
        multiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(multiplexer);
        manager.load();
        Atlas = manager.get("buttons/buttons.atlas", TextureAtlas.class);//загрузка атласа с изображениями
        skin_buttons = new Skin(Atlas);
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"), new TextureAtlas(Gdx.files.internal("ui/uiskin.atlas")));
        createStaff();
        this.setScreen(new Main_menu(this));
    }
    private void createStaff() {
        obj = new ArrayList<Objects>();
        player =new Player();
        obj.add(player);
        Objects object = new Objects("Cylinder", new Texture(Gdx.files.internal("obj_img/default.png")));
        object.model = new ModelInstance(new ModelBuilder().createCylinder(10, 10, 10, 90, new Material(ColorAttribute.createDiffuse(0.4f, 0.3f, 0.6f, 0.3f)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));
        //object.model.materials.get(0).set(TextureAttribute.);
        //object.model.materials.get(0).set(FloatAttribute.createAlphaTest(0.5f));
        //object.model.materials.get(0).set(TextureAttribute.createDiffuse(new Texture(Gdx.files.internal("medium.jpg"))));
        object.model.transform.setTranslation(15, 10, 5);

        obj.add(object);
        object = new Objects("X", new Texture(Gdx.files.internal("obj_img/default.png")));//red
        object.setVisible(false);
        object.model = new ModelInstance(new ModelBuilder().createArrow(new Vector3(0,0,0), new Vector3(10,0,0), new Material(ColorAttribute.createDiffuse(Color.RED)),VertexAttributes.Usage.Position));
        obj.add(object);
        object = new Objects("Y", new Texture(Gdx.files.internal("obj_img/default.png")));//green
        object.setVisible(false);
        object.model = new ModelInstance(new ModelBuilder().createArrow(new Vector3(0,0,0), new Vector3(0,10,0), new Material(ColorAttribute.createDiffuse(Color.GREEN)),VertexAttributes.Usage.Position));
        obj.add(object);
        object = new Objects("z", new Texture(Gdx.files.internal("obj_img/default.png")));//blue
        object.setVisible(false);
        object.model = new ModelInstance(new ModelBuilder().createArrow(new Vector3(0,0,0), new Vector3(0,0,10), new Material(ColorAttribute.createDiffuse(Color.BLUE)),VertexAttributes.Usage.Position));
        obj.add(object);
    }

    public AssetM getManager(){
        return manager;
    }
    public void movecam(Vector3 vector){
        go = vector;
    }
    public Vector3 movecam(){
        Vector3 ret = go;
        go = null;
        return ret;
    }
    @Override
    public void dispose(){
        getScreen().dispose();
        super.dispose();
    }

}
