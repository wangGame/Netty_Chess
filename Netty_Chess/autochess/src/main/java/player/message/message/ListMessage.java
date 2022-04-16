package player.message.message;

import java.util.ArrayList;

public class ListMessage extends ChessMessage{
    private ArrayList<String> chessMessages;
    public ListMessage(){
        chessMessages = new ArrayList<>();
        setType(Type.LIST);
    }

    public ArrayList<String> getChessMessages() {
        return chessMessages;
    }

    public void setChessMessages(ArrayList<String> chessMessages) {
        this.chessMessages = chessMessages;
    }
}
