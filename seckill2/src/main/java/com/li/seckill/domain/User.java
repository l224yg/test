package com.li.seckill.domain;

import com.li.seckill.validator.IsMobile;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @Desciption
 * @Auther Liyg
 * @Date Created in 2018/10/29 22:44
 */
public class User {

    private Long id;
    private String nickname;
    private String password;
    private String salt;
    private String head;
    private String registerDate;
    private String lastLloginDate;
    private String loginCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getLastLloginDate() {
        return lastLloginDate;
    }

    public void setLastLloginDate(String lastLloginDate) {
        this.lastLloginDate = lastLloginDate;
    }

    public String getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(String loginCount) {
        this.loginCount = loginCount;
    }
}
