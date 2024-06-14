package org.example.common.config.web;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 拦截器获取指定请求头
 * </p>
 *
 * @author author
 * @version 1.0
 * @since 2024/6/14
 */
@Component
public class HeaderInterceptor implements HandlerInterceptor {

    /**
     * 请求头线程变量
     */
    public static final ThreadLocal<String> ThreadLocalHolder = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        var headerValue = request.getHeader("Test-Mode");
        ThreadLocalHolder.set(headerValue);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        ThreadLocalHolder.remove();
    }

}
