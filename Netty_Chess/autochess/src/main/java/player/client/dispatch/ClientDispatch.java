package player.client.dispatch;

import static kw.mulitplay.game.config.LevelConfig.pipeiSuccess;
import static kw.mulitplay.game.config.LevelConfig.success;

import java.util.ArrayList;
import java.util.logging.Level;

import com.badlogic.gdx.Gdx;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import kw.ChessGame;
import kw.mulitplay.game.config.LevelConfig;
import player.client.handler.ChannelInHandler;
import player.message.message.*;

public class ClientDispatch {

    public static void dispatch(ChessMessage message, ChannelHandlerContext ctx){

        switch (message.getType()){
            case Type
                    .LIST:
                showList(message,ctx);
                break;
            case Type.PIPEI:
                pipeiSuccess((PipeiMessage)message);
                break;
            case Type.MOVE:
                move((MoveMessage)message,ctx);
                    break;
        }
    }

    private static void pipeiSuccess(PipeiMessage message) {
        if (runnable!=null){
            runnable.run();
        }
        LevelConfig.userUUID = message.getUuid();
        LevelConfig.currentUser = message.getPlyer();
        LevelConfig.BUSY = true;

    }

    private static void showList(ChessMessage message, ChannelHandlerContext ctx) {
        ArrayList<String> chessMessages = ((ListMessage) message).getChessMessages();
        for (int i = 0; i < chessMessages.size(); i++) {
            System.out.println(chessMessages.get(i));
        }
    }

    private static Runnable runnable;

    public static void pipei(Runnable run) {
        PipeiMessage message = new PipeiMessage();
        message.setUuid(LevelConfig.currentPlayerUUID);
        LevelConfig.context.writeAndFlush(message);
        runnable = run;
    }

    public static void move(MoveMessage message,ChannelHandlerContext ctx){
         Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                if (message.getMoveType()==1) {
                    LevelConfig.gameView.xx(message.getSelectKey(),message.getPosition());
                }else {
                    LevelConfig.gameView.xx2(message.getKey(),message.getSelectKey(),message.getPosition());
                }
            }
        });
    }


    public static void move11(String selectedPieceKey, int[] pos) {
        MoveMessage message = new MoveMessage(null,pos,selectedPieceKey);
        message.setMoveType(1);
        message.setUuid(LevelConfig.userUUID);
        LevelConfig.context.writeAndFlush(message);
    }

    public static void move222(String key, String selectedPieceKey, int[] pos) {
        MoveMessage message = new MoveMessage(key,pos,selectedPieceKey);
        message.setMoveType(2);
        message.setUuid(LevelConfig.userUUID);
        LevelConfig.context.writeAndFlush(message);
    }
}
