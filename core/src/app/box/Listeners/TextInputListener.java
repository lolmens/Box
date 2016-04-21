package app.box.Listeners;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import app.box.Controller;

/**
 * Created by user on 17.04.16.
 */
public class TextInputListener implements Input.TextInputListener {
    Controller controller;
    int id;
    int type;
    //types
    public final static int EDITNAME = 1;


    public TextInputListener(Controller controller, int id, int type){
        this.controller=controller;
        this.id=id;
        this.type=type;
    }
    @Override
    public void input(String text) {
        //System.out.println("Entered : " + text);
        switch (type){
            case 1:
                if (text.length() >= 15)
                    text = text.substring(0,15);
                controller.obj.get(id).setName(text);
                controller.get_edit_menu().update();
                break;
        }
        //controller.get_edit_menu().update(id);
    }

    @Override
    public void canceled() {
        //System.out.println("Canseled");
    }
}
