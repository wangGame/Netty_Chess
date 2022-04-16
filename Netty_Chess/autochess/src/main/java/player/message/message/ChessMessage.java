package player.message.message;

import java.io.Serializable;

public class ChessMessage implements Serializable {
    private String uuid;
    private int type;

    public void setType(int type) {
        this.type = type;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public int getType() {
        return type;
    }
}
