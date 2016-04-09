package app.box;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import java.util.ArrayList;

import app.box.Obj.Objects;
import app.box.Obj.Player;

/**
 * Created by user on 01.04.16.
 */
public class World {
    private WorldRenderer render;
    ArrayList<Objects> obj;
    public World(Controller controller) {
        obj = controller.obj;
        render = new WorldRenderer(controller);
    }
    public void update(float delta) {
        /*Gdx.gl.glClearColor(0.35f, 0, 0.35f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);*/
        render.render(obj);

    }

}
