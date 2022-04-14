package kw.mulitplay.game.all;

//import android.content.Context;
//import android.content.res.TypedArray;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.RectF;
//import android.util.AttributeSet;
//import android.util.TypedValue;
//import android.view.MotionEvent;
//import android.view.View;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
//import com.hzy.chinese.jchess.R;
//import com.hzy.chinese.jchess.game.GameConfig;
//import com.hzy.chinese.jchess.game.GameLogic;
//import com.hzy.chinese.jchess.game.IGameView;
//import com.hzy.chinese.jchess.xqwlight.Position;
import kw.mulitplay.game.auto.Position;

/**
 * Created by HZY on 2018/3/8.
 */

public class GameBoardView extends Group implements IGameView {
    private static final int WIDTH_CELL_COUNT = 9;
    private static final int HEIGHT_CELL_COUNT = 10;
    private int mPieceTheme = GameConfig.PIECE_THEME_CARTOON;
    private float mCellWidth;
    private Image[] mPiecesBitmap;
    private GameLogic mGameLogic;
    public GameBoardView() {
        initView();
        addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                int xx = (int) (x / mCellWidth);
                int yy = (int) (y / mCellWidth);
                int sq = Position.COORD_XY(xx + Position.FILE_LEFT, yy + Position.RANK_TOP);
                mGameLogic.clickSquare(sq);
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }


    private void initView() {
        mGameLogic = new GameLogic(this);
        loadBitmapResources();
    }

    public GameLogic getGameLogic() {
        return mGameLogic;
    }

    private void loadBitmapResources() {

        for (int i = 0; i < 15; i++) {
            mPiecesBitmap[i] = new Image(new Texture("orange.png"));
            addActor(mPiecesBitmap[i]);
        }

//        int[] pieceResArray = ;
//        if (mPieceTheme == GameConfig.PIECE_THEME_WOOD) {
//            pieceResArray = GameConfig.PIECE_RES_WOOD;
//        }
//        mPiecesBitmap = new Image[pieceResArray.length];
//        for (int i = 0; i < pieceResArray.length; i++) {
//            if (mPiecesBitmap[i] != null && !mPiecesBitmap[i].isRecycled()) {
//                mPiecesBitmap[i].recycle();
//            }
//        }
    }

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

    @Override
    public void drawPiece(int pc, int xx, int yy) {
//        if (mCanvas != null) {
            float x = xx * mCellWidth;
            float y = yy * mCellWidth;
            pc -= 8;
            if (pc > 6) {
                pc--;
            }
            mPiecesBitmap[pc].setPosition(x,y);
//            mPieceDstRectF.set(x, y, x + mCellWidth, y + mCellWidth);
//            mCanvas.drawBitmap(, null, mPieceDstRectF, null);
//        }
    }

    //绘制选中
    @Override
    public void drawSelected(int xx, int yy) {

            float x = xx * mCellWidth;
            float y = yy * mCellWidth;
            mPiecesBitmap[14].setPosition(x,y);
//            mPieceDstRectF.set(x, y, x + mCellWidth, y + mCellWidth);
//            mCanvas.drawBitmap(mPiecesBitmap[14], null, mPieceDstRectF, null);

    }
}
