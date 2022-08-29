package com.yueonsu.www.board.hit;

import com.yueonsu.www.board.hit.model.HitEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HitMapper {
    /**
     * 조회수 등록
     * @param entity
     * @return
     */
    int insHit(HitEntity entity);

    /**
     * 조회수 업데이트
     * @param entity
     * @return
     */
    int updBoardHit(HitEntity entity);
}
