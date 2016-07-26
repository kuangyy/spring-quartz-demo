package cn.kykys.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kuangye on 2016/7/22.
 *
 * @RestController replaced @Controller and @RequestMapping
 * and set the return to default @ResponseBody
 */
@RestController("/demo")
public class DemoController extends BaseController {


    @RequestMapping("test")
    public Map<String, ?> test(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> map = new HashMap();
        map.put("test", 1);
        return map;
    }


}
