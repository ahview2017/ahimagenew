package com.deepai.photo.common.dynamicdatasource;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * 读写分离拦截器，拦截所有含有 @DataSource 注解的方法 可取值为：
 * @DataSource("write")
 * @DataSource("read")
 * 
 * @author lizongjie
 *
 */
@Aspect
@Component
public class DataSourceAspect {
	private final static Logger log = Logger.getLogger(DataSourceAspect.class);

	@Pointcut("@annotation(com.deepai.common.dynamicdatasource.ReadSource)")
	public void pointCut() {
	};

	@Before("pointCut()")
	public void before(JoinPoint point) {
		Object target = point.getTarget();
		String method = point.getSignature().getName();
		Class<?> classz = target.getClass();
		Class<?>[] parameterTypes = ((MethodSignature) point.getSignature()).getMethod().getParameterTypes();
		try {
			Method m = classz.getMethod(method, parameterTypes);
			if (m != null && m.isAnnotationPresent(ReadSource.class)) {
				log.debug("读写分离拦截 " + m.getName());
//				ReadSource data = m.getAnnotation(ReadSource.class);
//				HandleDataSource.putDataSource(data.value());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
