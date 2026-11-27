package com.seckill.common.domain.dataobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeckillOrderSendCompensationDO {
    private Long id;

    /** 订单号（补偿记录的唯一业务标识，防止同一笔订单重复写入表中） */
    private String orderNo;

    private Long userId;

    private Long activityId;

    private Long goodsId;

    private Long seckillGoodsId;

    private Integer status;

    private Integer retryCount;

    private String failReason;

    private LocalDateTime nextRetryTime;

    private Integer isDeleted;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String messageBody;

}