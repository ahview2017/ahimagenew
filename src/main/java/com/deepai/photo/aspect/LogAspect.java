package com.deepai.photo.aspect;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.deepai.photo.bean.CpLog;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.common.annotation.LogInfo;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.common.util.json.JsonUtil;
import com.deepai.photo.service.admin.LogService;


//@Component
@Aspect
public class LogAspect {
	private Logger log=Logger.getLogger(LogAspect.class);
	@Autowired
	private LogService logService;

	//配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点，使用@LogInfo注解的，记录日志
//	@Pointcut("execution(* com.deepai.photo.controller..*(..))")
	@Pointcut("@annotation(com.deepai.photo.common.annotation.LogInfo))")
	public void aspect(){
	}
	/*@Pointcut("execution(* com.deepai.photo.controller..*(..))")
	public void aspectAll(){
	}*/
	
	/*public LogAspect(){
		System.out.println("aspect init...");
	}*/
	
	/*
	 * 配置后置通知,使用在方法aspect()上注册的切入点
	 * 同时接受JoinPoint切入点对象,可以没有该参数
	 */
	@AfterReturning(pointcut = "aspect()",returning = "result")
	public void afterReturning(JoinPoint joinPoint,ResponseMessage result) throws Throwable {

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();    
        //读取session中的用户    
        CpUser user = (CpUser) SessionUtils.getUser(request);  
        //读取session中的site
        int siteId = (Integer) SessionUtils.getSiteId(request);  
        //请求的IP    
        String ip = request.getRemoteAddr(); 
        if (StringUtils.isNotBlank(ip)) {
			 ip = request.getHeader("X-Real-IP");
			//请求的IP    
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
			final String[] arr = ip.split(",");
		    for (final String str : arr) {
		        if (!"unknown".equalsIgnoreCase(str)) {
		            ip = str;
		            break;
		        }
		    }
		}
      //获取用户请求方法的参数 
        StringBuffer params = new StringBuffer();  
         if (joinPoint.getArgs() !=  null && joinPoint.getArgs().length > 0) {    
             for ( int i = 0; i < joinPoint.getArgs().length; i++) {  
            	 if(joinPoint.getArgs()[i] instanceof MultipartFile){
            		 continue;
            	 }
            	 if(joinPoint.getArgs()[i] instanceof HttpServletResponse){
            		 continue;
            	 }
            	 if(joinPoint.getArgs()[i] instanceof HttpServletRequest){
            		 continue;
            	 }
                try {
					params.append(JsonUtil.getString(joinPoint.getArgs()[i])).append(";");
				} catch (Exception e) {
					continue;
				}
            }    
        }    
         try {    
        	 Map<String,Object> loginfo=getControllerMethodDescription(joinPoint);
            //*========数据库日志=========*//    
            CpLog log=new CpLog();
			log.setOpeContent(loginfo.get("content").toString());
			log.setLogtypeId(logService.getLogTypeIdByCode(loginfo.get("logTypeCode").toString()));
			log.setOpeIp(ip);
			log.setOpeMemo(loginfo.get("memo").toString());
			log.setOpeParam(params.toString());
			log.setOpeTime(new Date());
			log.setOpeType((Byte)loginfo.get("opeType"));
			if(user!=null){
				log.setOpeUser(user.getUserName());
				log.setOpeUserId(user.getId());
			}
			log.setOpeResult(result.getMsg());
			if(result.getOther()!=null&&result.getOther().toString().length()<100){
				log.setOpeIds(result.getOther().toString());
			}			
			log.setSiteId(siteId);
            //保存数据库    
            logService.addLog(log);    
        }  catch (Exception e) {    
            //记录本地异常日志    
        	e.printStackTrace();
        	log.error("Aop记录日志报错:"+ e.getMessage());    
        }    
	}

	  /**  
     * 获取注解中对方法的描述信息 用于Controller层注解  
     *  
     * @param joinPoint 切点  
     * @return 方法描述  
     * @throws Exception  
     */    
     public  static Map<String,Object> getControllerMethodDescription(JoinPoint joinPoint)  throws Exception {    
    	 Map<String,Object> map=new HashMap<String, Object>();
        String targetName = joinPoint.getTarget().getClass().getName();    
        String methodName = joinPoint.getSignature().getName();   
        Object[] arguments = joinPoint.getArgs();    
        Class targetClass = Class.forName(targetName);    
        Method[] methods = targetClass.getMethods();    
         for (Method method : methods) {    
             if (method.getName().equals(methodName)) {    
                Class[] clazzs = method.getParameterTypes();    
                 if (clazzs.length == arguments.length) {    
                    map.put("content", method.getAnnotation(LogInfo.class).content());
                    map.put("opeType", method.getAnnotation(LogInfo.class).opeType());
                    map.put("memo", method.getAnnotation(LogInfo.class).memo());
                    map.put("logTypeCode", method.getAnnotation(LogInfo.class).logTypeCode());
                     break;    
                }    
            }    
        }    
         return map;    
    }    
     
    /* public static void main(String[] args) {
		CpUser u=new CpUser();
		u.setId(1);
		u.setUserName("gyh");
		u.setMemo("ss");
		System.out.println(JSONUtils.objectToJSONObjectString(u));
	}*/
}
