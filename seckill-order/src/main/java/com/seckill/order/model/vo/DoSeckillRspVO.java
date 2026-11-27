package com.seckill.order.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: 犬小哈
 * @Date: 2026/5/8 18:31
 * @Version: v1.0.0
 * @Description: 秒杀下单出参
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoSeckillRspVO {

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 订单状态
     */
    private Integer status;
}
