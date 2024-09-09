package com.godcoder.myhome.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.godcoder.myhome.model.Board;

public interface BoardRepository extends JpaRepository<Board, Long>  {
	List<Board> findByTitle (String title);
	List<Board> findByTitleOrContent (String title, String content);
	List<Board> findByTitleAndContent (String title, String content);
	
	Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
	//Page<Board> findAll(Pageable pageable);
}
