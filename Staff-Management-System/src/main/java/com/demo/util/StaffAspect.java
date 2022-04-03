package com.demo.util;

import com.demo.pojo.StaffVO;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.mybatis.logging.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Date;


/**
 * @author lihongjie
 * @date 2022/3/31
 */
@Aspect
@Component
public class StaffAspect implements Ordered {
    /**
     * 日志输出对象
     */
    private static final Logger log = Logger.getLogger(StaffAspect.class);
    
    @Pointcut(value = "execution(public * com.demo.service.impl.*.*(..))")
    public void logPointCut(){}

    @Around("logPointCut() && args(paramVO, ..)")
    public Object aroundLog(ProceedingJoinPoint joinPoint, StaffVO paramVO){
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();
        log.info(MessageFormat.format("StaffManage_aroundLog 进入{0}方法，获取到的参数为：{1}", methodName, paramVO));
        long start = System.currentTimeMillis();
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return Result.fail(methodName + "执行失败，请检查。", 500);
            /*throwable.printStackTrace();
            throw new RuntimeException(throwable.getMessage());*/
        } finally {
            long wasteTime = System.currentTimeMillis() - start;
            log.info(MessageFormat.format("StaffManage_aroundLog 进入{0}方法，执行耗时为：{1}，执行结果为：{2}", methodName, wasteTime, result));
        }
        return result;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
