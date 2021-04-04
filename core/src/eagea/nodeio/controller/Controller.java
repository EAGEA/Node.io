package eagea.nodeio.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import eagea.nodeio.model.Model;

/**
 * Handle inputs from player.
 */
public class Controller implements InputProcessor
{
    // The game model.
    private final Model mModel;

    public Controller(Model model)
    {
        mModel = model ;
        // Set as default input handler.
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean keyDown(int keyCode)
    {
        switch (keyCode)
        {
            case Input.Keys.UP: mModel.getPlayer().moveUp(); return true;
            case Input.Keys.DOWN: mModel.getPlayer().moveDown(); return true;
            case Input.Keys.LEFT: mModel.getPlayer().moveLeft(); return true;
            case Input.Keys.RIGHT: mModel.getPlayer().moveRight(); return false;
            case Input.Keys.B: mModel.getPlayer().sayHello(); return false;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        return false;
    }

    @Override
    public boolean keyTyped(char character)
    {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY)
    {
        return false;
    }
}