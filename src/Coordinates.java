//Class to  store co-ordinate of each letter in the puzzlegrid

public class Coordinates
{
    //Row number
    int x_pos;

    //Column column
    int y_pos;

    public Coordinates(int x_pos, int y_pos) {
        this.x_pos = x_pos;
        this.y_pos = y_pos;
    }

    public int getX_pos() {
        return x_pos;
    }

    public void setX_pos(int x_pos) {
        this.x_pos = x_pos;
    }

    public int getY_pos() {
        return y_pos;
    }

    public void setY_pos(int y_pos) {
        this.y_pos = y_pos;
    }
}
