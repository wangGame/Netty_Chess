package kw.mulitplay.game.game;

/**
 * Created by HZY on 2018/3/8.
 */

public class GameConfig {

    public static final String DAT_ASSETS_PATH = "book.dat";
    public static final int SPLASH_DELAY_MILLISECONDS = 500;
    public static final int MAX_HISTORY_SIZE = 512;
    public static final String PREF_LAST_FEN = "PREF_LAST_FEN";

    public static final int RESP_CLICK = 0;
    public static final int RESP_ILLEGAL = 1;
    public static final int RESP_MOVE = 2;
    public static final int RESP_MOVE2 = 3;
    public static final int RESP_CAPTURE = 4;
    public static final int RESP_CAPTURE2 = 5;
    public static final int RESP_CHECK = 6;
    public static final int RESP_CHECK2 = 7;
    public static final int RESP_WIN = 8;
    public static final int RESP_DRAW = 9;
    public static final int RESP_LOSS = 10;

    public static final int PIECE_THEME_CARTOON = 0;
    public static final int PIECE_THEME_WOOD = 1;

//    public static final int[] SOUND_RES_ARRAY = {
//            ""
//            R.raw.click, R.raw.illegal, R.raw.move,
//            R.raw.move2, R.raw.capture, R.raw.capture2,
//            R.raw.check, R.raw.check2, R.raw.win,
//            R.raw.draw, R.raw.loss
//    };

    public static final String[] PIECE_RES_WOOD = {
            "rk", "ra", "rb",
            "rn", "rr", "rc",
            "rp", "bk", "ba",
            "bb", "bn", "br",
            "bc", "bp", "selected"
    };

    public static final String[] PIECE_RES_CARTOON = {
            "rk2", "ra2", "rb2",
            "rn2", "rr2", "rc2",
            "rp2", "bk2", "ba2",
            "bb2", "bn2", "br2",
            "bc2", "bp2", "selected2"
    };
}
