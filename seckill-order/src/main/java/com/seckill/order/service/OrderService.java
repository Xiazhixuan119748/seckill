package com.seckill.order.service;

import com.seckill.common.utils.Response;
import com.seckill.order.model.dto.SeckillOrderMqDTO;
import com.seckill.order.model.vo.*;

/**
 * @Author: 犬小哈
 * @Date: 2026/5/8 18:33
 * @Version: v1.0.0
 * @Description: 订单模块业务
 **/
public interface OrderService {

    /**
     * 秒杀下单
     *
     * @param reqVO
     * @return
     */
    Response<DoSeckillRspVO> doSeckill(DoSeckillReqVO reqVO);

    /**
     * 异步消费秒杀下单消息：扣减库存 + 创建订单
     *
     * @param message
     */
    void createSeckillOrder(SeckillOrderMqDTO message);

    /**
     * 查询秒杀订单处理结果
     *
     * @param reqVO
     * @return
     */
    Response<FindSeckillOrderResultRspVO> findSeckillOrderResult(FindSeckillOrderResultReqVO reqVO);

    /**
     * 获取秒杀令牌
     *
     * @param reqVO
     * @return
     */
    Response<GetSeckillTokenRspVO> getSeckillToken(GetSeckillTokenReqVO reqVO);
}
