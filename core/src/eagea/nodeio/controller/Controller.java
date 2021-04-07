package eagea.nodeio.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

import eagea.nodeio.Main;
import eagea.nodeio.model.Model;
import eagea.nodeio.model.logic.player.PlayerM;
import eagea.nodeio.view.View;

/**
 * Handle inputs from player.
 */
public class Controller implements InputProcessor
{
    // The game model and view.
    private final Model mModel;
    private final View mView;

    public Controller(Model model, View view)
    {
        mModel = model ;
        mView = view;
        // Set as default input handler.
        Gdx.input.setInputProcessor(this);
    }

    /**
     * Keyboard controller.
     */
    @Override
    public boolean keyDown(int keyCode)
    {
        // Can't play.
        if (mModel.getState() == Model.State.CAUGHT
                || mModel.getState() == Model.State.DISCONNECTED)
        {
            return false;
        }

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
            case Input.Keys.Q:
                mModel.askForDisconnection();
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
        // Can't play.
        if (mModel.getState() == Model.State.CAUGHT
                || mModel.getState() == Model.State.DISCONNECTED)
        {
            return false;
        }
        // Check if HUD touched.
        if (mView.isTouched(Main.mViewPortGame.unproject(new Vector2(screenX, screenY))))
        {
            return true;
        }
        // Otherwise it is a movement:
        // Center of screen.
        Vector2 center = new Vector2(Gdx.app.getGraphics().getWidth() / 2f,
                Gdx.app.getGraphics().getHeight() / 2f);

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