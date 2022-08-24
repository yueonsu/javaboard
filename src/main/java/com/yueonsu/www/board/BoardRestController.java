package com.yueonsu.www.board;

import com.yueonsu.www.board.model.BoardEntity;
import com.yueonsu.www.board.model.BoardPageable;
import com.yueonsu.www.board.model.BoardResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ajax/board")
@RequiredArgsConstructor
public class BoardRestController {

    private final BoardService boardService;

    /**
     * 게시글 리스트
     * @param page = 현재 페이지 번호
     * @param sel = 검색종류
     * @param text = 검색어
     */
    @GetMapping("list")
    public BoardResultVo getBoardList(int page, int sel, String text) {

        BoardPageable pageable = new BoardPageable();
        pageable.setPage(page);
        pageable.setSel(sel);
        pageable.setText(text);

        return boardService.selBoardList(pageable);
    }

    /**
     * 게시글 리스트 페이징
     * @param page = 현재 페이지 번호
     * @param sel = 검색종류
     * @param text = 검색어
     */
    @GetMapping("/page")
    public BoardResultVo getBoardPage(int page, int sel, String text) {

        BoardPageable pageable = new BoardPageable();
        pageable.setPage(page);
        pageable.setSel(sel);
        pageable.setText(text);

        return boardService.getPage(pageable);
    }

    /**
     * 디테일 게시글
     * @param nBoardSeq = 게시글 번호
     */
    @GetMapping("/detail")
    public BoardResultVo getDetailBoard(int nBoardSeq){
        return boardService.selBoardDetail(nBoardSeq);
    }

    @GetMapping("/delete")
    public BoardResultVo delBoard(int nBoardSeq) {
        BoardResultVo vo = new BoardResultVo();
        System.out.println(nBoardSeq);
        return vo;
    }
}
