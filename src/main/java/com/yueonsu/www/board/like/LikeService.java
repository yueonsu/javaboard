package com.yueonsu.www.board.like;

import com.yueonsu.www.board.like.model.LikeDto;
import com.yueonsu.www.board.like.model.LikeEntity;
import com.yueonsu.www.board.model.BoardResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeMapper likeMapper;

    public BoardResultVo showLikeStatus(LikeEntity entity) {
        BoardResultVo vo = new BoardResultVo();
        /**
         * true = 좋아요 눌러져 있을 때
         * false = 좋아요 안 눌러져 있을 때
         */
        boolean isLike = false;
        try {
            isLike = null != likeMapper.selLike(entity);
        } catch (Exception ignored) {
            vo.setStatus("400");
            vo.setDesc("fail");
        }
        vo.setResult(isLike);

        return vo;
    }

    public BoardResultVo changeLikeStatus(LikeEntity entity) {

        /**
         * true = 좋아요 안 눌러져 있을 때
         * false = 좋아요 눌러져 있을 때
         */
        boolean isLike = null == likeMapper.selLike(entity);

        /**
         * 1 : 눌렀을 때
         * 2 : 해제했을 때
         */
        int status = 0;
        BoardResultVo vo = new BoardResultVo();
        LikeDto dto = new LikeDto();
        dto.setFkBoardSeq(entity.getFkBoardSeq());
        if(isLike) {
            try {
                likeMapper.insLike(entity);
                status = 1;
                dto.setStatus("+");
            } catch (Exception ignored) {
                vo.setStatus("400");
                vo.setDesc("fail");
                vo.setResult(0);
            }
        } else {
            try {
                likeMapper.delLike(entity);
                status = 2;
                dto.setStatus("-");
            } catch (Exception ignored) {
                vo.setStatus("400");
                vo.setDesc("fail");
                vo.setResult(0);
            }
        }
        vo.setResult(status);
        if(0 != Integer.parseInt(vo.getResult().toString())) {
            likeMapper.updBoardLikeCount(dto);
        }

        return vo;
    }
}
