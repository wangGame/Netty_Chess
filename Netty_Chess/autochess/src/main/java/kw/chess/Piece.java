package kw.chess;

/**
 * Created by Tong on 12.03.
 * Chess > Piece entity
 *
 * 每一个棋子的信息
 */
public class Piece implements Cloneable {
    public String key;
    public char color;
    public char character;
    public char index;
    public int[] position;

    public Piece(String name, int[] position) {
        this.key = name;
        this.color = name.charAt(0);
        this.character = name.charAt(1);//bj0
        this.index = name.charAt(2);
        this.position = position;
    }
}
