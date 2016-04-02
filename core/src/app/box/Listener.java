package app.box;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import app.box.Screens.*;
import app.box.Screens.GameScreen;

/**
 * Created by user on 31.03.16.
 */
public class Listener extends InputListener {
    private int id;
    private Controller controller;

    public Listener(Controller controller, int id_button) {
        id = id_button;
        this.controller = controller;
    }

    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        return true;
    }

    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        switch (id) {
            case 4:
                controller.setScreen(new GameScreen(controller));
                break;
            case 5:
                controller.setScreen(new Edit_menu(controller));
                break;
            case 10:
                System.out.println("10 " + event.getButton() + " " + button);
                break;
            case 11:
                System.out.println("11 " + event.getListenerActor().getName() + event.getButton() + " " + button);
                event.getListenerActor().getName();
                break;
        }
    }

}
