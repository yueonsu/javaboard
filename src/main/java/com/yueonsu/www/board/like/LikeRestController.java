package com.yueonsu.www.board.like;

import com.yueonsu.www.auth.AuthenticationFacade;
import com.yueonsu.www.board.like.model.LikeEntity;
import com.yueonsu.www.board.model.BoardResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ajax/like")
@RequiredArgsConstructor
public class LikeRestController {
    private final LikeService likeService;
    private final AuthenticationFacade authenticationFacade;

    /**
     * 좋아요 여부
     * @param nBoardSeq
     */
    @GetMapping("/show")
    public BoardResultVo showLikeStatus(@RequestParam int nBoardSeq) {
        LikeEntity entity = new LikeEntity();
        entity.setFkBoardSeq(nBoardSeq);
        entity.setFkUserSeq(authenticationFacade.getLoginUserPk());

        return likeService.showLikeStatus(entity);
    }

    /**
     * 좋아요 상태 바꾸기
     * @param data
     */
    @PostMapping("/change")
    public BoardResultVo changeLikeStatus(@RequestBody Map<String, Object> data) {
        int fkBoardSeq = Integer.parseInt(data.get("fkboardSeq").toString());
        int loginUserPk = authenticationFacade.getLoginUserPk();
        LikeEntity entity = new LikeEntity();
        entity.setFkBoardSeq(fkBoardSeq);
        entity.setFkUserSeq(loginUserPk);

        return likeService.changeLikeStatus(entity);
    }
}
