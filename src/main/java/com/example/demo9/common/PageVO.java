package com.example.demo9.common;

import com.example.demo9.entity.Board;
import lombok.Data;

import java.util.List;

@Data
public class PageVO {
	private int pag;
	private int pageSize;
	private int totRecCnt;
	private int totPage;
	private int startIndexNo;
	private int curScrStartNo;
	private int blockSize;
	private int curBlock;
	private int lastBlock;
	
	private String section;	// 'guest/board/pds/member'....
	private String part;		// '학습/여행/음식/기타'...
	private String search;  // '글제목/글쓴이/글내용'
	private String searchString;  // '검색어...'
	private String searchStr; // '글제목/글쓴이/글내용'
	private String boardFlag;	// 검색기에서 글내용보기 호출시 사용하는 변수
	
	private int level;	// 회원 등급(초기값:99 - 비회원)

  private List<Board> boardList;
	
}
