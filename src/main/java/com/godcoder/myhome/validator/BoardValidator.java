package com.godcoder.myhome.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

import com.godcoder.myhome.model.Board;

@Component
public class BoardValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
			
		return Board.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Board b = (Board) target;
        if(StringUtils.isEmpty(b.getContent())) {
            errors.rejectValue("content", "key", "내용을 입력하세요");
            //content 필드를 키값이 있으면(하지만 이프로그램에서 키값은 없다) 키값이 없으면 3번째 인자인 "내용을 입력하세요"가 뜬다
        }
		
	}

}
