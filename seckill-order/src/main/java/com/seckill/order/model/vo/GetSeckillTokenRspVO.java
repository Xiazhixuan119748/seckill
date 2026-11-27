package com.seckill.order.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: 犬小哈
 * @Date: 2026/8/14 16:12
 * @Version: v1.0.0
 * @Description: 获取秒杀令牌
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetSeckillTokenRspVO {

    /**
     * 秒杀令牌
     */
    private String token;

    /**
     * 过期时间（单位：秒）
     */
    private Long expireSeconds;

}
