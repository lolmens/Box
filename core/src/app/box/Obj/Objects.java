package app.box.Obj;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;


/**
 * Created by user on 01.04.16.
 */
public class Objects {
    protected ModelInstanceAdvanced model;
    private String name;
    private Texture texture;
    private boolean visible = true;
    private Color color;
    private Vector3 rotation = new Vector3(0,0,0);

    public Objects(String name, Texture texture) {
        this.name = name;
        this.texture = texture;
    }

    public void createModel(Model model) {
        this.model = new ModelInstanceAdvanced(model);
    }

    public void createModel(Model model, Color color) {
        BlendingAttribute blendingAttribute;
        blendingAttribute = new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        blendingAttribute.opacity = 0.5f;
        this.model = new ModelInstanceAdvanced(model);
        this.model.materials.get(0).set(blendingAttribute);
        this.model.materials.get(0).set(ColorAttribute.createDiffuse(0, 0, 0, 0));
        //this.model.materials.get(0).set(IntAttribute.createCullFace(1));
        setColor(color);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Texture getTexture() {
        return texture;
    }

    public void render(ModelBatch batch, Environment environment) {
        batch.render(model, environment);
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    public boolean isVisible() {
        return visible;
    }

    public void rotation(float axisX, float axisY, float axisZ, float degrees) {
        Vector3 translation = model.transform.getTranslation(new Vector3());
        model.transform.setToRotationRad(axisX, axisY, axisZ, (float) Math.toRadians(degrees));
        model.transform.setTranslation(translation);
    }
    public void rotation(float axisX, float axisY, float axisZ) {
        rotation(axisX, axisY, axisZ, axisX + axisY + axisZ);
        rotation.set(axisX, axisY, axisZ);
    }
    public Vector3 getRotation(){return rotation;}

    public void moving(float x, float y, float z) {
        model.transform.setTranslation(x, y, z);
    }
    public Vector3 getMoving(Vector3 vector) {
        model.transform.getTranslation(vector);
        return vector;
    }

    public Vector3 getCenter() {
        return model.center;
    }

    public Float getRadius() {
        return model.radius;
    }

    public void setAttribute(Attribute attribute) {
        model.materials.get(0).set(attribute);
    }

    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
        model.materials.get(0).set(ColorAttribute.createDiffuse(color));
        for (Attribute attribute : model.materials.get(0)) {
            if (BlendingAttribute.class == attribute.getClass()) {
                ((BlendingAttribute) attribute).opacity = color.a;
            }
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public void destroy() {
        texture.dispose();
    }
}

class ModelInstanceAdvanced extends ModelInstance {
    public final Vector3 center = new Vector3();
    public final Vector3 dimens = new Vector3();
    public final float radius;
    private final static BoundingBox bound = new BoundingBox();

    public ModelInstanceAdvanced(Model model) {
        super(model);
        calculateBoundingBox(bound);
        bound.getCenter(center);
        bound.getDimensions(dimens);
        radius = dimens.len() / 2f;
    }
}
