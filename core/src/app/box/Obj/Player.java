package app.box.Obj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

/**
 * Created by user on 01.04.16.
 */
public class Player extends Objects{
    public Player(){
        super("Player", new Texture(Gdx.files.internal("obj_img/user.png")));
        ModelBuilder builder = new ModelBuilder();
        model = new ModelInstance(builder.createSphere(2, 2, 2, 10, 10, GL20.GL_TRIANGLES, new Material(ColorAttribute.createDiffuse(0.4f, 0.7f, 0.3f, 0.1f)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));
    }
    public void update() {
        //model.transform.set(***);
    }
}
