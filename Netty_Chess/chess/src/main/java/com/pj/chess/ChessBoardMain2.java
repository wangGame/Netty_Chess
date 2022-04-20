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
			"�ڽ�", "�ڳ�", "�ڳ�", "����", "����", "����", "����", "����", "����", "��ʿ", "��ʿ", "����", "����", "����", "����", "����",
			"�콫", "�쳵", "�쳵", "����", "����", "����", "����", "����", "����", "��ʿ", "��ʿ", "����", "����", "����", "����", "����",
	};
	public static final String[] chessIcon = new String[]{
			null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
			"BK", "BR", "BR", "BN", "BN", "BC", "BC", "BB", "BB", "BA", "BA", "BP", "BP", "BP", "BP", "BP",
			"RK", "RR", "RR", "RN", "RN", "RC", "RC", "RB", "RB", "RA", "RA", "RP", "RP", "RP", "RP", "RP",
	};
	int lastTimeCheckedSite = -1; //�ϴ�ѡ�����ӵ�λ��
	private ButtonActionListener my = new ButtonActionListener();
	Image[] buttons = new Image[BOARDSIZE90];
	int play = 1;
	volatile boolean[] android = new boolean[]{false, false};
	int begin = -1;
	int end = 0;
	private static ComputerLevel computerLevel = ComputerLevel.greenHand; //Ĭ��
	boolean isBackstageThink = false;
	boolean computeFig = false;
	TranspositionTable transTable;
	ChessMovePlay cmp = null;
	AICoreHandler _AIThink = new AICoreHandler();
	AICoreHandler backstageAIThink = new AICoreHandler();
	//	public static List<MoveNode> backMove=new ArrayList<MoveNode>();
	NodeLink moveHistory;
	int turn_num = 0;//�غ���
	ChessParam chessParamCont;
	private static boolean isSound = false;

	public void initHandler() {
		String startFen = "c6c5  rnbakabnr/9/1c5c1/p1p1p1p1p/9/9/P1P1P1P1P/1C5C1/9/RNBAKABNR b - - 0 1";
		String[] fenArray = Tools.fenToFENArray(startFen);
		//���Ʒ��뵽������
		int[] boardTemp = Tools.parseFEN(fenArray[1]);
		//�������̳�ʼ����
		chessParamCont = ChessInitialize.getGlobalChessParam(boardTemp);
		//��ʼ��������
		for (int i = 0; i < boardTemp.length; i++) {
			if (boardTemp[i] > 0) {
				this.setBoardIconUnchecked(i, boardTemp[i]);
			}
		}
		//��ʼ����(Ҫ�����Ӱںú���ܼ������ֵ)
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
//		"��������"
		Image computerMove = new Image();
		computerMove.addListener(my);
		constrol.addActor(button);
		constrol.addActor(computerMove);
		this.addActor(constrol);
		this.addListener(my);
		//��ʼ������
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
					if (chessParamCont.board[i] != NOTHING && (chessParamCont.board[i] & chessPlay[play]) == chessPlay[play]) {//�Է�����
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
//		if (JOptionPane.showConfirmDialog(this, msg + "�Ƿ������", "��Ϣ",
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
				msg = (play == BLACKPLAYSIGN ? "�ڷ�" : "�췽") + "�����̵Ľ�����";
				isGameOver = true;
				//�Լ�˧����
			} else if (chessParamCont.allChess[chessPlay[BLACKPLAYSIGN]] == NOTHING || moveHistory.getMoveNode().destChess == chessPlay[BLACKPLAYSIGN]) {
				isGameOver = true;
				msg = "�ڷ�����Ű��";
			} else if (chessParamCont.allChess[chessPlay[REDPLAYSIGN]] == NOTHING || moveHistory.getMoveNode().destChess == chessPlay[REDPLAYSIGN]) {
				msg = "�췽����Ű��";
				isGameOver = true;
			} else if (moveHistory.getMoveNode().score == -LONGCHECKSCORE) {
				msg = (play == BLACKPLAYSIGN ? "�ڷ�" : "�췽") + "�����и���";
				isGameOver = true;
			} else if (moveHistory.getMoveNode().score <= -(maxScore - 2)) {
				setCheckedLOSS(play);
				msg = (play == BLACKPLAYSIGN ? "�ڷ�" : "�췽") + "�����̵Ľ�����";
				isGameOver = true;
			} else if (moveHistory.getMoveNode().score >= (maxScore - 2)) {
				setCheckedLOSS(1 - play);
				msg = (play == BLACKPLAYSIGN ? "�ڷ�" : "�췽") + "Ӯ�������յ�ʤ����";
				isGameOver = true;
			} else if (chessParamCont.getAttackChessesNum(REDPLAYSIGN) == 0 && chessParamCont.getAttackChessesNum(BLACKPLAYSIGN) == 0) {
				msg = "˫�����޹������Ӵ��˺��壡";
				isGameOver = true;
			} else if (turn_num >= 300) {
				msg = "��ս300�غ�δ��ʤ������";
				isGameOver = true;
			}
			if (isGameOver) {
				gameOverMsg(msg);
			} else {
				MoveNode moveNode = moveHistory.getMoveNode();
				if (cmp.checked(1 - play)) {//�����Ƿ񱻽�
				} else if (moveNode.destChess != NOTHING) {
				} else {
				}
			}
			return isGameOver;
		}
		private void opponentMove() {
			//�鿴�Ƿ���ʤ��
			if (!checkGameOver()) {
				turn_num++;
				play = 1 - play; //����˫��
				//�����Ƿ�Ϊ����
				if (android[play]) {
					computeThinkStart();
				}
			}
		}

		private void computeThinkStart() {
			//���ú�̨˼��
			if (isBackstageThink && (guessLink != null && moveHistory != null)) {
				//�鿴�Ƿ����
				if (guessLink.getMoveNode().equals(moveHistory.getMoveNode())) {
					new Thread() {
						public void run() {
							System.out.println("---->�²����У���");
							try {
								//����ʱ�����
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
							System.out.println("--->δ����");
							//���û�н�������
							backstageAIThink.setStop();
							try {
								backstageThinkThread.join();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							System.out.println("--->����˼��");
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

		//��̨˼��
		private void backstageThink() {
			if (!isBackstageThink) {
				return;
			}
			if (moveHistory.getNextLink() != null && moveHistory.getNextLink().getMoveNode() != null) {

				backstageThinkThread = new Thread() {
					public void run() {
						//�²���ŷ�
						guessLink = moveHistory.getNextLink();
						backstageAIThink.setLocalVariable(computerLevel, chessParamCont, guessLink);
						System.out.println("---->��ʼ�²�(" + guessLink.getMoveNode() + ")");
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
		 * ��ȡ�ϴα����¼
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
