package kw.mulitplay.game.ai;

public class ChessData {
    private int chess;
    private int pox;
    private int poy;

    public void setChess(int chess) {
        this.chess = chess;
    }

    public void setPox(int pox) {
        this.pox = pox;
    }

    public void setPoy(int poy) {
        this.poy = poy;
    }

    public int getChess() {
        return chess;
    }

    public int getPox() {
        return pox;
    }

    public int getPoy() {
        return poy;
    }

    @Override
    public String toString() {
        return "ChessData{" +
                "chess=" + chess +
                ", pox=" + pox +
                ", poy=" + poy +
                '}';
    }
}
