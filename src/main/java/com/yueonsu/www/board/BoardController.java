package com.yueonsu.www.board;

import com.yueonsu.www.auth.AuthenticationFacade;
import com.yueonsu.www.board.model.BoardEntity;
import com.yueonsu.www.board.model.BoardPageable;
import com.yueonsu.www.board.model.BoardVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired private BoardService boardService;
    @Autowired private AuthenticationFacade authenticationFacade;

    @GetMapping("/list")
    public String main(Model model, BoardPageable pageable) {
        // 페이지 기본 값
        pageable.setPage((pageable.getPage() == 0 ? 1 : pageable.getPage()));
        // 페이징 데이터
        model.addAttribute("pageData", boardService.getPage(pageable));
        // 게시판 리스트
        model.addAttribute("boardList", boardService.selBoardList(pageable));
        return "board/list";
    }

    @GetMapping("/write")
    public String write(Model model) {
        if(authenticationFacade.getLoginUser() == null) { return "redirect:/user/login"; }

        return "board/write";
    }

    @PostMapping("/write")
    public String writeProc(Model model, BoardEntity entity) {
        int result = boardService.insBoard(entity);
        int nBoardSeq = entity.getNBoardSeq();

        String path = (result == 1) ? "/board/detail?nBoardSeq="+nBoardSeq : "/board/list";

        return "redirect:"+path;
    }
}
