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
    public BoardResultVo getBoardList(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "1") int sel, @RequestParam(defaultValue = "") String text) {
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
    public BoardResultVo getBoardPage(@RequestParam int page, @RequestParam int sel, @RequestParam String text) {
        BoardPageable pageable = new BoardPageable();
        pageable.setPage(page);
        pageable.setSel(sel);
        pageable.setText(text);
        return boardService.getPage(pageable);
    }

    /**
     * 게시글 등록
     * @param data
     */
    @PostMapping("/detail")
    public BoardResultVo authInsBoard(@RequestBody Map<String, Object> data) {
        BoardResultVo vo = new BoardResultVo();

        String title = "";
        String content = "";

        /**
         * 유효성 검사
         */
        try {
            title = data.get("stitle").toString();
            content = data.get("scontent").toString();
        } catch (Exception e) {
            vo.setStatus("400");
            vo.setDesc("제목과 내용을 확인해 주세요.");
            return vo;
        }
        if (0 == title.length() || 0 == content.length()) {
            vo.setStatus("400");
            vo.setDesc("제목과 내용을 확인해 주세요.");
            return vo;
        }

        BoardEntity entity = new BoardEntity();
        entity.setSTitle(title);
        entity.setSContent(content);
        entity.setFkUserSeq(authenticationFacade.getLoginUserPk());


        vo = boardService.insBoard(entity);

        return vo;
    }

    /**
     * 디테일 게시글
     * @param nBoardSeq = 게시글 번호
     */
    @GetMapping("/detail")
    public BoardResultVo getDetailBoard(@RequestParam int nBoardSeq){

        /**
         * 마지막 글 번호
         */
        int lastSeq = boardService.selBoardLastSeq();

        /**
         * 마지막 글 번호보다 크거나 1 보다 작으면 안됨
         */
        if (1 > nBoardSeq || lastSeq < nBoardSeq) {
            BoardResultVo vo = new BoardResultVo();
            vo.setStatus("400");
            vo.setDesc("fail");
            return vo;
        }
        return boardService.selBoardDetail(nBoardSeq);
    }

    /**
     * 수정 데이터 불러오기
     * @param nBoardSeq
     */
    @GetMapping("/mod")
    public BoardResultVo getModData(@RequestParam int nBoardSeq) {
        BoardResultVo vo = new BoardResultVo();

        int lastSeq = boardService.selBoardLastSeq();

        if (1 > nBoardSeq || lastSeq < nBoardSeq) {
            vo.setStatus("400");
            vo.setDesc("fail");
            return vo;
        }
        vo = boardService.selModData(nBoardSeq);
        return vo;
    }


    /**
     * 게시물 수정
     * @param data = sTitle, sContent, nBoardSeq
     *
     */
    @PutMapping("/detail")
    public BoardResultVo authModBoard(@RequestBody HashMap<String, Object> data) {
        BoardResultVo vo = new BoardResultVo();

        int lastSeq = boardService.selBoardLastSeq();

        String title = "";
        String content = "";
        int boardSeq = 0;

        try {
            title = data.get("stitle").toString();
            content = data.get("scontent").toString();
            boardSeq = Integer.parseInt(data.get("nboardSeq").toString());
        } catch (NumberFormatException e) {
            vo.setStatus("400");
            vo.setDesc("fail");
            return vo;
        }

        /**
         * 글 번호가 마지막 글 번호 보다 크거나 1 보다 작으면 안됨
         */
        if(1 > boardSeq || lastSeq < boardSeq) {
            vo.setStatus("400");
            vo.setDesc("fail");
            return vo;
        }

        BoardEntity entity = new BoardEntity();
        entity.setSTitle(title);
        entity.setSContent(content);
        entity.setNBoardSeq(boardSeq);
        entity.setFkUserSeq(authenticationFacade.getLoginUserPk());

        return boardService.updBoard(entity);
    }

    /**
     * 게시글 삭제
     * @param nBoardSeq
     */
    @DeleteMapping("/detail")
    public BoardResultVo authDelBoard(int nBoardSeq) {
        BoardResultVo vo = new BoardResultVo();

        int lastSeq = boardService.selBoardLastSeq();

        if (1 > nBoardSeq || lastSeq < nBoardSeq) {
            vo.setStatus("400");
            vo.setDesc("fail");
            return vo;
        }

        BoardEntity entity = new BoardEntity();
        entity.setNBoardSeq(nBoardSeq);
        entity.setFkUserSeq(authenticationFacade.getLoginUserPk());
        return boardService.delBoard(entity);
    }
}
