package com.example.demo9.common;

import com.example.demo9.entity.Board;
import com.example.demo9.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Pagination {

  private final BoardRepository boardRepository;

  public PageVO pagination(PageVO pageVO) {	// 각각의 변수로 받으면 초기값처리를 spring이 자동할수 있으나, 객체로 받으면 개별 문자/객체 자료에는 null이 들어오기에 따로 초기화 작업처리해야함.
    //int pag = pageVO.getPag() == 0 ? 0 : pageVO.getPag();
    int pag = pageVO.getPag();
    int pageSize = pageVO.getPageSize() == 0 ? 10 : pageVO.getPageSize();
    int level = pageVO.getLevel() == 0 ? 99 : pageVO.getLevel();
    String part = pageVO.getPart() == null ? "" : pageVO.getPart();

    int totRecCnt = 0, totPage = 0;

    //Pageable pageable = PageRequest.of(pag, pageSize, Sort.by("id").descending());
    PageRequest pageable = PageRequest.of(pag, pageSize, Sort.by("id").descending());

    if(pageVO.getSection().equals("board")) {
      Page<Board> page;

      if (pageVO.getSearch() != null && !pageVO.getSearch().isEmpty()) {
        if (pageVO.getSearch().equals("title")) page = boardRepository.findByTitleContaining(pageVO.getSearchString(), pageable);
        else if (pageVO.getSearch().equals("name")) page = boardRepository.findByNameContaining(pageVO.getSearchString(), pageable);
        else page = boardRepository.findByContentContaining(pageVO.getSearchString(), pageable);
      }
      else page = boardRepository.findAll(pageable);

      List<Board> boardList = page.getContent();

      boardList.forEach((board) -> {
        board.setHourDiff(Duration.between(board.getWDate(), LocalDateTime.now()).toHours());
        //board.setDateDiff(Duration.between(board.getWDate(), LocalDateTime.now()).toDays());
        board.setDateDiff(LocalDateTime.now().getDayOfMonth() - board.getWDate().getDayOfMonth());
        board.setReplyCnt(board.getBoardReplies().size());
      });

      pageVO.setBoardList(boardList);

      totRecCnt = (int) page.getTotalElements();
      totPage = page.getTotalPages();
    }

    int startIndexNo = pag * pageSize;
		int curScrStartNo = totRecCnt - startIndexNo;
		
		int blockSize = 3;
    int curBlock = ((pag + 1) - 1) / blockSize;
    int lastBlock = (totPage - 1) / blockSize;
		pageVO.setPag(pag+1);
		pageVO.setPageSize(pageSize);
		pageVO.setTotRecCnt(totRecCnt);
		pageVO.setTotPage(totPage);
		pageVO.setStartIndexNo(startIndexNo);
		pageVO.setCurScrStartNo(curScrStartNo);
		pageVO.setBlockSize(blockSize);
		pageVO.setCurBlock(curBlock);
		pageVO.setLastBlock(lastBlock);

		if(pageVO.getSearch() != null) {
			if(pageVO.getSearch().equals("title")) pageVO.setSearchStr("글제목");
			else if(pageVO.getSearch().equals("name")) pageVO.setSearchStr("글쓴이");
			else if(pageVO.getSearch().equals("content")) pageVO.setSearchStr("글내용");
		}
		pageVO.setSearch(pageVO.getSearch());
		pageVO.setSearchString(pageVO.getSearchString());
		
		pageVO.setPart(part);
		pageVO.setBoardFlag(pageVO.getBoardFlag());
		
		pageVO.setLevel(level);

		return pageVO;
	}


}
