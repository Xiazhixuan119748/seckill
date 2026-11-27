package com.seckill.order.service;

import com.seckill.order.enums.SeckillStockCompensationResultEnum;
import com.seckill.order.enums.SeckillStockDeductResultEnum;
import com.seckill.order.model.dto.SeckillOrderMqDTO;

/**
 * @Author: 犬小哈
 * @Date: 2026/7/20 20:21
 * @Version: v1.0.0
 * @Description: 秒杀库存服务
 **/
public interface SeckillStockService {

    /**
     * Redis Lua 原子预扣库存
     */
    SeckillStockDeductResultEnum preDeductStock(SeckillOrderMqDTO seckillOrderMqDTO,
                                                Long userOrderTtlSeconds,
                                                Long activityBeginTimeMillis,
                                                Long activityEndTimeMillis);


    /**
     * Redis Lua 原子回补秒杀库存，并删除用户购买标记
     */
    SeckillStockCompensationResultEnum compensatePreDeductStock(Long activityId,
                                                                Long goodsId,
                                                                Long userId,
                                                                String orderNo);
}
