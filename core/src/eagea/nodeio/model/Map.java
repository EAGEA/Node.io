package eagea.nodeio.model;

public class Map
{
    /* TO DO : ENUM CLASS FOR MAP (COLOR ?)*/

    private int size; // Map of N * N
    private int grid[][];
    //Map
    public Map(int mSize)
    {
        size = mSize;
        grid = new int[size][size];
    }

    //Init the map
    public void InitGrid()
    {
        int i,j;
        for(i = 0 ; i < size ; i++)
        {
            for(j = 0 ; j < size ; j ++)
            {
                grid[i][j] = -1 ;
            }
        }
    }

    //Return true if the cell is occupied by a player
    public boolean IsOccupied(int i, int j)
    {
        return grid[i][j] > 0;
    }

    //Get the size of the map
    public int getSize()
    {
        return size;
    }
}
