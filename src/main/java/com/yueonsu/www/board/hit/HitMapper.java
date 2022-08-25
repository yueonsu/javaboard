package com.yueonsu.www.board.hit;

import com.yueonsu.www.board.hit.model.HitEntity;
import com.yueonsu.www.board.model.BoardEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HitMapper {
    int insHit(HitEntity entity);
    int updBoardHit(HitEntity entity);
}
