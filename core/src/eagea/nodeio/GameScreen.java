package eagea.nodeio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import eagea.nodeio.controller.Controller;
import eagea.nodeio.model.Model;
import eagea.nodeio.view.View;

/**
 * Render the game environment i.e. map, players and more.
 */
public class GameScreen extends ScreenAdapter
{
    public static float FOOTSTEP_SOUND_VOLUME = 1f;
    public static float CATCH_SOUND_VOLUME = 0.5f;
    public static float BUTTON_SOUND_VOLUME = 0.15f;
    public static float SPEAK_SOUND_VOLUME = 0.7f;
    public static float MENU_MUSIC_VOLUME = 0.3f;
    public static float GAME_MUSIC_VOLUME = 0.3f;

    // Textures.
    public static TextureAtlas mEnvironmentAtlas;
    public static TextureAtlas mCharactersAtlas;
    public static TextureAtlas mHUDAtlas;
    public static TextureAtlas mGUIAtlas;
    // Sounds.
    public static Sound mFootStepSound;
    public static Sound mButtonSound;
    public static Sound mCatchSound;
    public static Sound mSpeakSound;
    // Musics.
    public static Music mMenuMusic;
    public static Music mGameMusic;
    // Model:
    private Model mModel;
    // View:
    private View mView;
    // Controller:
    private Controller mController;

    @Override
    public void show()
    {
        // Textures.
        mEnvironmentAtlas = new TextureAtlas(Gdx.files.internal("atlases/environment.atlas")) ;
        mCharactersAtlas = new TextureAtlas(Gdx.files.internal("atlases/characters.atlas")) ;
        mHUDAtlas = new TextureAtlas(Gdx.files.internal("atlases/hud.atlas")) ;
        mGUIAtlas = new TextureAtlas(Gdx.files.internal("atlases/gui.atlas")) ;
        // Game objects.
        mModel = new Model(this);
        mView = new View(mModel);
        mController = new Controller(mModel, mView);
        // Sounds.
        mFootStepSound = Gdx.audio.newSound(Gdx.files.internal("sounds/footstep.ogg"));
        mCatchSound = Gdx.audio.newSound(Gdx.files.internal("sounds/catch.wav"));
        mButtonSound = Gdx.audio.newSound(Gdx.files.internal("sounds/button.wav"));
        mSpeakSound = Gdx.audio.newSound(Gdx.files.internal("sounds/speak.wav"));
        mMenuMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/menu.mp3"));
        mGameMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/game.mp3"));
        // Appear in menu.
        startMenuMusic();
    }

    @Override
    public void dispose()
    {
        mEnvironmentAtlas.dispose();
        mCharactersAtlas.dispose();
        mFootStepSound.dispose();
        mButtonSound.dispose();
        mCatchSound.dispose();
        mSpeakSound.dispose();
        mMenuMusic.dispose();
        mGameMusic.dispose();
    }

    @Override
    public void render(float delta)
    {
        super.render(delta) ;
        // Clear the screen.
        Gdx.gl.glEnable(GL30.GL_BLEND) ;
        Gdx.gl.glClearColor(0, 0, 0, 1) ;
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT) ;
        // Render objects.
        if (mView != null)
        {
            Main.mBatch.begin();
            mView.render(delta);
            Main.mBatch.end();
        }
    }

    public void onStartGame()
    {
        mView.onStartGame();
    }

    public static void playFootstepSound()
    {
        mFootStepSound.play(FOOTSTEP_SOUND_VOLUME);
    }

    public static void playCatchSound()
    {
        mCatchSound.play(CATCH_SOUND_VOLUME);
    }

    public static void playButtonSound()
    {
        mButtonSound.play(BUTTON_SOUND_VOLUME);
    }

    public static void playSpeakSound()
    {
        mSpeakSound.play(SPEAK_SOUND_VOLUME);
    }

    public static void startMenuMusic()
    {
        mMenuMusic.setLooping(true);
        mMenuMusic.setVolume(MENU_MUSIC_VOLUME);
        mMenuMusic.play();
    }

    public static void stopMenuMusic()
    {
        mMenuMusic.stop();
    }

    public static void startGameMusic()
    {
        mGameMusic.setLooping(true);
        mGameMusic.setVolume(GAME_MUSIC_VOLUME);
        mGameMusic.play();
    }

    public static void stopGameMusic()
    {
        mGameMusic.stop();
    }
}