package com.alen.dao;

import com.alen.entity.MessageDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageDao {
    Boolean deleteByPrimaryKey(Integer id);

    Boolean insert(MessageDO record);

    MessageDO selectByPrimaryKey(Integer id);

    List<MessageDO> getMessage(@Param("parameter") String parameter);

    Boolean updateByPrimaryKey(MessageDO record);
}