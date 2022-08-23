package com.yueonsu.www.board;

import com.yueonsu.www.auth.AuthenticationFacade;
import com.yueonsu.www.board.model.BoardEntity;
import com.yueonsu.www.board.model.BoardPageable;
import com.yueonsu.www.board.model.BoardVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    @Autowired private BoardMapper boardMapper;
    @Autowired private AuthenticationFacade authenticationFacade;

    public int insBoard(BoardEntity entity) {

        // 로그인된 유저PK를 fkUserSeq 컬럼에 insert
        entity.setFkUserSeq(authenticationFacade.getLoginUserPk());
        return boardMapper.insBoard(entity);
    }
    public List<BoardVo> selBoardList(BoardPageable pageable) {
        // 1페이지 보다 작을 때
        pageable.setPage((pageable.getPage() < 1) ? 1 : pageable.getPage());

        // 마지막 페이지 보다 클 때
        pageable.setTotalCount(boardMapper.selBoardCount(pageable).getTotalCount());
        pageable.setPage((pageable.getPage() > pageable.getMaxPage()) ? pageable.getMaxPage() : pageable.getPage());

        return boardMapper.selBoardList(pageable);
    }

    public BoardPageable getPage(BoardPageable pageable) {
        BoardPageable pageObj = new BoardPageable();
        // 페이징에 필요한 데이터 가공
        pageObj.setPage(pageable.getPage());
        pageObj.setTotalCount(boardMapper.selBoardCount(pageable).getTotalCount());

        return pageObj;
    }
}
