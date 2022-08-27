package org.amos.satoken.properties;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
//@Component
public class JwtTokenProperties {
    /**
     * 请求头
     */
    @Value("${jwt.header:amos-auth}")
    private String header;
    /**
     * 密钥
     */
    @Value("${jwt.secret:ZmQ0ZGI5NjQ0MDQwY2I4MjMxY2Y3ZmI3MjdhN2ZmMjNhODViOTg1ZGE0NTBjMGM4NDA5NzYxMjdjOWMwYWRmZTBlZjlhNGY3ZTg4Y2U3YTE1ODVkZDU5Y2Y3OGYwZWE1NzUzNWQ2YjFjZDc0NGMxZWU2MmQ3MjY1NzJmNTE0MzI}")
    private String secret;
    /**
     * token开始字符串
     */
    @Value("${jwt.token-start-with:Bearer}")
    private String tokenStartWith;
    /**
     * token 过期时间
     */
    @Value("${jwt.token-expire-time:14400000}")
    private Long tokenExpireTime;

    public String getTokenStartWith() {
        return tokenStartWith + " ";
    }
}
