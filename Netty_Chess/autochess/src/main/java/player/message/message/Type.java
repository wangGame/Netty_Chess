package player.message.message;

import java.util.HashMap;

public abstract class Type {
    public static final int LIST = 2;
    public static final int MOVE = 3;
    public static final int PIPEI = 4;
    public static final int ERROR = 5;
    public static HashMap<Integer,Class<? extends ChessMessage>> hashMap = new HashMap<>();
    public static final int REGISTER = 1;
    static {
        hashMap.put(REGISTER,ChessMessage.class);
    }
}
