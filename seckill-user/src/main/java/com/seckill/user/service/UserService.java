package com.seckill.user.service;

import com.seckill.common.utils.Response;
import com.seckill.user.model.vo.LoginUserReqVO;
import com.seckill.user.model.vo.LoginUserRspVO;
import com.seckill.user.model.vo.RegisterUserReqVO;
import com.seckill.user.model.vo.SendVerifyCodeReqVO;

/**
 * @Author: 犬小哈
 * @Date: 2026/4/10 18:09
 * @Version: v1.0.0
 * @Description: 用户业务
 **/
public interface UserService {

    /**
     * 用户注册
     * @param registerUserReqVO
     * @return
     */
    Response<?> register(RegisterUserReqVO registerUserReqVO);

    /**
     * 用户登录
     * @param loginUserReqVO
     * @return
     */
    Response<LoginUserRspVO> login(LoginUserReqVO loginUserReqVO);

    /**
     * 发送验证码
     * @param sendVerifyCodeReqVO
     * @return
     */
    Response<?> sendVerifyCode(SendVerifyCodeReqVO sendVerifyCodeReqVO);

    /**
     * 退出登录
     * @return
     */
    Response<?> logout();
}
