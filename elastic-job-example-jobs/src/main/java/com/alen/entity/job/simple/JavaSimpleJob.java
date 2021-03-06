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

package com.alen.entity.job.simple;

import com.alen.entity.fixture.entity.Foo;
import com.alen.entity.fixture.repository.FooRepositoryFactory;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.alen.entity.fixture.repository.FooRepository;

import java.util.List;

public class JavaSimpleJob implements SimpleJob {
    
    private FooRepository fooRepository = FooRepositoryFactory.getFooRepository();
    
    @Override
    public void execute(final ShardingContext shardingContext) {
        System.out.println(" JavaSimpleJob简单任务测试-------------任务名："+shardingContext.getJobName()+"\n"
                +"，---ShardingParameter:"+shardingContext.getShardingParameter()+"\n"
                +",----TaskId:"+shardingContext.getTaskId()+"\n"
                +",----JobParameter:"+shardingContext.getJobParameter()+"\n"
                +",----tShardingItem:"+shardingContext.getShardingItem()+"\n"
                +",----ShardingTotalCount:"+shardingContext.getShardingTotalCount()+"\n"
        );
        //可以在这里根据分片和ShardingParameter与具体的数据关联起来
        List<Foo> data = fooRepository.findTodoData(shardingContext.getShardingParameter(), 10);
        for (Foo each : data) {
            fooRepository.setCompleted(each.getId());
        }
    }
}
