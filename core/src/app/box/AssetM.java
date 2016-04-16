package app.box;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

/**
 * Created by user on 30.03.16.
 */
public class AssetM extends AssetManager {
    private Controller controller;
    AssetM(Controller controller) {
        super();
        this.controller = controller;
    }

    public void load() {
        load("buttons/buttons.atlas", TextureAtlas.class);
        while (!update()) ;//ждём загрузки всего.
    }

    public ImageButton.ImageButtonStyle load_Style(String name) {
        ImageButton.ImageButtonStyle button = new ImageButton.ImageButtonStyle();
        button.up = controller.skin_buttons.getDrawable("button_" + name + "_up");//кнопка не нажата
        button.over = controller.skin_buttons.getDrawable("button_" + name + "_up");//на кнопку навели /*("button_" + name + "_over")*/
        button.down = controller.skin_buttons.getDrawable("button_" + name + "_down"); // кнопка нажата
        return button;
    }
}
