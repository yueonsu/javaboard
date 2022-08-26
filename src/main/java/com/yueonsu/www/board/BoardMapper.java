package com.yueonsu.www.board;

import com.yueonsu.www.board.model.BoardEntity;
import com.yueonsu.www.board.model.BoardPageable;
import com.yueonsu.www.board.model.BoardVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    // 게시글 리스트
    List<BoardVo> selBoardList(BoardPageable vo);
    // 글 등록
    int insBoard(BoardEntity entity);
    // 전체 게시글 수
    BoardPageable selBoardCount(BoardPageable pageable);
    // 게시글 디테일
    BoardVo selBoardDetail(int nBoardSeq);
    // 수정 데이터 불러오기
    BoardEntity selModData(int nBoardSeq);
    // 게시글 수정
    int updBoard(BoardEntity entity);
    // 게시글 삭제
    int delBoard(BoardEntity entity);
}
