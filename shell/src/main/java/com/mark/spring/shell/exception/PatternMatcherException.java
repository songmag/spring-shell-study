package com.mark.spring.shell.exception;

import javax.validation.constraints.Pattern;

public class PatternMatcherException extends Throwable{

    private PatternMatcherException(String message){
        super(message);
    }
    private PatternMatcherException(String message, Throwable e){
        super(message, e);
    }
    public static PatternMatcherException unSupportRegex(){
        return new PatternMatcherException("지원하지 않는 정규식입니다");
    }
}
