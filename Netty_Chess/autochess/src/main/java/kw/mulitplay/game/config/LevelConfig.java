package kw.mulitplay.game.config;


import io.netty.channel.ChannelHandlerContext;
import kw.chess.Board;
import kw.control.GameController;
import kw.view.GameView;

public class LevelConfig {
    public static char currentPlayer;
    public static int clickType = 0; //0 是点击了自己   1 是点击对方
    public static int currentStatus;
    public static int pipeiSuccess = 1;
    public static int level = 2;
    public static int success = 3;
    public static String userUUID;
    public static String userUUID1;
    public static int play = 0;
    public static int startGame = 4;
    public static int Playing = -1;
    public static int model = 0;
    public static String currentPlayerUUID = "";
    public static GameView gameView;
    public static Board board;
    public static ChannelHandlerContext context;
    public static char currentUser;
    public static boolean BUSY = false;
}
