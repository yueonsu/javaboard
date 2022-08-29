package com.yueonsu.www.board;

import com.yueonsu.www.board.model.BoardEntity;
import com.yueonsu.www.board.model.BoardPageable;
import com.yueonsu.www.board.model.BoardVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface BoardMapper {

    /**
     * 게시글 리스트
     * @param vo
     * @return
     */
    List<BoardVo> selBoardList(BoardPageable vo);

    /**
     * 글 등록
     * @param entity
     * @return
     */
    int insBoard(BoardEntity entity);

    /**
     * 전체 게시글 수
     * @param pageable
     * @return
     */
    BoardPageable selBoardCount(BoardPageable pageable);

    /**
     * 게시글 디테일
     * @param nBoardSeq
     * @return
     */
    BoardVo selBoardDetail(int nBoardSeq);

    /**
     * 수정 데이터 불러오기
     * @param nBoardSeq
     * @return
     */
    BoardEntity selModData(int nBoardSeq);
    BoardEntity selBoardLastSeq();

    /**
     * 게시글 수정
     * @param entity
     * @return
     */
    int updBoard(BoardEntity entity);

    /**
     * 게시글 삭제
     * @param entity
     * @return
     */
    int delBoard(BoardEntity entity);
}
