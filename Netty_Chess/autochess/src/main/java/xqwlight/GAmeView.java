package xqwlight;

/**
 * Created by HZY on 2018/3/6.
 */
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import game.GameConfig;
import game.GameLogic;
import game.IGameView;



public class GAmeView extends Group implements IGameView {
    private static final int WIDTH_CELL_COUNT = 9;
    private static final int HEIGHT_CELL_COUNT = 10;
    private int mPieceTheme = GameConfig.PIECE_THEME_CARTOON;

    private float mCellWidth;
    private Image[] mPiecesBitmap;
    //    private Canvas mCanvas;
    private GameLogic mGameLogic;
//    private RectF mPieceDstRectF;

    public GAmeView() {
        initView();
    }

    private void initView() {
        mGameLogic = new GameLogic(this);
//        mPieceDstRectF = new RectF();
//        setBackgroundResource(R.drawable.board);
        addActor(new Image(new Texture("orange.png")));
        loadBitmapResources();
    }

    public GameLogic getGameLogic() {
        return mGameLogic;
    }

    public void setPieceTheme(int theme) {
        if (theme == mPieceTheme)
            return;
        mPieceTheme = theme;
        loadBitmapResources();
    }

    private void loadBitmapResources() {
        String[] pieceResArray = GameConfig.PIECE_RES_CARTOON;

        mPiecesBitmap = new Image[pieceResArray.length];
        for (int i = 0; i < pieceResArray.length; i++) {
//            if (mPiecesBitmap[i] != null && !mPiecesBitmap[i].isRecycled()) {
//                mPiecesBitmap[i].recycle();
//            }
            mPiecesBitmap[i] = new Image(new Texture("qizi/"+pieceResArray[i]+".png"));
            addActor(mPiecesBitmap[i]);
        }
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//        float widthCell = widthSize * 1.0f / WIDTH_CELL_COUNT;
//        float heightCell = heightSize * 1.0f / HEIGHT_CELL_COUNT;
//        float cellWidth;
//        if (widthCell < 0.1f || heightCell < 0.1f) {
//            cellWidth = Math.max(widthCell, heightCell);
//        } else {
//            cellWidth = Math.min(widthCell, heightCell);
//        }
//        setMeasuredDimension((int) (cellWidth * WIDTH_CELL_COUNT),
//                (int) (cellWidth * HEIGHT_CELL_COUNT));
//    }

//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//        mCellWidth = w * 1.0f / WIDTH_CELL_COUNT;
//    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        mGameLogic.drawGameBoard();
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_UP) {
//            int xx = (int) (event.getX() / mCellWidth);
//            int yy = (int) (event.getY() / mCellWidth);
//            int sq = Position.COORD_XY(xx + Position.FILE_LEFT, yy + Position.RANK_TOP);
//            mGameLogic.clickSquare(sq);
//            return true;
//        }
//        return true;
//    }

    @Override
    public void postRepaint() {
//        postInvalidate();
    }

    int index=0;
    @Override
    public void drawPiece(int pc, int xx, int yy) {
        mCellWidth = 70;
//        if (mCanvas != null) {
        float x = xx * mCellWidth;
        float y = yy * mCellWidth;
        pc -= 8;
        if (pc > 6) {
            pc--;
        }
//        pc = Math.abs();
        mPiecesBitmap[index++%mPiecesBitmap.length].setPosition(x,y);
        System.out.println(x+"---------"+y);
//            mPieceDstRectF.set(x, y, x + mCellWidth, y + mCellWidth);
//            mCanvas.drawBitmap(mPiecesBitmap[pc], null, mPieceDstRectF, null);
//        }
    }

    //绘制选中
    @Override
    public void drawSelected(int xx, int yy) {
        mCellWidth = 70;
//        if (mCanvas != null) {
        float x = xx * mCellWidth;
        float y = yy * mCellWidth;
//            mPieceDstRectF.set(x, y, x + mCellWidth, y + mCellWidth);
//            mCanvas.drawBitmap(mPiecesBitmap[14], null, mPieceDstRectF, null);
        mPiecesBitmap[14].setPosition(x,y);
//    }
    }
}
