package kw.mulitplay.game.data;

public class ChessData {
    private int data[][];
    private String str = "rnbakabnr/9/1c5c1/p1p1p1p1p/9/9/P1P1P1P1P/1C5C1/9/RNBAKABNR";
    public ChessData(){
        data = new int[9][10];
        String[] split = str.split("/");
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            int index = 0;
            for (int i1 = 0; i1 < s.length(); i1++) {
                char c = s.charAt(i1);
                if (c>='0'&&c<='9'){
                    index+=(c-'0');
                }else {
                    System.out.println(i);
                    data[index][i] = c;
                    index++;
                }

            }
        }
    }

    public String getStr() {
        return "rnbakabnr/9/1c5c1/p1p1p1p1p/9/9/P1P1P1P1P/1C5C1/9/RNBAKABNR w - - 0 1";
    }

    public int[][] getData() {
        return data;
    }

    public static void main(String[] args) {
        ChessData data = new ChessData();
    }


    public static final String[] PIECE_RES_CARTOON = {
            "rk2", "ra2", "rb2",
            "rn2", "rr2", "rc2",
            "rp2", "bk2", "ba2",
            "bb2", "bn2", "br2",
            "bc2", "bp2", "selected2"
    };
}
