package app.box.Obj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by user on 01.04.16.
 */
public class Player extends Objects{
    public Player(){
        super("Center", new Texture(Gdx.files.internal("obj_img/player.png")));
        ModelBuilder builder = new ModelBuilder();
        model = new ModelInstance(builder.createSphere(0.2f, 0.2f, 0.2f, 20, 20, GL20.GL_TRIANGLES, new Material(), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));
        //moveplayer(new Vector3(0,0,0));
        setVisible(true);
        setColor(new Color(0.2f,0.16f,0.44f,1f));
    }

}
