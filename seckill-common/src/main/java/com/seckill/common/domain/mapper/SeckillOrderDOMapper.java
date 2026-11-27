package com.seckill.common.domain.mapper;

import com.seckill.common.domain.dataobject.SeckillOrderDO;
import org.apache.ibatis.annotations.Param;

public interface SeckillOrderDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SeckillOrderDO record);

    int insertSelective(SeckillOrderDO record);

    SeckillOrderDO selectByPrimaryKey(Long id);

    SeckillOrderDO selectByOrderNoAndUserId(@Param("orderNo") String orderNo,
                                            @Param("userId") Long userId);

    int updateByPrimaryKeySelective(SeckillOrderDO record);

    int updateByPrimaryKey(SeckillOrderDO record);

}