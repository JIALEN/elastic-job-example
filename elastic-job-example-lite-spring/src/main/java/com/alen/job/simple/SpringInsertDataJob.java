

package com.alen.job.simple;

import com.alen.entity.MessageDO;
import com.alen.entity.fixture.repository.FooRepository;
import com.alen.service.MessageService;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * 插入测试数据库
 */
public class SpringInsertDataJob implements SimpleJob {
    
    @Resource
    private FooRepository fooRepository;

    @Autowired
    private  MessageService  messageService;

    @Override
    public void execute(final ShardingContext shardingContext) {
        System.out.println("SpringSimpleJob 简单任务-------------任务名："+shardingContext.getJobName()+"\n"
                +"，---ShardingParameter:"+shardingContext.getShardingParameter()+"\n"
                +",----TaskId:"+shardingContext.getTaskId()+"\n"
                +",----JobParameter:"+shardingContext.getJobParameter()+"\n"
                +",----tShardingItem:"+shardingContext.getShardingItem()+"\n"
                +",----ShardingTotalCount:"+shardingContext.getShardingTotalCount()+"\n"
        );
        int i=0;
        while (i<100) {
            MessageDO messageDO = new MessageDO();
            messageDO.setStatus(false);
            messageDO.setRemark(shardingContext.getJobName());
            messageDO.setMessage("测试数据");
            messageDO.setParameter(shardingContext.getShardingParameter());
            messageService.insert(messageDO);
            i++;
        }
    }
}
