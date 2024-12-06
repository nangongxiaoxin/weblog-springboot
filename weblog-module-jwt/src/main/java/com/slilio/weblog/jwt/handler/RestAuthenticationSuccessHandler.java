package com.slilio.weblog.jwt.handler;

import com.slilio.weblog.common.utils.Response;
import com.slilio.weblog.jwt.model.LoginRspVO;
import com.slilio.weblog.jwt.utils.JwtTokenHelper;
import com.slilio.weblog.jwt.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class RestAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    /**
     * 处理身份验证成功后的逻辑
     * @param request the request which caused the successful authentication
     * @param response the response
     * @param authentication the <tt>Authentication</tt> object which was created during
     * the authentication process.
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //从authentication对象中获取用户的UserDetails实例，这里是获取用户名
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        //通过用户名生成token
        String username = userDetails.getUsername();
        String token = jwtTokenHelper.generateToken(username);

        //返回token
        LoginRspVO loginRspVO = LoginRspVO.builder().token(token).build();

        ResultUtil.ok(response, Response.success(loginRspVO));
    }
}
