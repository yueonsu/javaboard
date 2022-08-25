package com.yueonsu.www.board;

import com.yueonsu.www.auth.AuthenticationFacade;
import com.yueonsu.www.board.model.BoardEntity;
import com.yueonsu.www.board.model.BoardPageable;
import com.yueonsu.www.board.model.BoardResultVo;
import com.yueonsu.www.board.model.BoardVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;
    private final AuthenticationFacade authenticationFacade;

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

        BoardResultVo resultVo = new BoardResultVo();
        List<BoardVo> vo = null;
        try {
            vo = boardMapper.selBoardList(pageable);
        } catch (Exception e) {
            resultVo.setStatus("400");
            resultVo.setDesc("fail");
        }
        resultVo.setResult(vo);

        return resultVo;
    }

    /**
     * 게시판 페이징
     * @param pageable
     * @return
     */
    public BoardResultVo getPage(BoardPageable pageable) {
        BoardResultVo vo = new BoardResultVo();

        BoardPageable pageObj = new BoardPageable();
        pageObj.setPage(pageable.getPage());
        try {
            pageObj.setTotalCount(boardMapper.selBoardCount(pageable).getTotalCount());
        } catch (Exception e) {
            vo.setStatus("400");
            vo.setDesc("fail");
        }
        vo.setResult(pageObj);

        pageObj.setTotalCount(boardMapper.selBoardCount(pageable).getTotalCount());
        vo.setResult(pageObj);


        return vo;
    }

    /**
     * 글 등록
     * @param entity
     * @return
     */
    public BoardResultVo insBoard(BoardEntity entity) {
        // 로그인된 유저PK를 fkUserSeq 컬럼에 insert
        entity.setFkUserSeq(authenticationFacade.getLoginUserPk());
        BoardResultVo vo = new BoardResultVo();
        int result = 0;
        try {
            result = boardMapper.insBoard(entity);
        } catch (Exception ignored) {
            vo.setStatus("400");
            vo.setDesc("fail");
        }
        vo.setResult(result);
        if (0 < entity.getNBoardSeq()) {
            vo.setResult(entity);
        }

        return vo;
    }

    /**
     * 디테일 게시판
     * @param nBoardSeq
     * @return
     */
    public BoardResultVo selBoardDetail(int nBoardSeq) {
        BoardVo boardVo = null;
        BoardResultVo resultVo = new BoardResultVo();
        try {
            boardVo = boardMapper.selBoardDetail(nBoardSeq);
        } catch (Exception e) {
            resultVo.setStatus("400");
            resultVo.setDesc("fail");
        }
        resultVo.setResult(boardVo);
        resultVo.setLoginUserPk(authenticationFacade.getLoginUserPk());

        return resultVo;
    }

    /**
     * 수정 데이터 불러오기
     * @param nBoardSeq
     */
    public BoardResultVo selModData(int nBoardSeq) {
        BoardResultVo vo = new BoardResultVo();
        BoardEntity entity = null;
        try {
            entity = boardMapper.selModData(nBoardSeq);
        } catch (Exception e) {
            vo.setStatus("400");
            vo.setDesc("fail");
        }
        vo.setResult(entity);

        return vo;
    }

    /**
     * 글 수정
     * @param entity
     * @return
     */
    public BoardResultVo updBoard(BoardEntity entity) {
        BoardResultVo vo = new BoardResultVo();
        int loginUserPk = authenticationFacade.getLoginUserPk();
        entity.setFkUserSeq(loginUserPk);
        int result = 0;
        try {
            result = boardMapper.updBoard(entity);
        } catch (Exception e) {
            vo.setStatus("400");
            vo.setDesc("fail");
        }
        if(result == 0) {
            vo.setStatus("400");
            vo.setDesc("fail");
        }
        vo.setResult(result);

        return vo;
    }

    /**
     * 글 삭제
     * @param nBoardSeq
     * @return
     */
    public BoardResultVo delBoard(int nBoardSeq) {
        BoardResultVo vo = new BoardResultVo();
        int loginUserPk = authenticationFacade.getLoginUserPk();
        int result = 0;
        if(0 == loginUserPk) {
            vo.setResult(0);
            vo.setStatus("400");
            vo.setDesc("fail");
            return vo;
        }
        try {
            result = boardMapper.delBoard(nBoardSeq, loginUserPk);
        } catch (Exception ignored) {
            vo.setStatus("400");
            vo.setDesc("fail");
        }
        vo.setResult(result);

        return vo;
    }
}
