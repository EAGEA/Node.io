package eagea.nodeio.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

import eagea.nodeio.model.Model;

/**
 * Handle inputs from player.
 */
public class Controller implements InputProcessor
{
    // The game model.
    private final Model mModel;
    // True if user is holding the key/touch.
    private boolean mIsHolding;

    public Controller(Model model)
    {
        mModel = model ;
        mIsHolding = false;
        // Set as default input handler.
        Gdx.input.setInputProcessor(this);
    }

    /**
     * Keyboard controller.
     */
    @Override
    public boolean keyDown(int keyCode)
    {
        switch (keyCode)
        {
            case Input.Keys.UP:
                mModel.getPlayer().moveUp();
                mIsHolding = true;
                return true;
            case Input.Keys.DOWN:
                mModel.getPlayer().moveDown();
                mIsHolding = true;
                return true;
            case Input.Keys.LEFT:
                mModel.getPlayer().moveLeft();
                mIsHolding = true;
                return true;
            case Input.Keys.RIGHT:
                mModel.getPlayer().moveRight();
                mIsHolding = true;
                return false;
            case Input.Keys.B:
                mModel.getPlayer().sayHello();
                return false;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keyCode)
    {
        switch (keyCode)
        {
            case Input.Keys.UP:
            case Input.Keys.DOWN:
            case Input.Keys.LEFT:
            case Input.Keys.RIGHT:
                mIsHolding = false;
                return true;
        }

        return false;
    }

    @Override
    public boolean keyTyped(char character)
    {
        return false;
    }

    /**
     * Phone controller.
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        // Center of screen.
        Vector2 center = new Vector2(Gdx.graphics.getWidth() / 2f,
                Gdx.graphics.getHeight() / 2f);

        if (screenX < center.x)
        {
            if (screenY < center.y)
            {
                mModel.getPlayer().moveLeft();
            }
            else
            {
                mModel.getPlayer().moveDown();
            }
        }
        else
        {
            if (screenY < center.y)
            {
                mModel.getPlayer().moveUp();
            }
            else
            {
                mModel.getPlayer().moveRight();
            }
        }

        mIsHolding = true;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        mIsHolding = false;
        return true;
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