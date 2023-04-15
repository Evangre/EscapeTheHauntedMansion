package game;

public class Player extends GameObject {
    public Player(int x, int y) {
        super(x, y);
    }

    @Override
    public String getDisplayString() {
        return "[ P ]";
    }
}