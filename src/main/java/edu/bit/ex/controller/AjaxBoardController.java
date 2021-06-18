package edu.bit.ex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.bit.ex.vo.BoardVO;
import lombok.extern.log4j.Log4j;

@Log4j
@Controller
@RequestMapping("/ajax/**")
public class AjaxBoardController {

    @GetMapping("/list")
    public String ajaxList(Model model) {
        log.info("ajaxList()..");

        return "ajax/ajaxList";
    }

 
}
