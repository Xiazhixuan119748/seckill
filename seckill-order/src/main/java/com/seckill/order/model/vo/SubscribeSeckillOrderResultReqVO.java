package com.seckill.order.model.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: 犬小哈
 * @Date: 2026/7/15 8:49
 * @Version: v1.0.0
 * @Description: 订阅秒杀订单结果入参
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscribeSeckillOrderResultReqVO {

    @NotBlank(message = "订单号不能为空")
    private String orderNo;

}
