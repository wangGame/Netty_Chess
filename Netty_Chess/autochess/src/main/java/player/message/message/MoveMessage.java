package player.message.message;

public class MoveMessage extends ChessMessage {
    private String key;
    private int[] position;
    private int moveType = 0;
    private String selectKey;
    public MoveMessage(String key,int[] position,String selectKey){
        setType(Type.MOVE);
        this.key = key;
        this.position = position;
        this.selectKey = selectKey;
    }

    public void setSelectKey(String selectKey) {
        this.selectKey = selectKey;
    }

    public String getSelectKey() {
        return selectKey;
    }

    public void setMoveType(int moveType) {
        this.moveType = moveType;
    }

    public int getMoveType() {
        return moveType;
    }

    @Override
    public int getType() {
        return super.getType();
    }

    public String getKey() {
        return key;
    }

    public int[] getPosition() {
        return position;
    }
}
