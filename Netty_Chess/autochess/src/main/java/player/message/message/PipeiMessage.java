package player.message.message;

public class PipeiMessage extends ChessMessage{
    private char plyer = 'r';
    public PipeiMessage(){
        setType(Type.PIPEI);
    }

    public void setPlyer(char plyer) {
        this.plyer = plyer;
    }

    public char getPlyer() {
        return plyer;
    }
}
