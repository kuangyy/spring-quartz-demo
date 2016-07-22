package cn.kykys.filter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;

/**
 * Created by kuangye on 2016/7/22.
 */
public class FilterA implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {



    return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) throws Exception {

    }

    protected void output(HttpServletRequest request, HttpServletResponse response, Object object){
        OutputStream ps = null;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(DateFormat.getDateTimeInstance());
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            ps = response.getOutputStream();
            String type = request.getParameter("type");
            // 如果不需要简化输出,则输出所有
            if(StringUtils.isEmpty(type) || !type.toLowerCase().equals("phone")) {
                ps.write(objectMapper.writeValueAsString(object).getBytes("UTF-8"));
            }
            else {
                //否则只输出带有PhoneFilter注解的,供手机端使用
                objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
//                ps.write(objectMapper.writerWithView(PhoneShow.class).writeValueAsString(object).getBytes("UTF-8"));
            }
        } catch (IOException e) {
//            throw new BusinessException(e.getMessage(), 400);
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
