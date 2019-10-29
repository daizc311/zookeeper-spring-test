package club.dreamccc.manager.controller;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;


@RequestMapping("/zk")
public class ZookeeperController {

    @Resource
    CuratorFramework curatorFramework;

    @RequestMapping("/show")
    public Object show() throws Exception {

        byte[] bytes = curatorFramework.getData().forPath("/lock");

        return new String(bytes);
    }

}


