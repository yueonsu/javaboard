package com.yueonsu.www.board.hit;

import com.yueonsu.www.board.hit.model.HitEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HitService {

    private final HitMapper hitMapper;

    public void hitCountUp(HitEntity entity) {
        int result = 0;
        try {
            result = hitMapper.insHit(entity);
        } catch (Exception ignored) {

        }
        if(0 != result) {
            hitMapper.updBoardHit(entity);
        }
    }
}
