package app.box;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Created by user on 30.03.16.
 */
public class AssetM extends AssetManager{

    public void load(){
        load("buttons.atlas", TextureAtlas.class);
        while(!update());//ждём загрузки всего.
    }
}
