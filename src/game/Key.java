package game;

public class Key extends GameObject {
    public Key(int x, int y) {
        super(x, y);
    }

    @Override
    public String getDisplayString() {
        return "[ K ]";
    }
}