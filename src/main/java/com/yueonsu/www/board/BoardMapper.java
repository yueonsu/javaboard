package com.yueonsu.www.board;

import com.yueonsu.www.board.model.BoardEntity;
import com.yueonsu.www.board.model.BoardPageable;
import com.yueonsu.www.board.model.BoardVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<BoardVo> selBoardList(BoardPageable vo);
    int insBoard(BoardEntity entity);

    BoardPageable selBoardCount(BoardPageable pageable);
}
