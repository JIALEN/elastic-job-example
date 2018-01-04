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

package com.alen.job.simple;

import com.alen.entity.MessageDO;
import com.alen.entity.fixture.entity.Foo;
import com.alen.entity.fixture.repository.FooRepository;
import com.alen.service.MessageService;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

public class SpringSimpleJob implements SimpleJob {


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
