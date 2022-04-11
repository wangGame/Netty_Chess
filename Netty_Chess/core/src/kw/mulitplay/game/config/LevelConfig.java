package kw.mulitplay.game.config;

import kw.mulitplay.game.data.Chess;

public class LevelConfig {
    public static Chess chessSelected;
    public static Chess currentSelected;
    public static Chess tarGetSelected;
    public static int PLAYER1 = 0;
    public static int PLAYER0 = 1;
    public static int currentPlayer = PLAYER0;
    public static int clickType = 0; //0 是点击了自己   1 是点击对方
}
