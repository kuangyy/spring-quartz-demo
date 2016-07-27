package cn.kykys.controller;

import cn.kykys.model.MyRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.velocity.VelocityView;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kuangye on 2016/7/22.
 *
 * @RestController replaced @Controller and @RequestMapping
 * and set the return to default @ResponseBody
 */
@Controller
@RequestMapping("/demo")
public class DemoController extends BaseController {


    @RequestMapping("/test")
    public Map<String, ?> test(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {

        Map<String, Object> map = new HashMap();
        map.put("test", 1);
        return map;
    }


    @RequestMapping("/valid")
    public String valid(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
                              @Valid MyRequest request, BindingResult valid) {
//        @Valid是使用hibernate validation的时候使用
//        @Validated 是只用spring  Validator 校验机制使用

        ModelAndView mav = new ModelAndView("demo");

        if (valid.hasErrors()) {
            mav.addObject("valid", false);
        } else {
            mav.addObject("valid", true);
        }


        return "demo.ftl";
    }


}
