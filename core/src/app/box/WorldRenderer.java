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
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

import java.util.ArrayList;

import app.box.Obj.Objects;

/**
 * Created by user on 01.04.16.
 */
public class WorldRenderer {
    private ModelBatch batch;
    private PerspectiveCamera camera;
    private Environment environment;
    private CameraInputController cameraInputController;
    private Controller controller;

    public WorldRenderer(Controller controller) {
        this.controller = controller;
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
        cameraInputController = new CameraInputController(camera);
        controller.multiplexer.addProcessor(cameraInputController);
        Gdx.input.setInputProcessor(controller.multiplexer);
        //Gdx.input.setInputProcessor(cameraInputController);
    }

    private void createEnvironment() {
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.5f, 0.5f, 0.5f, 0.8f));
        //environment.add(new DirectionalLight().set(0.7f, 0.7f, 0.7f, 1f, -0.8f, -0.2f));
        environment.add(new DirectionalLight().set(1f, 1f, 1f, -0.1f, -1f, -0.1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, 0.1f, 1f, 0.1f));
        environment.add(new DirectionalLight().set(new Color(Color.valueOf("FFFF99").add(-0.4f, -0.4f, -0.4f, -0.9f)), new Vector3(-10, -3, -10)));
        //environment.add(new PointLight().set(Color.RED, 10, 10, 15, 100));
    }

    public void render(ArrayList<Objects> obj) {
        //controller.update();
        /*Vector3 movecam  = controller.movecam();//future
        if (movecam != null){ camera_go(movecam); System.out.println(camera.position.x+" "+camera.position.y+" "+camera.position.z);}*/
        camera.update();
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        batch.begin(camera);
        for (Objects objk : obj) {
            objk.render(batch, environment);
        }
        batch.end();
    }

    public int getObject(int ScrX, int ScrY) {
        Ray ray = camera.getPickRay(ScrX, ScrY);
        int res = -1;
        float span = -1f;
        ArrayList<Objects> obj = controller.obj;
        Vector3 position = new Vector3();
        for (int i = 0; i < obj.size(); i++) {
            Objects object = obj.get(i);
            if (object.isVisible()) {
                object.getMoving(position);
                position.add(object.getCenter());
                final float len = ray.direction.dot(position.x - ray.origin.x, position.y - ray.origin.y, position.z - ray.origin.z);
                if (len < 0f)
                    continue;
                float span2 = position.dst2(ray.origin.x + ray.direction.x * len, ray.origin.y + ray.direction.y * len, ray.origin.z + ray.direction.z * len);
                if (span >= 0f && span2 > span)
                    continue;
                if (span2 <= object.getRadius() * object.getRadius()) {
                    res = i;
                    span = span2;
                }
            }
        }
        return res;
    }


    public void camera_go(Vector3 v) {
        camera.position.add(v);
    }

    public void camera_look(Vector3 v) {
        camera.lookAt(v);
    }

    public void dispose() {
        batch.dispose();
        controller.multiplexer.removeProcessor(cameraInputController);
    }
}
