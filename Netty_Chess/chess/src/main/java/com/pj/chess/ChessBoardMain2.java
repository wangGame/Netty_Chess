package com.pj.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.pj.chess.chessmove.ChessMovePlay;
import com.pj.chess.chessmove.MoveNode;
import com.pj.chess.chessparam.ChessParam;
import com.pj.chess.evaluate.EvaluateComputeMiddleGame;
import com.pj.chess.zobrist.TranspositionTable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

import static com.pj.chess.ChessConstant.*;

public class ChessBoardMain2 extends Group {

	private static final long serialVersionUID = 1L;
	public static final String[] chessName = new String[]{
			"   ", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
			"黑将", "黑车", "黑车", "黑马", "黑马", "黑炮", "黑炮", "黑象", "黑象", "黑士", "黑士", "黑卒", "黑卒", "黑卒", "黑卒", "黑卒",
			"红将", "红车", "红车", "红马", "红马", "红炮", "红炮", "红象", "红象", "红士", "红士", "红卒", "红卒", "红卒", "红卒", "红卒",
	};
	public static final String[] chessIcon = new String[]{
			null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
			"BK", "BR", "BR", "BN", "BN", "BC", "BC", "BB", "BB", "BA", "BA", "BP", "BP", "BP", "BP", "BP",
			"RK", "RR", "RR", "RN", "RN", "RC", "RC", "RB", "RB", "RA", "RA", "RP", "RP", "RP", "RP", "RP",
	};
	int lastTimeCheckedSite = -1; //上次选中棋子的位置
	private ButtonActionListener my = new ButtonActionListener();
	Image[] buttons = new Image[BOARDSIZE90];
	int play = 1;
	volatile boolean[] android = new boolean[]{false, false};
	int begin = -1;
	int end = 0;
	private static ComputerLevel computerLevel = ComputerLevel.greenHand; //默认
	boolean isBackstageThink = false;
	boolean computeFig = false;
	TranspositionTable transTable;
	ChessMovePlay cmp = null;
	AICoreHandler _AIThink = new AICoreHandler();
	AICoreHandler backstageAIThink = new AICoreHandler();
	//	public static List<MoveNode> backMove=new ArrayList<MoveNode>();
	NodeLink moveHistory;
	int turn_num = 0;//回合数
	ChessParam chessParamCont;
	private static boolean isSound = false;

	public void initHandler() {
		String startFen = "c6c5  rnbakabnr/9/1c5c1/p1p1p1p1p/9/9/P1P1P1P1P/1C5C1/9/RNBAKABNR b - - 0 1";
		String[] fenArray = Tools.fenToFENArray(startFen);
		//将牌放入到数组中
		int[] boardTemp = Tools.parseFEN(fenArray[1]);
		//根据棋盘初始参数
		chessParamCont = ChessInitialize.getGlobalChessParam(boardTemp);
		//初始界面棋子
		for (int i = 0; i < boardTemp.length; i++) {
			if (boardTemp[i] > 0) {
				this.setBoardIconUnchecked(i, boardTemp[i]);
			}
		}
		//初始局面(要把棋子摆好后才能计算局面值)
		transTable = new TranspositionTable();
		if (moveHistory == null) {
			moveHistory = new NodeLink(1 - play, transTable.boardZobrist32, transTable.boardZobrist64);
		}
		play = 1 - moveHistory.play;
		android[1 - play] = true;
		cmp = new ChessMovePlay(chessParamCont, transTable, new EvaluateComputeMiddleGame(chessParamCont));
	}

	Group jpanelContent;

	private void setCenter() {
		if (jpanelContent != null) {
			jpanelContent.remove();
		}
		jpanelContent = new Group();
		Image image = new Image(new Texture("images/MAIN.GIF"));
		image.setScale(720/image.getWidth());
		jpanelContent.addActor(image);
		Group panel = new Group();
		jpanelContent.setSize(image.getWidth()*image.getScaleX(),image.getHeight()*image.getScaleX());
		float size = 75;
		int y = 0;
		for (int i = 0; i < BOARDSIZE90; i++) {
			Image p = new Image();
			p.addListener(my);
			p.setSize(size, size);
			buttons[i] = p;
			panel.addActor(p);

			if (i != 0 && i % 9 == 0) {
				y++;
			}
			buttons[i].setX(size * (i % 9));
			buttons[i].setY(size * y);

		}

		this.addActor(jpanelContent);
		jpanelContent.addActor(panel);


	}

	public ChessBoardMain2() {
		setCenter();
		Group constrol = new Group();
		Image button = new Image();
		button.addListener(my);
//		"立即走棋"
		Image computerMove = new Image();
		computerMove.addListener(my);
		constrol.addActor(button);
		constrol.addActor(computerMove);
		this.addActor(constrol);
		this.addListener(my);
		//初始处理器
		initHandler();
		this.setSize(568, 680);
		this.setVisible(true);
	}

	public void setBoardIconUnchecked(int site, int chess) {
		if (chess == NOTHING) {
			buttons[site].setDrawable(null);
		} else {
			buttons[site].setDrawable(new TextureRegionDrawable(new Texture("images/" + chessIcon[chess] + ".GIF")));
		}
	}

	public void setBoardIconChecked(int site, int chess) {
		buttons[site].setDrawable(new TextureRegionDrawable(new Texture("images/" + chessIcon[chess] + "S.GIF")));
	}

	public void setCheckedLOSS(int play) {
		buttons[chessParamCont.allChess[chessPlay[play]]].setDrawable(new TextureRegionDrawable(new Texture(chessIcon[chessPlay[play]] + "M")));
	}

	public void move(MoveNode moveNode) {
		if (lastTimeCheckedSite != -1) {
			setBoardIconUnchecked(lastTimeCheckedSite, chessParamCont.board[lastTimeCheckedSite]);
		}
		setBoardIconUnchecked(moveNode.srcSite, NOTHING);
		setBoardIconChecked(moveNode.destSite, moveNode.srcChess);
		lastTimeCheckedSite = moveNode.destSite;
	}

	class ButtonActionListener extends ClickListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			if (android[play]) {
				return false;
			}
			for (int i = 0; i < buttons.length; i++) {
				Image p = buttons[i];
				if (p == event.getTarget()) {
					if (chessParamCont.board[i] != NOTHING && (chessParamCont.board[i] & chessPlay[play]) == chessPlay[play]) {//自方子力
						if (i != begin) {
							begin = i;

							setBoardIconChecked(i, chessParamCont.board[i]);
							if (lastTimeCheckedSite != -1) {
								setBoardIconUnchecked(lastTimeCheckedSite, chessParamCont.board[lastTimeCheckedSite]);
							}
							lastTimeCheckedSite = begin;
						}
						return false;
					} else if (begin == -1) {
						return false;
					}
					end = i;
					if (this.checkZFPath(begin, end, play)) {
						MoveNode moveNode = new MoveNode(begin, end, chessParamCont.board[begin], chessParamCont.board[end], 0);
						showMoveNode(moveNode);
						NodeLink nextLink = new NodeLink(play, transTable.boardZobrist32, transTable.boardZobrist64);
						nextLink.setMoveNode(moveNode);
						moveHistory.setNextLink(nextLink);
						moveHistory = moveHistory.getNextLink();
						begin = -1;
						opponentMove();
					}
				}
			}
			return super.touchDown(event, x, y, pointer, button);
		}


		private boolean checkZFPath(int srcSite, int destSite, int play) {
			if (chessParamCont.board[srcSite] == NOTHING) {
				return false;
			}
			MoveNode moveNode = new MoveNode(srcSite, destSite, chessParamCont.board[srcSite], chessParamCont.board[destSite], 0);
			return cmp.legalMove(play, moveNode);
		}

		private void unMove(MoveNode moveNode) {
			if (lastTimeCheckedSite != -1) {
				setBoardIconUnchecked(lastTimeCheckedSite, chessParamCont.board[lastTimeCheckedSite]);
			}
			if (moveNode.srcChess == NOTHING) {
				buttons[moveNode.srcSite].setDrawable(null);
			} else {
				setBoardIconUnchecked(moveNode.srcSite, moveNode.srcChess);
			}
			if (moveNode.destChess == NOTHING) {
				buttons[moveNode.destChess].setDrawable(null);
			} else {
				setBoardIconChecked(moveNode.destSite, moveNode.destChess);
			}
			lastTimeCheckedSite = moveNode.destSite;
		}
		public void gameOverMsg(String msg) {
//		if (JOptionPane.showConfirmDialog(this, msg + "是否继续？", "信息",
//				 JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
////			dispose();
//			new ChessBoardMain2();
//		} else{
////			dispose();
//		}
		}
		private boolean checkGameOver() {
			boolean isGameOver = false;
			String msg = null;
			if (moveHistory == null || moveHistory.getMoveNode() == null) {
				msg = (play == BLACKPLAYSIGN ? "黑方" : "红方") + "被残忍的将死！";
				isGameOver = true;
				//自己帅被吃
			} else if (chessParamCont.allChess[chessPlay[BLACKPLAYSIGN]] == NOTHING || moveHistory.getMoveNode().destChess == chessPlay[BLACKPLAYSIGN]) {
				isGameOver = true;
				msg = "黑方被完虐！";
			} else if (chessParamCont.allChess[chessPlay[REDPLAYSIGN]] == NOTHING || moveHistory.getMoveNode().destChess == chessPlay[REDPLAYSIGN]) {
				msg = "红方被完虐！";
				isGameOver = true;
			} else if (moveHistory.getMoveNode().score == -LONGCHECKSCORE) {
				msg = (play == BLACKPLAYSIGN ? "黑方" : "红方") + "长将判负！";
				isGameOver = true;
			} else if (moveHistory.getMoveNode().score <= -(maxScore - 2)) {
				setCheckedLOSS(play);
				msg = (play == BLACKPLAYSIGN ? "黑方" : "红方") + "被残忍的将死！";
				isGameOver = true;
			} else if (moveHistory.getMoveNode().score >= (maxScore - 2)) {
				setCheckedLOSS(1 - play);
				msg = (play == BLACKPLAYSIGN ? "黑方" : "红方") + "赢得了最终的胜利！";
				isGameOver = true;
			} else if (chessParamCont.getAttackChessesNum(REDPLAYSIGN) == 0 && chessParamCont.getAttackChessesNum(BLACKPLAYSIGN) == 0) {
				msg = "双方都无攻击棋子此乃和棋！";
				isGameOver = true;
			} else if (turn_num >= 300) {
				msg = "大战300回合未分胜负啊！";
				isGameOver = true;
			}
			if (isGameOver) {
				gameOverMsg(msg);
			} else {
				MoveNode moveNode = moveHistory.getMoveNode();
				if (cmp.checked(1 - play)) {//对手是否被将
				} else if (moveNode.destChess != NOTHING) {
				} else {
				}
			}
			return isGameOver;
		}
		private void opponentMove() {
			//查看是否以胜利
			if (!checkGameOver()) {
				turn_num++;
				play = 1 - play; //交换双方
				//对手是否为电脑
				if (android[play]) {
					computeThinkStart();
				}
			}
		}

		private void computeThinkStart() {
			//设置后台思考
			if (isBackstageThink && (guessLink != null && moveHistory != null)) {
				//查看是否猜中
				if (guessLink.getMoveNode().equals(moveHistory.getMoveNode())) {
					new Thread() {
						public void run() {
							System.out.println("---->猜测命中！！");
							try {
								//加入时间控制
								backstageAIThink.launchTimer();
								backstageThinkThread.join();
							} catch (InterruptedException e) {
								e.printStackTrace();
								computeThink();
							}
							computeAIMoving(guessLink.getNextLink());
						}
					}.start();
				} else {
					new Thread() {
						public void run() {
							System.out.println("--->未命中");
							//如果没中进行运算
							backstageAIThink.setStop();
							try {
								backstageThinkThread.join();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							System.out.println("--->重新思考");
							computeThink();
						}
					}.start();
				}
			} else {
				computeThink();
			}
		}

		private void computeThink() {
			new Thread() {
				public void run() {
					_AIThink.setLocalVariable(computerLevel, chessParamCont, moveHistory);
					_AIThink.launchTimer();
					_AIThink.run();
					Gdx.app.postRunnable(() -> {

						computeAIMoving(moveHistory.getNextLink());
					});
				}
			}.start();
		}

		private void computeAIMoving(NodeLink nodeLink) {
			moveHistory = nodeLink;
			// if(!checkGameOver()){
			if (nodeLink != null && nodeLink.getMoveNode() != null) {
				MoveNode moveNode = nodeLink.getMoveNode();
				showMoveNode(moveNode);
			}
			opponentMove();
			backstageThink();
			// }
		}

		private Thread backstageThinkThread = null;
		private NodeLink guessLink;

		//后台思考
		private void backstageThink() {
			if (!isBackstageThink) {
				return;
			}
			if (moveHistory.getNextLink() != null && moveHistory.getNextLink().getMoveNode() != null) {

				backstageThinkThread = new Thread() {
					public void run() {
						//猜测的着法
						guessLink = moveHistory.getNextLink();
						backstageAIThink.setLocalVariable(computerLevel, chessParamCont, guessLink);
						System.out.println("---->开始猜测(" + guessLink.getMoveNode() + ")");
						backstageAIThink.guessRun(guessLink.getMoveNode());
					}
				};
				backstageThinkThread.start();
			}
		}

		private void showMoveNode(MoveNode moveNode) {
			if (moveNode != null) {
				move(moveNode);
				cmp.moveOperate(moveNode);
				transTable.synchroZobristBoardToStatic();
			}
		}

		/*
		 * 记取上次保存记录
		 */
		public String readSaved() {
			String fen = null;
			FileInputStream fileInput = null;
			try {
				File chessFile = new File("chess.txt");
				fileInput = new FileInputStream(chessFile);
				BufferedReader bufferedReader = new BufferedReader(
						new java.io.InputStreamReader(fileInput));

				while (bufferedReader.ready()) {
					fen = bufferedReader.readLine();
				}
				if (fen != null) {
					chessFile.deleteOnExit();
					fen = "c6c5  rnbakabnr/9/1c5c1/p1p1p1p1p/9/9/P1P1P1P1P/1C5C1/9/RNBAKABNR b - - 0 1";
				}
			} catch (Exception e) {
				fen = "c6c5  rnbakabnr/9/1c5c1/p1p1p1p1p/9/9/P1P1P1P1P/1C5C1/9/RNBAKABNR b - - 0 1";
			} finally {
				if (fileInput != null) {
					try {
						fileInput.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return fen;
		}
	}
}
