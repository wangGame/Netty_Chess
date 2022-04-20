package kw.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import kw.chess.Board;
import kw.chess.Piece;
import kw.chess.Rules;
import kw.control.GameController;
import kw.mulitplay.game.config.LevelConfig;
import player.client.dispatch.ClientDispatch;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tong on 12.10.
 * Dealing with graphics logic in the chess game. Render with j2d.
 */
public class GameView extends Group {
    private static final int VIEW_WIDTH = 700;
    private static final int VIEW_HEIGHT = 712;
    private static final int PIECE_WIDTH = 67;
    private static final int PIECE_HEIGHT = 67;
    private static final int SY_COE = 69;
    private static final int SX_COE = 68;
    private static final int SX_OFFSET = 50;
    private static final int SY_OFFSET = 15;
    private Map<String, Image> pieceObjects;
    private Board board;
    private String selectedPieceKey;
    private GameController controller;
    private Image lblPlayer;
    private Image pointr;
    private Image pointb;

    public GameView(GameController gameController) {
        pieceObjects = new HashMap<String, Image>();
        this.controller = gameController;
        setSize(700,712);
        LevelConfig.gameView  = this;
        this.board = gameController.getBoard();
        init();
    }

    public void init() {
        //绘制背景
        Image bgBoard = new Image(new Texture("res/img/board.png"));
        bgBoard.setPosition(0, 0);
        bgBoard.setSize(VIEW_WIDTH, VIEW_HEIGHT);
        bgBoard.addListener(new BoardClickListener());
        addActor(bgBoard);
        /* Initialize player image.*/
        lblPlayer = new Image(new Texture("res/img/r.png"));
        lblPlayer.setPosition(10, 320);
        lblPlayer.setSize(PIECE_WIDTH, PIECE_HEIGHT);
        addActor(lblPlayer);
        /* Initialize chess pieces and listeners on each piece.*/
//        绘制每一个棋子图片
        Map<String, Piece> pieces = board.pieces;
        for (Map.Entry<String, Piece> stringPieceEntry : pieces.entrySet()) {
            String key = stringPieceEntry.getKey();
            int[] pos = stringPieceEntry.getValue().position;
            int[] sPos = modelToViewConverter(pos);
            Image lblPiece = new Image(new Texture("res/img/" + key.substring(0, 2) + ".png"));
            lblPiece.setPosition(sPos[0], sPos[1]);
            lblPiece.setSize(PIECE_WIDTH, PIECE_HEIGHT);
            lblPiece.addListener(new PieceOnClickListener(key));
            pieceObjects.put(stringPieceEntry.getKey(), lblPiece);
            addActor(lblPiece);
            lblPiece.setOrigin(Align.center);
            lblPiece.setRotation(180);
        }
        pointr = new Image(new Texture("qizi/selected.png"));
        pointb = new Image(new Texture("qizi/selected2.png"));
        addActor(pointb);
        addActor(pointr);
        pointr.setVisible(false);
        pointb.setVisible(false);

    }

    public void movePieceFromModel(String pieceKey, int[] to) {
        Image pieceObject = pieceObjects.get(pieceKey);
        int[] sPos = modelToViewConverter(to);
        pieceObject.setPosition(sPos[0], sPos[1]);
        /* Clear 'from' and 'to' info on the board */
        selectedPieceKey = null;
    }

    public void movePieceFromAI(String pieceKey, int[] to) {
        Piece inNewPos = board.getPiece(to);
        if (inNewPos != null) {
            removeActor(pieceObjects.get(inNewPos.key));
            pieceObjects.remove(inNewPos.key);
        }

        Image pieceObject = pieceObjects.get(pieceKey);
        int[] sPos = modelToViewConverter(to);
        pieceObject.setPosition(sPos[0], sPos[1]);

        /* Clear 'from' and 'to' info on the board */
        selectedPieceKey = null;
    }

    private int[] modelToViewConverter(int pos[]) {
        int sx = pos[1] * SX_COE + SX_OFFSET;
        int sy = pos[0] * SY_COE + SY_OFFSET;
        return new int[]{sx, sy};
    }

    private int[] viewToModelConverter(int sPos[]) {
        /* To make things right, I have to put an 'additional sy offset'. God knows why. */
        int ADDITIONAL_SY_OFFSET = 25;
        int y = (sPos[0] - SX_OFFSET) / SX_COE;
        int x = (sPos[1] - SY_OFFSET - ADDITIONAL_SY_OFFSET) / SY_COE;
        return new int[]{x, y};
    }

    public void showPlayer(char player) {
        lblPlayer.setDrawable(new TextureRegionDrawable(new Texture("res/img/" + player + ".png")));
        setVisible(true);
    }

    public void showWinner(char player) {
//        JOptionPane.showMessageDialog(null, (player == 'r') ? "Red player has won!" : "Black player has won!", "Intelligent Chinese Chess", JOptionPane.INFORMATION_MESSAGE);
//        System.exit(0);
    }

    public void xx2(String key,String selectedPieceKey,int []pos) {

        pieceObjects.get(key).remove();
        pieceObjects.remove(key);
        controller.moveChess(selectedPieceKey, pos, board);
        movePieceFromModel(selectedPieceKey, pos);
        pointb.setVisible(false);
        if (controller.hasWin() != 'x') {
            showWinner(board.player);
        }


    }

    class PieceOnClickListener extends ClickListener {
        private String key;

        PieceOnClickListener(String key) {
            this.key = key;
        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            if (LevelConfig.model==2&&LevelConfig.currentUser != board.player) {
                return super.touchDown(event, x, y, pointer, button);
            }
            if (selectedPieceKey != null && key.charAt(0) != board.player) {
                int[] pos = board.pieces.get(key).position;
                int[] selectedPiecePos = board.pieces.get(selectedPieceKey).position;
                /* If an enemy piece already has been selected.*/
                for (int[] each : Rules.getNextMove(selectedPieceKey, selectedPiecePos, board)) {
                    if (Arrays.equals(each, pos)) {
                        // Kill self and move that piece.

                        if (LevelConfig.model == 2) {
                            ClientDispatch.move222(key, selectedPieceKey, pos);
                        }

                        pieceObjects.get(key).remove();
                        pieceObjects.remove(key);
                        controller.moveChess(selectedPieceKey, pos, board);
                        movePieceFromModel(selectedPieceKey, pos);
                        pointb.setVisible(false);
                        if (controller.hasWin() != 'x') {
                            showWinner(board.player);
                        }
                        break;
                    }
                }
            } else if (key.charAt(0) == board.player) {
                /* Select the piece.*/
                selectedPieceKey = key;
                pointb.setVisible(true);
                Piece piece = board.pieces.get(selectedPieceKey);
                int[] ints = modelToViewConverter(piece.position);
                pointb.setPosition(ints[0]+PIECE_WIDTH/2,ints[1]+PIECE_HEIGHT/2, Align.center);
            }
            return super.touchDown(event, x, y, pointer, button);
        }

    }

    public void xx(String selectedPieceKey,int[] pos){
        controller.moveChess(selectedPieceKey, pos, board);
        //移动展示
        movePieceFromModel(selectedPieceKey, pos);

    }
    /**
     * 点击到桌面了
     */
    class BoardClickListener extends ClickListener {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            if (LevelConfig.model == 2&&LevelConfig.currentUser != board.player) {
                return super.touchDown(event, x, y, pointer, button);
            }
            if (selectedPieceKey != null) {
                pointb.setVisible(false);
                    int[] sPos = new int[]{(int) x, (int) y};
                    int[] pos = viewToModelConverter(sPos);
                    int[] selectedPiecePos = board.pieces.get(selectedPieceKey).position;
                    //找到 符合这个棋子可以移动的位置
                    for (int[] each : Rules.getNextMove(selectedPieceKey, selectedPiecePos, board)) {
                        //如果位置等于点击的位置那么久移动
                        if (Arrays.equals(each, pos)) {
                            //更改坐标
                            if (LevelConfig.model==2) {
                                ClientDispatch.move11(selectedPieceKey, pos);
                            }
                            controller.moveChess(selectedPieceKey, pos, board);
                            //移动展示
                            movePieceFromModel(selectedPieceKey, pos);
                            return super.touchDown(event, x, y, pointer, button);
                        }
                    }
            }
            return super.touchDown(event, x, y, pointer, button);
        }
    }
}
