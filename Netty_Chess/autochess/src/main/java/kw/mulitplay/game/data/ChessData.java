package kw.mulitplay.game.data;

public class ChessData {
    private int data[][];
    public ChessData(){
        data = new int[9][10];
        String str = "rnbakabnr/9/1c5c1/p1p1p1p1p/9/9/P1P1P1P1P/1C5C1/9/RNBAKABNR";
//        for (int i = 0; i < data.length; i++) {
//            for (int i1 = 0; i1 < data[0].length; i1++) {
//                data[i][i1] = 1;
//            }
//        }
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

    public int[][] getData() {
        return data;
    }

    public static void main(String[] args) {
        ChessData data = new ChessData();
    }
}
