package app.box;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;

import app.box.Obj.Objects;
import app.box.Obj.Player;
import app.box.Screens.Main_menu;

/**
 * Created by user on 29.03.16.
 */
public class Controller extends Game {
    private final AssetM manager = new AssetM();
    private TextureAtlas Atlas;
    public ArrayList<Objects> obj;
    public Skin skin_buttons;
    public Skin skin;

    @Override
    public void create() {
        manager.load();
        Atlas = manager.get("buttons.atlas", TextureAtlas.class);//загрузка атласа с изображениями
        skin_buttons = new Skin(Atlas);
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"), new TextureAtlas(Gdx.files.internal("ui/uiskin.atlas")));
        createStaff();
        this.setScreen(new Main_menu(this));
    }
    private void createStaff() {
        obj = new ArrayList<Objects>();
        obj.add(new Player());
        Objects object = new Objects("Cylinder", new Texture(Gdx.files.internal("obj_img/default.png")));
        object.model = new ModelInstance(new ModelBuilder().createCylinder(10,10,10,90,new Material(ColorAttribute.createDiffuse(0.4f, 0.3f, 0.3f, 0.1f)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));
        object.model.transform.setTranslation(15,10,5);
        obj.add(object);
    }

}
