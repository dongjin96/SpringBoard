package board.Controller;

import board.Domain.Dto.BoardDto;
import board.Service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class BoardController {

    @Autowired
    HttpServletRequest request;
//게시물 쓰기 페이지로 이동
@GetMapping("/board/boardwrite")
    public String boardwrite(){return "board/boardwrite";}
// 게시물 쓰기 처리
    @PostMapping("/board/boardwritecontroller")
    public String boardwritecontroller(){


        BoardDto boardDto = BoardDto.builder()
                .b_title( request.getParameter("b_title"))
                .b_contents(request.getParameter("b_contents"))
                .b_write(request.getParameter("b_write"))
                .b_password(request.getParameter("b_password"))
                .build();
        boardService.boardwrite(boardDto);
        return "redirect:/board/boardlsit";
    }

    @Autowired
    BoardService boardService;


    @GetMapping("/board/boardlsit")
    public String boardlist(Model model){
        ArrayList<BoardDto> boardDtos = boardService.boardlist();
        model.addAttribute("BoardDtos",boardDtos);
        return "/board/boardlsit"; //타임리프를 통한  html반환
    }
    //게시물 보기 페이지 이동
    @GetMapping("/board/boardview/{b_num}")
    public String boardview(@PathVariable("b_num") int b_num, Model model){
        BoardDto boardDto = boardService.getboard(b_num);
        model.addAttribute("boardDto",boardDto);
        return "/board/boardview";
    }
    //게시물 삭제 처리
    @GetMapping("/board/boarddelete")
            @ResponseBody
            public int boarddelete(@RequestParam("b_num") int b_num){
                System.out.println(b_num);
                boolean result = boardService.delete(b_num);
                if(result){
            return 1;
        }else{
            return 2;
        }
    }
    @GetMapping("/board/boardupdate/{b_num}")
    //수정페이지 이동
    public String boardupdate(@PathVariable("b_num")int b_num,Model model){
        BoardDto boardDto = boardService.getboard(b_num);
        model.addAttribute("boardDto",boardDto);
        return "/board/boardupdate";
    }
    @PostMapping("/board/boardcontroller")
    public String boardcontroller(BoardDto boardDto){
        boolean result = boardService.update(boardDto);
        return "redirect:/board/boardlsit";
    }
    @PostMapping("/board/boardpassword")
    public String password(BoardDto boardDto, Model model) {
        boolean result = boardService.passwordcheck(boardDto.getB_num(),boardDto.getB_password());
        BoardDto boardDto1 = boardService.getboard(boardDto.getB_num());
        if (result) {
                model.addAttribute("boardDto",boardDto1);
                model.addAttribute("passwordmsg","1");
                return "board/boardview";
        } else {
            model.addAttribute("boardDto",boardDto1);
            model.addAttribute("passwordmsg","안됩니다");
            return "board/boardview/"+boardDto.getB_num();
        }



    }
}
