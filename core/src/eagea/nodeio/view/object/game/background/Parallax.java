package eagea.nodeio.view.object.game.background;

import java.util.ArrayList;

/**
 * Parallax background. Use multiple layer for rendering.
 */
public class Parallax
{
    // Total number of layers
    private final int LAYERS = 4;

    // Layers.
    private final ArrayList<eagea.nodeio.view.object.game.background.Layer> mLayers;

    public Parallax()
    {
        mLayers = new ArrayList<>();

        for (int i = 0; i < LAYERS; i ++)
        {
            mLayers.add(new Layer(i, (float) (Math.pow(2, i) / 10d)));
        }
    }

    public void render(float delta)
    {
        mLayers.forEach(l -> l.render(delta));
    }
}