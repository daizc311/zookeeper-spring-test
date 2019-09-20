package club.dreamccc.manager.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.admin.ZooKeeperAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

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
    ZooKeeperAdmin zooKeeperAdmin() throws IOException {

        return new ZooKeeperAdmin(zkUrl, 10000, event -> {
            log.info("创建ZooKeeperAdmin成功[{}]",event);
        });
    }
//    @Bean(value = "zkClient")
//    ZooKeeper zooKeeper() throws IOException {
//
//        return new ZooKeeper(zkUrl, 1000, event -> {
//            log.info("创建ZooKeeper成功[{}]", event);
//        });
//    }
}
