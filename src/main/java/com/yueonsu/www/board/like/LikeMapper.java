package com.yueonsu.www.board.like;

import com.yueonsu.www.board.like.model.LikeDto;
import com.yueonsu.www.board.like.model.LikeEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikeMapper {
    /**
     * 좋아요 등록
     * @param entity
     * @return
     */
    int insLike(LikeEntity entity);

    /**
     * 좋아요 여부
     * @param entity
     * @return
     */
    LikeEntity selLike(LikeEntity entity);

    /**
     * 좋아요 갯수 업데이트
     * @param dto
     * @return
     */
    int updBoardLikeCount(LikeDto dto);

    /**
     * 좋아요 삭제
     * @param entity
     * @return
     */
    int delLike(LikeEntity entity);
}
