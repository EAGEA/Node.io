package eagea.nodeio.model.logic.map;

import com.badlogic.gdx.math.Vector2;

import java.io.Serializable;
import java.util.Observable;

/**
 * A cell of a zone from the map.
 */
public class CellM extends Observable implements Serializable
{
    private static final long serialVersionUID = 8727742012328555099L;

    // Type of zone.
    public enum Type { EMPTY, BUSH, VOID }

    // Type of cell.
    private Type mType;
    // Position in zone.
    private Vector2 mPosition;

    public CellM(int i, int j)
    {
        // Default.
        mType = Type.EMPTY;

        mPosition = new Vector2(i, j);
    }

    public void setType(Type type)
    {
        mType = type;
    }

    public Type getType()
    {
        return mType;
    }

    public Vector2 getPosition()
    {
        return mPosition;
    }
}