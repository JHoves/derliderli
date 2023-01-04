package com.jhoves.derliderli.domain.exception;

/**
 * @author JHoves
 * @create 2023-01-04 14:04
 */

public class ConditionException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private String code;

    public ConditionException(String code,String name){
        super(name);
        this.code = code;
    }

    public ConditionException(String name){
        super(name);
        code = "500";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
