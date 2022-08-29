package com.yueonsu.www.board;

import com.yueonsu.www.auth.AuthenticationFacade;
import com.yueonsu.www.board.hit.HitService;
import com.yueonsu.www.board.hit.model.HitEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final HitService hitService;
    private final AuthenticationFacade authenticationFacade;

    /**
     * 게시판 리스트
     * @return
     */
    @GetMapping("/list")
    public String main() {

        return "board/list";
    }

    /**
     * 글등록 / 글수정
     * @param model
     * @param nBoardSeq
     * @return
     */
    @GetMapping("/write")
    public String write(Model model, @RequestParam(required = false, defaultValue = "0") int nBoardSeq) {
        // 수정 페이지
        if (0 < nBoardSeq) {
            model.addAttribute("modData", boardService.selBoardDetail(nBoardSeq));
        }

        return "board/write";
    }

    /**
     * 디테일 게시판
     * @return
     */
    @GetMapping("/detail")
    public String detail(@RequestParam int nBoardSeq) {
        HitEntity entity = new HitEntity();
        entity.setFkBoardSeq(nBoardSeq);
        entity.setFkUserSeq(authenticationFacade.getLoginUserPk());

        if (0 != nBoardSeq) {
            hitService.hitCountUp(entity);
        }
        return "board/detail";
    }
}
