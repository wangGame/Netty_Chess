package kw.mulitplay.game.data;

public class ChessData {
    private int data[][];
    public ChessData(){
        data = new int[9][10];
        for (int i = 0; i < data.length; i++) {
            for (int i1 = 0; i1 < data[0].length; i1++) {
                data[i][i1] = 1;
            }
        }
    }

    public int[][] getData() {
        return data;
    }
}
