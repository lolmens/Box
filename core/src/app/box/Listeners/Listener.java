package app.box.Listeners;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import app.box.Controller;
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
            case 1:
                break;

            case 2:
                break;

            case 3:
                break;

            case 4:
                controller.getScreen().dispose();
                controller.setScreen(new GameScreen(controller));
                break;

            case 5://Кнопка Edit_menu в Main_menu
                controller.getScreen().dispose();
                controller.setScreen(new Edit_menu(controller));
                break;

            case 10://onli for table in Edit_menu
                //System.out.println("Number: " + event.getListenerActor().getName());
                if (controller.get_edit_menu().table_with_background != null) {
                    ((Table) event.getListenerActor()).setBackground(controller.get_edit_menu().table_with_background.getBackground());
                    Drawable drawable = null;
                    controller.get_edit_menu().table_with_background.setBackground(drawable);
                } else {
                    ((Table)event.getListenerActor()).setBackground(controller.get_edit_menu().getBackground());
                }
                controller.get_edit_menu().table_with_background = (Table) event.getListenerActor();
                /*System.out.println(
                        "controller.get_edit_menu().table_with_background : " + (controller.get_edit_menu().table_with_background == null ? "null ":"not null ") + "\n" +
                        "controller.get_edit_menu().getBackground() : " + (controller.get_edit_menu().getBackground() == null ? "null ":"not null ") + "\n" +
                        "event.getListenerActor().getBackground() : " + ( ((Table) event.getListenerActor()).getBackground() == null ? "null ":"not null ") + "\n" +
                        "controller.get_edit_menu().table_with_background.getBackground() : " +(controller.get_edit_menu().table_with_background.getBackground() == null ? "null " : "not null ") + "\n"
                );*/
                //event.getListenerActor()//***
                break;

            case 20://Кнопка назад
                controller.getScreen().dispose();
                controller.setScreen(new Main_menu(controller));
                break;

            case 25://Кнока edit name в edit menu
                if (controller.get_edit_menu().table_with_background != null){
                TextInputListener listener = new TextInputListener();
                Gdx.input.getTextInput(listener, "Edit name", null, "Enter new name.");
                } //else
                break;
            case 26://Кнока edit coordinates в edit menu
                if (controller.get_edit_menu().table_with_background != null);//подумать возможно сделать новый экран
                break;
            case 27://Кнока edit rotation в edit menu
                if (controller.get_edit_menu().table_with_background != null);//подумать возможно сделать новый экран
                break;
            case 28://Кнока edit color в edit menu
                if (controller.get_edit_menu().table_with_background != null);//Cделать новый экран
                break;
            case 29://Кнока edit transnarensy в edit menu
                if (controller.get_edit_menu().table_with_background != null);//Cделать новый экран
                break;
        }
    }
}
