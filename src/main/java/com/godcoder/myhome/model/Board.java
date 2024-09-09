package com.godcoder.myhome.model;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Board {
	
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY) maria db에서는 사용가능하며 오라클은 불가능 왜냐하면 오라클은 autoincrement가 속성에 없음
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "boardtest_seq")
    @SequenceGenerator(name = "boardtest_seq", sequenceName = "boardtest_seq", allocationSize = 1) //sequece 증가시키기
	private long id;
	@NotNull
	@Size(min=2, max=30, message = "제목은 2자이상 30자 이하입니다.")
	private String title;
	private String content;

}
