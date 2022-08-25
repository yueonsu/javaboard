package com.yueonsu.www.board.like;

import com.yueonsu.www.board.like.model.LikeDto;
import com.yueonsu.www.board.like.model.LikeEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikeMapper {
    int insLike(LikeEntity entity);
    LikeEntity selLike(LikeEntity entity);
    int updBoardLikeCount(LikeDto dto);
    int delLike(LikeEntity entity);
}
