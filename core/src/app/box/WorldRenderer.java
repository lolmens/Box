package app.box;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;

import java.util.ArrayList;

import app.box.Obj.Objects;

/**
 * Created by user on 01.04.16.
 */
public class WorldRenderer {
    private ModelBatch batch;
    private PerspectiveCamera camera;
    private Environment environment;
    private CameraInputController controller;

    public WorldRenderer() {
        batch = new ModelBatch();
        createCamera();
        createEnvironment();
    }

    private void createCamera() {
        camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(25, 25, 25);//x y z
        camera.lookAt(-5, -5, -5);
        camera.far = 100;
        camera.update();
        controller = new CameraInputController(camera);
        Gdx.input.setInputProcessor(controller);
    }

    private void createEnvironment() {
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.5f, 0.5f, 0.5f, 0.8f));
        environment.add(new DirectionalLight().set(0.7f, 0.7f, 0.7f, 1f, -0.8f, -0.2f));
        environment.add(new PointLight().set(Color.RED, 10, 10, 15, 100));
    }

    public void render(ArrayList<Objects> obj) {
        //controller.update();
        camera.update();
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        batch.begin(camera);
        for (Objects objk : obj) {
            objk.render(batch, environment);
        }
        batch.end();
    }
}
