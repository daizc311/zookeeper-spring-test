package club.dreamccc.manager.controller;

import club.dreamccc.manager.service.ZkService;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.zookeeper.discovery.ZookeeperInstance;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * <h2>TestController</h2>
 * <p>请添加描述</p>
 *
 * @author Daizc
 * @date 2019/9/20
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Value(value = "${spring.cloud.zookeeper.connect-string}")
    String zkUrl;

    @Resource
    ZkService zkService;

    @RequestMapping("/zk1")
    public Object zk1() throws IOException {

        ZooKeeper zooKeeper = new ZooKeeper(zkUrl, 1000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

                if (watchedEvent.getType() == Event.EventType.NodeCreated) {

                    try {
                        zkService.cloneNode(watchedEvent.getPath(), "/path1/123");
                    } catch (KeeperException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        return null;
    }

    @RequestMapping("/zk2")
    public Object zk2() throws IOException, KeeperException, InterruptedException {
        ServiceInstance<ZookeeperInstance> control = zkService.getPrimaryMetaInstance("control");
//        zkService.cloneNode("/meta/manager", "/123");
        return control.getPayload().getMetadata();
    }

    @RequestMapping("/zk3")
    public String getNodeValue(String nodePath) throws IOException, KeeperException, InterruptedException {

        return zkService.getNodeValue(nodePath);
    }
}
