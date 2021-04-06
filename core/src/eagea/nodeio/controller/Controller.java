package eagea.nodeio.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

import eagea.nodeio.model.Model;
import eagea.nodeio.model.logic.player.PlayerM;

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
                mModel.askForMove(PlayerM.Event.UP);
                return true;
            case Input.Keys.DOWN:
                mModel.askForMove(PlayerM.Event.DOWN);
                return true;
            case Input.Keys.LEFT:
                mModel.askForMove(PlayerM.Event.LEFT);
                return true;
            case Input.Keys.RIGHT:
                mModel.askForMove(PlayerM.Event.RIGHT);
                return true;
            case Input.Keys.E:
                mModel.askForSpeak(PlayerM.Speak.HELLO);
                return true;
            case Input.Keys.R:
                mModel.askForSpeak(PlayerM.Speak.LOOSER);
                return true;
            case Input.Keys.T:
                mModel.askForSpeak(PlayerM.Speak.BYE);
                return true;
            case Input.Keys.C:
                mModel.askForCatch();
                return true;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keyCode)
    {
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
                mModel.askForMove(PlayerM.Event.LEFT);
            }
            else
            {
                mModel.askForMove(PlayerM.Event.DOWN);
            }
        }
        else
        {
            if (screenY < center.y)
            {
                mModel.askForMove(PlayerM.Event.UP);
            }
            else
            {
                mModel.askForMove(PlayerM.Event.RIGHT);
            }
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
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