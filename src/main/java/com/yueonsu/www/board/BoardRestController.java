package com.yueonsu.www.board;

import com.yueonsu.www.auth.AuthenticationFacade;
import com.yueonsu.www.board.model.BoardEntity;
import com.yueonsu.www.board.model.BoardPageable;
import com.yueonsu.www.board.model.BoardResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/ajax/board")
@RequiredArgsConstructor
public class BoardRestController {

    private final BoardService boardService;
    private final AuthenticationFacade authenticationFacade;

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

    @PostMapping("/detail")
    public BoardResultVo authInsBoard(@RequestBody Map<String, Object> data) {
        String sTitle = data.get("stitle").toString();
        String sContent = data.get("scontent").toString();
        BoardEntity entity = new BoardEntity();
        entity.setSTitle(sTitle);
        entity.setSContent(sContent);
        entity.setFkUserSeq(authenticationFacade.getLoginUserPk());

        return boardService.insBoard(entity);
    }

    /**
     * 디테일 게시글
     * @param nBoardSeq = 게시글 번호
     */
    @GetMapping("/detail")
    public BoardResultVo getDetailBoard(int nBoardSeq){
        return boardService.selBoardDetail(nBoardSeq);
    }

    /**
     * 수정 데이터 불러오기
     * @param nBoardSeq
     */
    @GetMapping("/mod")
    public BoardResultVo getModData(int nBoardSeq) {
        return boardService.selModData(nBoardSeq);
    }


    /**
     * 게시물 수정
     * @param data = sTitle, sContent, nBoardSeq
     *
     */
    @PutMapping("/detail")
    public BoardResultVo authModBoard(@RequestBody HashMap<String, Object> data) {

        String sTitle = data.get("stitle").toString();
        String sContent = data.get("scontent").toString();
        int nBoardSeq = Integer.parseInt(data.get("nboardSeq").toString());

        BoardEntity entity = new BoardEntity();
        entity.setSTitle(sTitle);
        entity.setSContent(sContent);
        entity.setNBoardSeq(nBoardSeq);
        entity.setFkUserSeq(authenticationFacade.getLoginUserPk());

        return boardService.updBoard(entity);
    }

    /**
     * 게시글 삭제
     * @param nBoardSeq
     */
    @DeleteMapping("/detail")
    public BoardResultVo authDelBoard(int nBoardSeq) {
        BoardEntity entity = new BoardEntity();
        entity.setNBoardSeq(nBoardSeq);
        entity.setFkUserSeq(authenticationFacade.getLoginUserPk());

        return boardService.delBoard(entity);
    }
}
