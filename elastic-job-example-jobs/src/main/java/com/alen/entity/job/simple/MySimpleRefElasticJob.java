package com.alen.entity.job.simple;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

/**
 * job-ref 	String 	否 		作业关联的beanId，该配置优先级大于class属性配置
 *
 * @author alen
 * @create 2018-01-03 16:01
 **/
public class MySimpleRefElasticJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println("MySimpleRefElasticJob job-ref 简单任务-------------任务名："+shardingContext.getJobName()+"\n"
                +"，---ShardingParameter:"+shardingContext.getShardingParameter()+"\n"
                +",----TaskId:"+shardingContext.getTaskId()+"\n"
                +",----JobParameter:"+shardingContext.getJobParameter()+"\n"
                +",----tShardingItem:"+shardingContext.getShardingItem()+"\n"
                +",----ShardingTotalCount:"+shardingContext.getShardingTotalCount()+"\n"
        );
    }
}
