package club.dreamccc.manager.controller

import org.apache.curator.framework.CuratorFramework
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.Resource

@RestController
@RequestMapping("/zk")
class ZookeeperController {

    @Resource
    internal var curatorFramework: CuratorFramework? = null


    @RequestMapping("/show")
    fun show(): Any {

        val bytes = curatorFramework!!.data.forPath("/lock")

        return String(bytes)
    }

}
