package com.alen.job.simple;

import com.alen.entity.MessageDO;
import com.alen.service.MessageService;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * job-ref 	String 	否 		作业关联的beanId，该配置优先级大于class属性配置
 *
 * @author alen
 * @create 2018-01-03 16:01
 **/
public class MySimpleRefElasticJob implements SimpleJob {

    @Autowired
    private MessageService messageService;
    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println("MySimpleRefElasticJob job-ref 简单任务-------------任务名："+shardingContext.getJobName()+"\n"
                +"，---ShardingParameter:"+shardingContext.getShardingParameter()+"\n"
                +",----TaskId:"+shardingContext.getTaskId()+"\n"
                +",----JobParameter:"+shardingContext.getJobParameter()+"\n"
                +",----tShardingItem:"+shardingContext.getShardingItem()+"\n"
                +",----ShardingTotalCount:"+shardingContext.getShardingTotalCount()+"\n"
        );
        //根据JobParameter，ShardingParameter ，ShardingItem来对应数据，片与数据的关系
        List<MessageDO> messageDOList=messageService.getMessage(shardingContext.getShardingParameter());
        if(messageDOList!=null){
            for (MessageDO messageDO: messageDOList) {
                System.out.println(
                        "id为:"+messageDO.getId()+"\n" +
                                ",----tShardingItem:"+shardingContext.getShardingItem()+"\n"
                );
                messageService.updateStatus(messageDO);
            }
        }
    }
}
