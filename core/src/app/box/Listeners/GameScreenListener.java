package app.box.Listeners;

import com.badlogic.gdx.InputProcessor;

import app.box.Controller;
import app.box.Screens.GameScreen;

/**
 * Created by user on 06.05.16.
 */
public class GameScreenListener implements InputProcessor {
    private GameScreen gameScreen;

    public GameScreenListener(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        gameScreen.selecting = gameScreen.getObject(screenX, screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(gameScreen.selecting >= 0){
            if(gameScreen.selecting == gameScreen.getObject(screenX, screenY)){
                gameScreen.setSelected(gameScreen.selecting);
                gameScreen.selecting = -1;
            }
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
