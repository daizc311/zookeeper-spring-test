package club.dreamccc.manager.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.admin.ZooKeeperAdmin;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.zookeeper.discovery.ZookeeperInstance;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ZkService {

    @Autowired
    ZooKeeperAdmin zooKeeperAdmin;
    //    @Autowired
//    ZooKeeper zooKeeper;



    public void removeNode(String targetPath) throws KeeperException, InterruptedException {

        zooKeeperAdmin.delete(targetPath,-1);
    }

    public String createNode(String targetPath,String data) throws KeeperException, InterruptedException {

        return zooKeeperAdmin.create(targetPath, data.getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.CONTAINER);
    }

    public void cloneNode(String sourcePath, String targetPath) throws KeeperException, InterruptedException {
//        List<String> children = zooKeeperAdmin.(sourcePath, false)


        byte[] data = zooKeeperAdmin.getData(sourcePath, false, new Stat());
        List<ACL> acls = new ArrayList<>();
        acls.add(new ACL(0, new Id("anyone", "crdwa")));

        String s = zooKeeperAdmin.create(targetPath, data,  ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.CONTAINER);
    }

    public void listen(String nodePath) throws KeeperException, InterruptedException {


    }

    public String getNodeValue(String nodePath) throws KeeperException, InterruptedException, UnsupportedEncodingException {
        byte[] data = zooKeeperAdmin.getData(nodePath, event -> log.debug("getNode {} event {}", nodePath, event), new Stat());

        String value = new String(data, StandardCharsets.UTF_8);

        log.debug("Getting  property{} form Node {}", value, nodePath);
        return value;
    }

    public ServiceInstance<ZookeeperInstance> getPrimaryMetaInstance(String applicationName) throws InterruptedException, IOException, KeeperException {
        return this.getMetaInstance(applicationName, 0);
    }

    public ServiceInstance<ZookeeperInstance> getMetaInstance(String applicationName, int num) throws KeeperException, InterruptedException, IOException {

        List<String> children = zooKeeperAdmin.getChildren("/meta/" + applicationName, false);

        String nodeValue = this.getNodeValue(String.format("/meta/%s/%s",applicationName,children.get(num)));

        ServiceInstance<ZookeeperInstance> serviceInstance = new ObjectMapper().readValue(nodeValue, new TypeReference<ServiceInstance<ZookeeperInstance>>() {
        });

        log.debug("{}",serviceInstance);

        return serviceInstance;
    }

    public void setNodeValue(String nodePath, String value) throws KeeperException, InterruptedException {

        log.debug("Setting property {} to {}", value, nodePath);
        zooKeeperAdmin.setData(nodePath, value.getBytes(), -1);
    }


    private String checkNodePath(String path, String node) {

        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        if (!node.startsWith("/")) {
            node = "/" + node;
        }
        if (node.endsWith("/")) {
            node = node.substring(0, node.length() - 1);
        }
        return path + node;

    }


    class ServiceInstanceWrapper{

    }
}
