package com.pj.chess.zobrist;

import java.util.List;

import com.pj.chess.chessmove.MoveNode;

public class HashItem {
	//У���
	public long checkSum;
	//hash����  �ϱ߽�  �±߽�  ��ȷֵ
	public int entry_type;
	//ֵ
	public int value;
	//����
	public int depth; 
	
	public MoveNode moveNode;
	//�Ƿ��������
	public boolean isExists=true;
	
//	public boolean isCheckMate=false;
	//�Ƿ�Ϊ���ֿ��е�����
//	public boolean isFEN=false;
//	public short moveNum=0;
//	public int play;
	public HashItem(){};
	public HashItem(HashItem copy){
		copyToSelf(copy);
	};
	public void copyToSelf(HashItem copy){
		this.checkSum=copy.checkSum;
		this.entry_type=copy.entry_type;
		this.value=value;
		this.depth=depth;
		this.moveNode=moveNode;
		this.isExists=this.isExists;
	}
	
}
