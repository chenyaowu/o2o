package com.chen.o2o.entity;

import java.util.Date;
//微信账号
public class WechatAuth {
    //Id
    private Long wachatAuthId;
    //微信账号和公众号绑定Id
    private String openId;
    //创建时间
    private Date createTime;
    //用户信息
    private PersonInfo personInfo;

    public Long getWachatAuthId() {
        return wachatAuthId;
    }

    public void setWachatAuthId(Long wachatAuthId) {
        this.wachatAuthId = wachatAuthId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(PersonInfo personInfo) {
        this.personInfo = personInfo;
    }
}
