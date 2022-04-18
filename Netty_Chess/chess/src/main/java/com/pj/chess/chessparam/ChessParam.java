package com.pj.chess.chessparam;

import static com.pj.chess.ChessConstant.BLACKPLAYSIGN;
import static com.pj.chess.ChessConstant.MaskChesses;
import static com.pj.chess.ChessConstant.NOTHING;
import static com.pj.chess.ChessConstant.REDPLAYSIGN;
import static com.pj.chess.ChessConstant.chessRoles;

import com.pj.chess.BitBoard;
import com.pj.chess.ChessConstant;
import com.pj.chess.Tools;

/**
 * @author pengjiu
 * Ϊ��ֹ���߳��£�һЩ����Ҫ�Ĳ�������ͬ������
 */
public class ChessParam {
	public  int[] board;	 // ����->����

	public  int[] allChess; //����->����
	
	public int[] baseScore=new int[2];
//	public int redBaseScore=0;  //�췽����
	
//	public int blackBaseScore=0; //�ڷ�����
	
	public int[] boardBitRow; //λ����  ��
	
	public int[] boardBitCol; //λ����  ��
	
	private int[] boardRemainChess; //ʣ����������
	
	//��������λ����
	public BitBoard maskBoardChesses;
	//���Ե�λ����
	public BitBoard[] maskBoardPersonalChesses;
	//���԰���ɫ�����λ����[��ɫ]
	public BitBoard[] maskBoardPersonalRoleChesses;
	
	//[���][0������������  1������������]
	private int[][] attackAndDefenseChesses=new int[2][2];  
	
    //ÿ�����Ӷ�ӦattackAndDefenseChesses ���±��
	public static  final int[] indexOfAttackAndDefense=new int[]{0,
		0,1,1,0,0,0,1,
		0,1,1,0,0,0,1
	};
	public ChessParam(int[] board,int[] allChess,int[] baseScore,int[] boardBitRow,int[] boardBitCol,int[] boardRemainChess,BitBoard maskBoardChesses,BitBoard[] maskBoardPersonalChesses,BitBoard[] maskBoardPersonalRoleChesses){
		this.board=board;
		this.allChess=allChess;
		this.baseScore=baseScore;
		this.boardBitRow=boardBitRow;
		this.boardBitCol=boardBitCol;
		this.boardRemainChess=boardRemainChess;
		this.maskBoardChesses=maskBoardChesses;
		this.maskBoardPersonalChesses=maskBoardPersonalChesses;
		this.maskBoardPersonalRoleChesses=maskBoardPersonalRoleChesses;
	}
	public ChessParam(ChessParam param){
		this.copyToSelf(param);
	}
	public void copyToSelf(ChessParam param){
		//����copy
		int[] allChessTemp = param.allChess;
		this.allChess=new int[allChessTemp.length];
		for(int i=0;i<allChessTemp.length;i++){
			this.allChess[i]=allChessTemp[i];
		}
		//����copy
		int[] boardTemp = param.board;
		this.board=new int[boardTemp.length];
		for(int i=0;i<boardTemp.length;i++){
			this.board[i]=boardTemp[i];
		}
		//λ������
		int[] boardBitRowTemp = param.boardBitRow;
		this.boardBitRow=new int[boardBitRowTemp.length];
		for(int i=0;i<boardBitRowTemp.length;i++){
			this.boardBitRow[i]=boardBitRowTemp[i];
		} 
		//λ������
		int[] boardBitColTemp = param.boardBitCol;
		this.boardBitCol=new int[boardBitColTemp.length];
		for(int i=0;i<boardBitColTemp.length;i++){
			this.boardBitCol[i]=boardBitColTemp[i];
		} 
		//��������
		int[] boardRemainChessTemp = param.boardRemainChess;
		this.boardRemainChess=new int[boardRemainChessTemp.length];
		for(int i=0;i<boardRemainChessTemp.length;i++){
			this.boardRemainChess[i]=boardRemainChessTemp[i];
		} 
		// ���������Ӻͷ�������������
		int[][] attackAndDefenseChessesTemp = param.attackAndDefenseChesses;
		for(int i=0;i<attackAndDefenseChessesTemp.length;i++){
			for(int j=0;j<attackAndDefenseChessesTemp[i].length;j++){
				this.attackAndDefenseChesses[i][j]=attackAndDefenseChessesTemp[i][j];
			}
		} 
		//����������λ����
		this.maskBoardChesses = new BitBoard(param.maskBoardChesses);
		
		this.maskBoardPersonalChesses=new BitBoard[param.maskBoardPersonalChesses.length];
		//����������λ����
		this.maskBoardPersonalChesses[ChessConstant.REDPLAYSIGN] =  new BitBoard(param.maskBoardPersonalChesses[ChessConstant.REDPLAYSIGN]);
		this.maskBoardPersonalChesses[ChessConstant.BLACKPLAYSIGN] =  new BitBoard(param.maskBoardPersonalChesses[ChessConstant.BLACKPLAYSIGN]);
		//������������ɫ����
		maskBoardPersonalRoleChesses=new BitBoard[param.maskBoardPersonalRoleChesses.length];
		for(int i=0;i<param.maskBoardPersonalRoleChesses.length;i++){
			this.maskBoardPersonalRoleChesses[i]=new BitBoard(param.maskBoardPersonalRoleChesses[i]);
		}
		
		
		//����
		this.baseScore[ChessConstant.REDPLAYSIGN] = param.baseScore[ChessConstant.REDPLAYSIGN];
		this.baseScore[ChessConstant.BLACKPLAYSIGN] = param.baseScore[ChessConstant.BLACKPLAYSIGN];
		
	}
	private int getPlayByChessRole(int chessRole){
		return chessRole>ChessConstant.REDKING?ChessConstant.BLACKPLAYSIGN:ChessConstant.REDPLAYSIGN;
	}
	public int getChessesNum(int chessRole){
		return boardRemainChess[chessRole];
	}
	public int getChessesNum(int play,int chessRole){
		return boardRemainChess[getRoleIndexByPlayRole(play, chessRole)];
	}
	/**
	 * @param chessRole ���ӽ�ɫ
	 *  ������������
	 */
	public void reduceChessesNum(int chessRole){
		boardRemainChess[chessRole]--;
		attackAndDefenseChesses[getPlayByChessRole(chessRole)][indexOfAttackAndDefense[chessRole]]--;
	}
	/**
	 * @param chessRole ���ӽ�ɫ
	 * ������������
	 */
	public void increaseChessesNum(int chessRole){
		boardRemainChess[chessRole]++;
		int play=getPlayByChessRole(chessRole);
		attackAndDefenseChesses[play][indexOfAttackAndDefense[chessRole]]++;
	}
	/**
	 * @return ������������
	 */
	public int getAllChessesNum(){
		int num=0;
		for(int i:boardRemainChess){
			num+=i;
		}
		return num;
	}
	//���й�����������
	public int getAttackChessesNum(int play){
		return attackAndDefenseChesses[play][0];
	}
	//���з�����������
	public int getDefenseChessesNum(int play){
		return attackAndDefenseChesses[play][1];
	}
	public BitBoard getBitBoardByPlayRole(int play,int role){
		return maskBoardPersonalRoleChesses[this.getRoleIndexByPlayRole(play, role)];
	}
	public int getRoleIndexByPlayRole(int play,int role){
		return role=play==ChessConstant.REDPLAYSIGN?role:(role+7);
	}
	public static void main(String[] args) {
		/*ChessParam chess1 = new ChessParam(new int[64],new int[32],0,0);
		ChessParam chess2 = new ChessParam(chess1);
		chess1.allChess[0]=10;
		System.out.println(chess2.allChess[0]);*/
	}
	public void initChessBaseScoreAndNum(){
		
		for (int i = 16; i < allChess.length; i++) {
			if(allChess[i]!=NOTHING){
				int site=allChess[i];
				int chessRole = chessRoles[board[allChess[i]]];
				int play=i < 32?BLACKPLAYSIGN:REDPLAYSIGN;
				increaseChessesNum(chessRole);
//				chessParamCont.baseScore[play]+=EvaluateComputeMiddle.chessBaseScore[i];
//				chessParamCont.baseScore[play]+=new EvaluateComputeMiddle(chessParamCont).chessAttachScore(chessRole,allChess[i]);
				maskBoardChesses.assignXor(MaskChesses[site]);
				maskBoardPersonalChesses[play].assignXor(MaskChesses[site]);
				maskBoardPersonalRoleChesses[chessRole].assignXor(MaskChesses[site]);
			}
		} 
		
	}
}











