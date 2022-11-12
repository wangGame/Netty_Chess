package kw.mulitplay.game.move;

public class GameMove {
    private int startX;
    private int startY;
    private int endX;
    private int endY;

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }
}
