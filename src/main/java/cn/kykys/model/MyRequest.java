package cn.kykys.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by kuangye on 2016/7/27.
 */
public class MyRequest {


    @NotBlank
    private String name;

    @NotNull
    @Min(18)
    @Max(60)
    private Integer age;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
