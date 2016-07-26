package cn.kykys.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by kuangye on 2016/7/22.
 */
public class BaseController {

    private static Logger logger = Logger.getLogger(BaseController.class);



    @ExceptionHandler
    private void exceptionHandler(HttpServletRequest request, HttpServletResponse response,Exception ex){



        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/xml; charset=utf-8");


        if(ex instanceof IllegalArgumentException){
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }else if (ex instanceof BusinessException) {
            response.setStatus(HttpStatus.BAD_REQUEST);
        }else {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        output(request,response,null);
        logger.error(ex.getMessage(), ex);

    }


    protected void output(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Object object,DateFormat dateFormat){
        OutputStream ps = null;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(dateFormat);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            ps = httpResponse.getOutputStream();
            String type = httpRequest.getParameter("debug");
            // 如果不需要简化输出,则输出所有
            if(StringUtils.isEmpty(type) || !type.toLowerCase().equals("debug")) {
                ps.write(objectMapper.writeValueAsString(object).getBytes("UTF-8"));
            }
            else {
                //否则只输出带有PhoneFilter注解的,供手机端使用
                objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
                ps.write(objectMapper.writerWithView(Debug.class).writeValueAsString(object).getBytes("UTF-8"));
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage(), HttpStatus.BAD_GATEWAY);
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

    protected void output(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Object object){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.output(httpRequest, httpResponse, object, dateFormat);
    }

}
