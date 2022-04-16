package player.message.message;

public class EorrMessage extends ChessMessage{
    private String msg;
    public EorrMessage(String msg){
        setType(Type.ERROR);
        this.msg = msg;
    }
}
