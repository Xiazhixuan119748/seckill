package com.seckill.order.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.seckill.common.aspect.ApiOperationLog;
import com.seckill.common.utils.Response;
import com.seckill.order.aspect.SeckillRateLimit;
import com.seckill.order.model.vo.*;
import com.seckill.order.service.OrderService;
import com.seckill.order.service.SeckillOrderResultNotifyService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @Author: 犬小哈
 * @Date: 2026/5/8 21:40
 * @Version: v1.0.0
 * @Description: 订单模块
 **/
@RestController
@RequestMapping("/seckill/order")
@Slf4j
public class OrderController {

    @Resource
    private OrderService orderService;

    @Resource
    private SeckillOrderResultNotifyService seckillOrderResultNotifyService;

    /**
     * 获取秒杀令牌
     *
     * @param reqVO
     * @return
     */
    @PostMapping("/token")
    @ApiOperationLog(description = "获取秒杀令牌")
    public Response<GetSeckillTokenRspVO> getSeckillToken(@RequestBody @Validated GetSeckillTokenReqVO reqVO) {
        return orderService.getSeckillToken(reqVO);
    }

    /**
     * 秒杀下单
     *
     * @param reqVO
     * @return
     */
    @PostMapping
    @ApiOperationLog(description = "秒杀下单")
    @SeckillRateLimit(userMaxCount = 5, userWindowSeconds = 10, ipMaxCount = 50, ipWindowSeconds = 10)
    public Response<DoSeckillRspVO> doSeckill(@RequestBody @Validated DoSeckillReqVO reqVO) {
        return orderService.doSeckill(reqVO);
    }

    /**
     * 查询秒杀订单处理结果
     *
     * @param reqVO
     * @return
     */
    @PostMapping("/result")
    @ApiOperationLog(description = "查询秒杀订单处理结果")
    public Response<FindSeckillOrderResultRspVO> findSeckillOrderResult(@RequestBody @Validated FindSeckillOrderResultReqVO reqVO) {
        return orderService.findSeckillOrderResult(reqVO);
    }

    /**
     * 订阅秒杀订单处理结果
     *
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/result/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ApiOperationLog(description = "订阅秒杀订单处理结果")
    public SseEmitter subscribeSeckillOrderResult(@RequestBody @Validated SubscribeSeckillOrderResultReqVO reqVO) {
        // 获取当前登录用户 ID
        long userId = StpUtil.getLoginIdAsLong();
        return seckillOrderResultNotifyService.subscribe(userId, reqVO.getOrderNo());
    }
}
