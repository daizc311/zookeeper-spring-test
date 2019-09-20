package club.dreamccc.manager;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.admin.ZooKeeperAdmin;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ZkService {

    @Autowired
    ZooKeeperAdmin zooKeeperAdmin;
//    @Autowired
//    ZooKeeper zooKeeper;


    public void cloneNode(String sourcePath, String targetPath) throws KeeperException, InterruptedException {
//        List<String> children = zooKeeperAdmin.(sourcePath, false)


        byte[] data = zooKeeperAdmin.getData(sourcePath, false, new Stat());
        List<ACL> acls = new ArrayList<>();
        acls.add(new ACL(0, new Id("anyone", "crdwa")));

        String s = zooKeeperAdmin.create(targetPath, data, acls, CreateMode.CONTAINER);
    }

    public void listen(String nodePath) throws KeeperException, InterruptedException {


    }

    public String getNodeValue(String nodePath) throws KeeperException, InterruptedException, UnsupportedEncodingException {
        byte[] data = zooKeeperAdmin.getData(nodePath, event -> {
            log.debug("getNode {} event {}", nodePath, event);
        }, new Stat());

        String value = new String(data, "UTF-8");

        log.debug("Getting  property{} form Node {}", value, nodePath);
        return value;
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

}
