package com.yueonsu.www.board;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yueonsu.www.auth.AuthenticationFacade;
import com.yueonsu.www.board.model.BoardEntity;
import com.yueonsu.www.board.model.BoardPageable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final AuthenticationFacade authenticationFacade;

    @GetMapping("/list")
    public String main() {

        return "board/list";
    }

    @GetMapping("/write")
    public String write(Model model, BoardEntity entity) {
        int loginUserPk = authenticationFacade.getLoginUserPk();
        // 로그인 안 되어있을 때
        if (1 == loginUserPk) {
            return "redirect:/user/login";
        }
        // 수정 페이지
        if (0 < entity.getNBoardSeq()) {
            model.addAttribute("modData", boardService.selBoardDetail(entity.getNBoardSeq()));
        }

        return "board/write";
    }

    /**
     * 글쓰기 폼 조회
     * @param entity dd
     */
    @PostMapping("/write")
    public String writeProc(BoardEntity entity) {
        ObjectMapper mapper = new ObjectMapper();

        int result = 0;
        int nBoardSeq = 0;
        if (0 == entity.getNBoardSeq()) {
            result = boardService.insBoard(entity);
            nBoardSeq = entity.getNBoardSeq();
        } else {

        }

        String path = (1 == result) ? "/board/detail?nBoardSeq="+  nBoardSeq : "/board/list";
        return "redirect:"+path;
    }

    @GetMapping("/detail")
    public String detail() {
        return "board/detail";
    }
}
