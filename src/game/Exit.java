package game;

public class Exit extends GameObject {
    public Exit(int x, int y) {
        super(x, y);
    }

    @Override
    public String getDisplayString() {
        return "[ E ]";
    }
}