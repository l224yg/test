package com.li.seckill.domain;

import com.li.seckill.validator.IsMobile;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Desciption
 * @Auther Liyg
 * @Date Created in 2018/10/29 22:44
 */
@Getter
@Setter
@ToString
public class User {

    private Long id;
    private String nickname;
    private String password;
    private String salt;
    private String head;
    private Date registerDate;
    private Date lastLloginDate;
    private Integer loginCount;

}
