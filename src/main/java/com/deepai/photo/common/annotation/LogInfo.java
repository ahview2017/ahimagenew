package com.deepai.photo.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Documented //注解是否将包含在JavaDoc中
@Inherited //是否允许子类集成该注解
@Target(ElementType.METHOD) //注解用于什么地方
@Retention(RetentionPolicy.RUNTIME) //什么时候使用该注解
public @interface LogInfo {
	public String content() default "操作内容"; //操作内容
    public byte opeType() default 2;//操作类型
    public String memo() default "";//备注
    public String logTypeCode() ;//日志类型
    
}
