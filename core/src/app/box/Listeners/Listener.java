package app.box.Listeners;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

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
                    ((Table) event.getListenerActor()).setBackground(controller.get_edit_menu().getBackground());
                }
                controller.get_edit_menu().table_with_background = (Table) event.getListenerActor();
                controller.get_edit_menu().table_with_background.setName(event.getListenerActor().getName());
                //System.out.println( controller.get_edit_menu().table_with_background.getName());
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
                if (controller.get_edit_menu().table_with_background != null) {
                    TextInputListener listener = new TextInputListener(controller, Integer.parseInt(controller.get_edit_menu().table_with_background.getName()), TextInputListener.EDITNAME);
                    //System.out.println(controller.get_edit_menu().table_with_background.getName());
                    Gdx.input.getTextInput(listener, "Edit name", null, "Enter new name.");
                } //else
                break;
            case 26://Кнока edit coordinates в edit menu
                if (controller.get_edit_menu().table_with_background != null)
                    controller.get_edit_menu().moving();
                break;
            case 27://Кнока edit rotation в edit menu
                if (controller.get_edit_menu().table_with_background != null)
                    controller.get_edit_menu().rotation();
                break;
            case 28://Кнока edit color в edit menu
                if (controller.get_edit_menu().table_with_background != null) ;//Cделать новый экран
                break;
            case 29://Кнока add в edit menu
                break;
            case 30://Кнока remove в edit menu
                if (controller.get_edit_menu().table_with_background != null)
                    controller.get_edit_menu().remove();
                else controller.get_edit_menu().error_window("First you have to choose what to delete, isn't it?");
                break;
            case 40://Cansel, ok в window в edit menu
                controller.get_edit_menu().update();
                break;
            case 41://Turn в window в edit menu
                if (!controller.get_edit_menu().edit_rotation()) {
                    controller.get_edit_menu().update();
                    controller.get_edit_menu().error_window("Ops, something was wrong, trust me.");
                } else controller.get_edit_menu().update();
                break;
            case 42://Move в window в edit menu
                if (!controller.get_edit_menu().edit_moving()) {
                    controller.get_edit_menu().update();
                    controller.get_edit_menu().error_window("Ops, something was wrong, trust me.");
                } else controller.get_edit_menu().update();
                break;
            case 43:
                controller.get_edit_menu().remove_confirm();
                controller.get_edit_menu().update();
                break;

        }
    }
}
