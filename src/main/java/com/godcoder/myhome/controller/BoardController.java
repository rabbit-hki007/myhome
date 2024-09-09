package com.godcoder.myhome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.godcoder.myhome.model.Board;
import com.godcoder.myhome.repository.BoardRepository;
import com.godcoder.myhome.validator.BoardValidator;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private BoardValidator boardValidator;
	
	@GetMapping("/list")
	public String list(Model model, @PageableDefault(size=2) Pageable pageable,
			                        @RequestParam(required = false, defaultValue = "") String searchText) { //@PageableDefault(size=2)처음에 게시판 눌렀을때 보여줄 게시글수를 2개씩보여줌
		
		// List<Board> boards = boardRepository.findAll(); //page 없을때
		//Pageable pageable = PageRequest.of(0, 10);
		//Page<Board> boards = boardRepository.findAll(pageable);
		//=> 검색기능을 추가하면서 위에 줄 대신 아래줄로 수정하였음
		Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);
		//Page<Board> boards = boardRepository.findAll(PageRequest.of(0, 20)); //한줄로 줄인 코드
		//boards.getTotalElements(); //이런 함수 사용가능해짐
		//boards.getTotalPages(); // 이런 함수도 사용가능해짐
		System.out.println("boards : " + boards.toString());
		System.out.println("현재페이지 : " + boards.getPageable().getPageNumber());
		System.out.println("마지막페이지(전체페이지) : " + boards.getTotalPages());
		//보여줄 시작페이지설정-음수페이지 방지용으로 Math.max 0으로 셋팅
		//최소값은 0으로 셋팅
		int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
		//보여줄 마지막 페이지수 설정-음수페이지 방지용으로 Math.max 0으로 셋팅
	    //최소값은 토탈페이지수로 셋팅
		int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 4);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("boards", boards);
		return "board/list";
	}
	
	@GetMapping("/form")
	public String form(Model model, @RequestParam(required = false) Long id) {
		if (id == null ) {
			model.addAttribute("board", new Board());  //spring.io Guide를 따라 한 것임
		} else {
			Board board = boardRepository.findById(id).orElse(null);
			model.addAttribute("board", board);
		}
		return "board/form";
	}
	
	@PostMapping("/form")
	public String form(@Valid Board board, BindingResult bindingResult) { //BindingResult bindingResult를 바인디 해주어야 함
	//public String form(@ModelAttribute Board board) { validation 검사 안했을때
		System.out.println("Board form 저장에 진입하였습니다.");
		
		boardValidator.validate(board, bindingResult);
		
		if (bindingResult.hasErrors()) {
			return "board/form";
		}
		boardRepository.save(board); 
		//board의 id가 비어있으면 insert, id 값을 전달(안비어있음)받았다면 update를 하게됨  
		return "redirect:/board/list";
	}

}
