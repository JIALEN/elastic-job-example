package com.alen.service;

import com.alen.dao.MessageDao;
import com.alen.entity.MessageDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author alen
 * @create 2018-01-04 14:19
 **/
@Service
public class MessageService {

    @Autowired
    private MessageDao messageDao;

  public Boolean updateStatus(MessageDO record){
      record.setStatus(true);
      return  messageDao.updateByPrimaryKey(record);
  }

    public List<MessageDO> getMessage(String parameter){
        return  messageDao.getMessage(parameter);
    }

    public Boolean insert(MessageDO record){
        return  messageDao.insert(record);
    }
}
