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

package com.alen.entity.job.dataflow;

import com.alen.entity.fixture.entity.Foo;
import com.alen.entity.fixture.repository.FooRepository;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SpringDataflowJob implements DataflowJob<Foo> {
    
    @Resource
    private FooRepository fooRepository;
    
    @Override
    public List<Foo> fetchData(final ShardingContext shardingContext) {
        System.out.println("SpringDataflowJob Dataflow类型作业-------------任务名："+shardingContext.getJobName()+"\n"
                +"，---ShardingParameter:"+shardingContext.getShardingParameter()+"\n"
                +",----TaskId:"+shardingContext.getTaskId()+"\n"
                +",----JobParameter:"+shardingContext.getJobParameter()+"\n"
                +",----tShardingItem:"+shardingContext.getShardingItem()+"\n"
                +",----ShardingTotalCount:"+shardingContext.getShardingTotalCount()+"\n"
        );
        return fooRepository.findTodoData(shardingContext.getShardingParameter(), 10);
    }
    
    @Override
    public void processData(final ShardingContext shardingContext, final List<Foo> data) {
        System.out.println(String.format("Item: %s | Time: %s | Thread: %s | %s",
                shardingContext.getShardingItem(), new SimpleDateFormat("HH:mm:ss").format(new Date()), Thread.currentThread().getId(), "DATAFLOW PROCESS"));
        for (Foo each : data) {
            fooRepository.setCompleted(each.getId());
        }
    }
}
