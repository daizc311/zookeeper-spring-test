package club.dreamccc.manager.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ApplicationBootstarpListener implements ApplicationListener {

    @Value("${spring.application.name}")
    String appName;

    @Resource
    CuratorFramework curatorFramework;


    @Override
    public void onApplicationEvent(@NotNull ApplicationEvent event) {
        // 在这里可以监听到Spring Boot的生命周期
        // 初始化环境变量
        if (event instanceof ApplicationEnvironmentPreparedEvent) {
        }
        // 初始化完成
        else if (event instanceof ApplicationPreparedEvent) {
        }
        // 应用已启动完成
        else if (event instanceof ApplicationReadyEvent) {

            // new 一个事件直接触发监听器
            WatchedEvent watchedEvent = new WatchedEvent(Watcher.Event.EventType.NodeDeleted, null, "/lock");
            new AbstractPathDestroyListener(curatorFramework) {
                @Override
                public void processEvent(WatchedEvent event) throws Exception {
                    // 尝试创建节点
                    curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath(event.getPath(), appName.getBytes());
                }
            }.process(watchedEvent);

        }
        // 应用启动，需要在代码动态添加监听器才可捕获
        else if (event instanceof ContextStartedEvent) {
        }
        // 应用停止
        else if (event instanceof ContextStoppedEvent) {
        }
        // 应用关闭
        else if (event instanceof ContextClosedEvent) {
        } else {
        }
    }


}
