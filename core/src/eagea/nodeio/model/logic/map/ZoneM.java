package eagea.nodeio.model.logic.map;

import com.badlogic.gdx.math.Vector2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

/**
 * A zone of the game map.
 */
public class ZoneM extends Observable implements Serializable
{
    private static final long serialVersionUID = -3060804350757580941L;

    // Type of zone.
    public enum Type { BLACK, GRASS, GRAVEL, ROCK, SAND, SNOW }
    // Max number of type of cells per zone.
    public final int MAX_CELL_VOID = SIZE / 2 - 1;
    public final int MAX_CELL_BUSH = SIZE / 2 - 1;

    // Width and height.
    public static final int SIZE = 6;

    // ID of the player who owns this zone.
    private String mOwner;
    // Type of zone.
    private Type mType;
    // Zone position in map.
    private final int mPositionInMap;
    // Cells.
    private final CellM[][] mCells;

    public ZoneM(String owner, Type type, int position)
    {
        mOwner = owner;
        mType = type;
        mPositionInMap = position;
        mCells = new CellM[SIZE][SIZE];

        generateCells();
    }

    private void generateCells()
    {
        ArrayList<Vector2> indexes = new ArrayList<>();
        // Create cells and get their index.
        for (int i = 0; i < SIZE; i ++)
        {
            for (int j = 0; j < SIZE; j ++)
            {
                mCells[i][j] = new CellM(i, j);
                indexes.add(new Vector2(i, j));
            }
        }
        // Get void cells.
        for (int i = 0; i < Math.random() * MAX_CELL_VOID; i ++)
        {
            Vector2 void_cell = indexes.get((int) (Math.random() * indexes.size()));
            indexes.remove(void_cell);
            mCells[(int) void_cell.x][(int) void_cell.y].setType(CellM.Type.VOID);
        }
        // Get bush cells.
        for (int i = 0; i < Math.random() * MAX_CELL_BUSH; i ++)
        {
            Vector2 bush_cell = indexes.get((int) (Math.random() * indexes.size()));
            indexes.remove(bush_cell);
            mCells[(int) bush_cell.x][(int) bush_cell.y].setType(CellM.Type.BUSH);
        }
    }

    public void setOwner(String owner)
    {
        mOwner = owner;
    }

    public void setType(Type type)
    {
        mType = type;
    }

    public String getOwner()
    {
        return mOwner;
    }

    public Type getType()
    {
        return mType;
    }

    public int getPositionInMap() { return mPositionInMap; }

    public CellM[][] getCells()
    {
        return mCells;
    }
}