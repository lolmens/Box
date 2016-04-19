package app.box.Obj;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;

/**
 * Created by user on 01.04.16.
 */
public class Objects {
    /*protected*/public ModelInstance model;
    private String name;
    private Texture texture;
    private boolean visible = true;
    public Objects(String name, Texture texture){
        this.name = name;
        this.texture = texture;
    }
    public String getName(){
        return  name;
    }
    public void setName(String name){this.name=name;}
    public Texture getTexture() {return texture;}
    public void render(ModelBatch batch, Environment environment) {
        batch.render(model, environment);
    }
    public void setVisible (boolean visible){this.visible=visible;}
    public boolean isVisible(){return visible;}

}
