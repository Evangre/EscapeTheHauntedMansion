package game;

public class Room extends GameObject {
    public Room(int x, int y) {
        super(x, y);
    }

    @Override
    public String getDisplayString() {
        return "[   ]";
    }
}
