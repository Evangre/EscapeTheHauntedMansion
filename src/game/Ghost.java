package game;

public class Ghost extends GameObject {
    public Ghost(int x, int y) {
        super(x, y);
    }

    @Override
    public String getDisplayString() {
        return "[ G ]";
    }
}