package edu.bit.ex.service;

import java.util.List; 


import edu.bit.ex.vo.BoardVO;


public interface BoardService {
    List<BoardVO> getList();
    BoardVO get(int bid);
}
