package com.yueonsu.www.board;

import com.yueonsu.www.auth.AuthenticationFacade;
import com.yueonsu.www.board.model.BoardEntity;
import com.yueonsu.www.board.model.BoardPageable;
import com.yueonsu.www.board.model.BoardResultVo;
import com.yueonsu.www.board.model.BoardVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;
    private final AuthenticationFacade authenticationFacade;

    // 글 등록
    public int insBoard(BoardEntity entity) {

        // 로그인된 유저PK를 fkUserSeq 컬럼에 insert
        entity.setFkUserSeq(authenticationFacade.getLoginUserPk());
        return boardMapper.insBoard(entity);
    }

    /**
     * 게시글 리스트
     * @param pageable = 페이지번호, 검색종류, 검색어
     */
    public BoardResultVo selBoardList(BoardPageable pageable) {
        // 1페이지 보다 작을 때
        pageable.setPage(Math.max(pageable.getPage(), 1));

        // 마지막 페이지 보다 클 때
        pageable.setTotalCount(boardMapper.selBoardCount(pageable).getTotalCount());
        pageable.setPage(Math.min(pageable.getPage(), pageable.getMaxPage()));

        String status = "200";
        String desc = "success";
        List<BoardVo> vo = null;
        try {
            vo = boardMapper.selBoardList(pageable);
        } catch (Exception e) {
            status = "400";
            desc = "fail";
        }

        BoardResultVo resultVo = new BoardResultVo();
        resultVo.setStatus(status);
        resultVo.setDesc(desc);
        resultVo.setResult(vo);

        return resultVo;
    }

    // 페이징
    public BoardResultVo getPage(BoardPageable pageable) {

        String status = "200";
        String desc = "success";

        BoardPageable pageObj = new BoardPageable();
        try {
            pageObj.setPage(pageable.getPage());
            pageObj.setTotalCount(boardMapper.selBoardCount(pageable).getTotalCount());
        } catch (Exception e) {
            status = "400";
            desc = "fail";
        }

        BoardResultVo vo = new BoardResultVo();
        vo.setStatus(status);
        vo.setDesc(desc);
        vo.setResult(pageObj);

        pageObj.setTotalCount(boardMapper.selBoardCount(pageable).getTotalCount());
        vo.setResult(pageObj);


        return vo;
    }

    // 게시글 디테일
    public BoardResultVo selBoardDetail(int nBoardSeq) {
        BoardVo boardVo = null;
        String status = "200";
        String desc = "success";
        try {
            boardVo = boardMapper.selBoardDetail(nBoardSeq);
        } catch (Exception e) {
            status = "400";
            desc= "fail";
        }
        BoardResultVo resultVo = new BoardResultVo();
        resultVo.setStatus(status);
        resultVo.setDesc(desc);
        resultVo.setResult(boardVo);

        return resultVo;
    }
}
