package club.dreamccc.manager.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.admin.ZooKeeperAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.IOException;

/**
 * <h2>ZookeeperConfig</h2>
 * <p>请添加描述</p>
 *
 * @author Daizc
 * @date 2019/9/20
 */
@Configuration
@Slf4j
public class ZookeeperConfig {

    @Value(value = "${spring.cloud.zookeeper.connect-string}")
    String zkUrl;


    @Bean(value = "zkAdmin")
    @Lazy
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    ZooKeeperAdmin zooKeeperAdmin() throws IOException, KeeperException, InterruptedException {

        ZooKeeperAdmin zooKeeperAdmin = new ZooKeeperAdmin(zkUrl, 10000, event -> log.info("创建ZooKeeperAdmin成功[{}],default watcher start", event));

        return zooKeeperAdmin;
    }

    private ThreadPoolTaskExecutor zkCuratorTheadPool;

    @Bean("zkCuratorTheadPool")
    public AsyncTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        zkCuratorTheadPool = executor;

        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(30);

        return executor;
    }

    @Bean(value = "zkCurator")
    CuratorFramework curatorFramework() throws Exception {

        // 多个地址逗号隔开
        CuratorFramework zkCurator = CuratorFrameworkFactory.builder().connectString(zkUrl)
                // 连接超时时间
                .sessionTimeoutMs(1000)
                // 会话超时时间
                .connectionTimeoutMs(1000)
                // 刚开始重试间隔为1秒，之后重试间隔逐渐增加，最多重试不超过三次
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .threadFactory(zkCuratorTheadPool)
                .build();

        zkCurator.start();

        return zkCurator;
    }

    private static final String META_NAME_SPACE = "/meta";


}
