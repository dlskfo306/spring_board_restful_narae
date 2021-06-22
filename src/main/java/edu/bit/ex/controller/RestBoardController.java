package edu.bit.ex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import edu.bit.ex.service.BoardService;
import edu.bit.ex.vo.BoardVO;
import lombok.extern.log4j.Log4j;

//spring v4.0에서 부터
//@RestController라는 어노테이션을 추가해서 
//해당 Controller의 모든 메서드의 리턴타입을 기존과 다르게 처리한다는 것을 명시

//하나의 URI가 하나의 고유한 리소스를 대표하도록 설계된 개념

//http://localhost/spring02/list?bno=1 ==> url+파라미터
//http://localhost/spring02/list/1 ==> url
//RestController은 스프링 4.0부터 지원
//@Controller, @RestController 차이점은 메서드가 종료되면 화면전환의 유무

@Log4j
@RestController
@RequestMapping("/restful/*")
public class RestBoardController {

    @Autowired
    private BoardService boardService;

    // 1. list(처음 진입 화면이므로 화면이 깜박여도 상관없으므로 @Controller방식으로 접근 - veiw(화면)를 리턴
    @GetMapping("/board")
    public ModelAndView list(ModelAndView mav) {
        mav.setViewName("rest/rest_list"); // restful/rest_list.jsp
        mav.addObject("list", boardService.getList());

        return mav;
    }

    /*
     * //위에꺼랑 이거랑 똑같은애임. //근데 위에 @RestController가 있으면 이게 안먹힘. //문법을 완전히 무시하고 단순히
     * string으로 리턴해버림
     * 
     * @GetMapping("/board") public String list(Model model) {
     * 
     * model.addAttribute("list", boardService.getList());
     * 
     * return "restful/rest_list"; }
     */

    // 조회
    @GetMapping("/board/{bid}")
    public ModelAndView rest_content_view(BoardVO boardVO, ModelAndView mav) {

        log.info("rest_content_view");

        mav.setViewName("rest/rest_content_view");
        mav.addObject("content_view", boardService.read(boardVO.getBid()));

        return mav;
    }

    //restful 관련된 스프링 API
    //@RestController
    //@ResponseBody
    //@RequestBody
    //ResponseEntity<String>
    //@PathVariable
    
    // 수정 /board/10
    @PutMapping("/board/{bid}")
    public ResponseEntity<String> restUpdate(@RequestBody BoardVO boardVO, ModelAndView mav) {

        log.info("restUpdate()..");
        log.info("boardVO.." + boardVO);

        ResponseEntity<String> entity = null;
        try {
            boardService.modify(boardVO);
            entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return entity;
    }
    
    
    // 삭제
    @DeleteMapping("/board/{bid}")
    public ResponseEntity<String> restDelete(@PathVariable("bid") int bid) {

        log.info("restDelete()..");
        log.info("bid.." + bid);

        ResponseEntity<String> entity = null;
        try {
            
            int rn = boardService.remove(bid);
            
            
            log.info("delete result:"+rn);
            //삭제가 성공하면 성공 상태메세지 지정 
            entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            //삭제가 실패하면 실패 상태메세지 지정
            entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return entity;
    }
 
}
