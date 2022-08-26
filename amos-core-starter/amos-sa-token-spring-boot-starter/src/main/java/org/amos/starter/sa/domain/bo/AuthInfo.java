package org.amos.starter.sa.domain.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

@Data
public class AuthInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @JSONField(serialize = false)
    private Long id;

    /**
     * 用户昵称
     */
    private String userName;
    /**
     * 手机号码
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 状态：0未激活、1启用、2锁定
     */
    @JSONField(serialize = false)
    private Integer status;
    /**
     * 令牌
     */
    private String token;
    /**
     * token有效时间
     */
    private Long expire;
    /**
     * 身份角色
     */
    private Object principal;
    /**
     * 密码
     */
    @JSONField(serialize = false)
    private String password;
}
