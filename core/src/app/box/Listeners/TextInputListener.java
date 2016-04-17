package app.box.Listeners;

import com.badlogic.gdx.Input;

/**
 * Created by user on 17.04.16.
 */
public class TextInputListener implements Input.TextInputListener {

    @Override
    public void input(String text) {
        System.out.println("Entered : " + text);
    }

    @Override
    public void canceled() {
        System.out.println("Canseled");
    }
}
