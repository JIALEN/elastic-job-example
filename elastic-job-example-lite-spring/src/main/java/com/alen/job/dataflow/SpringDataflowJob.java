/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package com.alen.job.dataflow;

import com.alen.entity.MessageDO;
import com.alen.entity.fixture.entity.Foo;
import com.alen.entity.fixture.repository.FooRepository;
import com.alen.service.MessageService;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SpringDataflowJob implements DataflowJob<MessageDO> {

    @Autowired
    private MessageService messageService;


   /* 流式处理数据只有fetchData方法的返回值为null或集合长度为空时，作业才停止抓取，否则作业将一直运行下去；
    非流式处理数据则只会在每次作业执行过程中执行一次fetchData方法和processData方法，随即完成本次作业。
    如果采用流式作业处理方式，建议processData处理数据后更新其状态，
    避免fetchData再次抓取到，从而使得作业永不停止。
    流式数据处理参照TbSchedule设计，适用于不间歇的数据处理。*/
    @Override
    public List<MessageDO> fetchData(final ShardingContext shardingContext) {
        System.out.println("SpringDataflowJob Dataflow类型作业-------------任务名："+shardingContext.getJobName()+"\n"
                +"，---ShardingParameter:"+shardingContext.getShardingParameter()+"\n"
                +",----TaskId:"+shardingContext.getTaskId()+"\n"
                +",----JobParameter:"+shardingContext.getJobParameter()+"\n"
                +",----tShardingItem:"+shardingContext.getShardingItem()+"\n"
                +",----ShardingTotalCount:"+shardingContext.getShardingTotalCount()+"\n"
        );

        List<MessageDO> messageDOList=messageService.getMessage(shardingContext.getShardingParameter());

        return messageDOList;
    }
    
    @Override
    public void processData(final ShardingContext shardingContext, final List<MessageDO> data) {
        if(data!=null){
            for (MessageDO messageDO: data) {
                System.out.println(
                        "id为:"+messageDO.getId()+"\n" +
                                ",----tShardingItem:"+shardingContext.getShardingItem()+"\n"
                );
                messageService.updateStatus(messageDO);
            }
        }
    }
}
