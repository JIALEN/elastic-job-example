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

package com.alen;

import com.alen.entity.EmbedZookeeperServer;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class SpringMain {
    
    private static final int EMBED_ZOOKEEPER_PORT = 5181;
    
    // CHECKSTYLE:OFF
    public static void main(final String[] args) {
    // CHECKSTYLE:ON
        // 仅用于运行Elastic-Job的例子时无需额外启动Zookeeper. 如有必要, 请使用本地环境可用的Zookeeper代替.
        EmbedZookeeperServer.start(EMBED_ZOOKEEPER_PORT);
        new ClassPathXmlApplicationContext("classpath:META-INF/applicationContext.xml");
    }
}
