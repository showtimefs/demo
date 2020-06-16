package com.whyrkj.demo.config.interceptor;

import com.alibaba.fastjson.JSON;
import com.whyrkj.demo.config.model.ResultData;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ParamInterceptor implements HandlerInterceptor {

    private Logger logger = Logger.getLogger(ParamInterceptor.class);

    /**
     * 1.分别做拦截 小程序不拦截
     * 2.酒店方 hotel  主办方 organizer
     *
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String requestURL = request.getServletPath();

        if (requestURL.contains("/om/")) {
            //后台接口做拦截
            PrintWriter writer = null;
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=utf-8");
            //session获取用户信息
//            Admin admin = (Admin) request.getSession().getAttribute("adminUser");
//            if (admin != null) {
//                logger.info("验证成功,允许登录");
//                return true;
//            }
            try {
                ResultData resultData = new ResultData();
                resultData.setStatus(403);
                String error = "身份验证失败,请重新登录";
                resultData.setMessage(error);
                writer = response.getWriter();
                writer.append(JSON.toJSONString(resultData));
                return false;
            } catch (IOException e) {
                logger.error("返回出错", e);
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {


    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
