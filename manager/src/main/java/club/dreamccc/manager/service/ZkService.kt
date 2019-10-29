package club.dreamccc.manager.service

import lombok.extern.slf4j.Slf4j
import org.apache.curator.framework.CuratorFramework
import org.apache.curator.framework.recipes.cache.*
import org.apache.zookeeper.CreateMode
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.text.MessageFormat
import javax.annotation.Resource

@Service
@Slf4j
class ZkService {

    private val log = LoggerFactory.getLogger(this.javaClass)
    
    @Resource
    lateinit var zkCurator: CuratorFramework


    /**
     * 创建永久Zookeeper节点
     * @author zifangsky
     * @date 2018/8/1 14:31
     * @since 1.0.0
     * @param nodePath 节点路径（如果父节点不存在则会自动创建父节点），如：/curator
     * @param nodeValue 节点数据
     * @return java.lang.String 返回创建成功的节点路径
     */
    fun createPersistentNode(nodePath: String, nodeValue: String): String? {
        try {
            return zkCurator.create().creatingParentsIfNeeded()
                    .forPath(nodePath, nodeValue.toByteArray())
        } catch (e: Exception) {
            log.error(MessageFormat.format("创建永久Zookeeper节点失败,nodePath:{0},nodeValue:{1}", nodePath, nodeValue), e)
        }

        return null
    }

    /**
     * 创建永久有序Zookeeper节点
     * @author zifangsky
     * @date 2018/8/1 14:31
     * @since 1.0.0
     * @param nodePath 节点路径（如果父节点不存在则会自动创建父节点），如：/curator
     * @param nodeValue 节点数据
     * @return java.lang.String 返回创建成功的节点路径
     */
    fun createSequentialPersistentNode(nodePath: String, nodeValue: String): String? {
        try {
            return zkCurator.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT_SEQUENTIAL)
                    .forPath(nodePath, nodeValue.toByteArray())
        } catch (e: Exception) {
            log.error(MessageFormat.format("创建永久有序Zookeeper节点失败,nodePath:{0},nodeValue:{1}", nodePath, nodeValue), e)
        }

        return null
    }

    /**
     * 创建临时Zookeeper节点
     * @author zifangsky
     * @date 2018/8/1 14:31
     * @since 1.0.0
     * @param nodePath 节点路径（如果父节点不存在则会自动创建父节点），如：/curator
     * @param nodeValue 节点数据
     * @return java.lang.String 返回创建成功的节点路径
     */
    fun createEphemeralNode(nodePath: String, nodeValue: String): String? {
        try {
            return zkCurator.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .forPath(nodePath, nodeValue.toByteArray())
        } catch (e: Exception) {
            log.error(MessageFormat.format("创建临时Zookeeper节点失败,nodePath:{0},nodeValue:{1}", nodePath, nodeValue), e)
        }

        return null
    }

    /**
     * 创建临时有序Zookeeper节点
     * @author zifangsky
     * @date 2018/8/1 14:31
     * @since 1.0.0
     * @param nodePath 节点路径（如果父节点不存在则会自动创建父节点），如：/curator
     * @param nodeValue 节点数据
     * @return java.lang.String 返回创建成功的节点路径
     */
    fun createSequentialEphemeralNode(nodePath: String, nodeValue: String): String? {
        try {
            return zkCurator.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath(nodePath, nodeValue.toByteArray())
        } catch (e: Exception) {
            log.error(MessageFormat.format("创建临时有序Zookeeper节点失败,nodePath:{0},nodeValue:{1}", nodePath, nodeValue), e)
        }

        return null
    }

    /**
     * 检查Zookeeper节点是否存在
     * @author zifangsky
     * @date 2018/8/1 17:06
     * @since 1.0.0
     * @param nodePath 节点路径
     * @return boolean 如果存在则返回true
     */
    fun checkExists(nodePath: String): Boolean {
        try {
            val stat = zkCurator.checkExists().forPath(nodePath)

            return stat != null
        } catch (e: Exception) {
            log.error(MessageFormat.format("检查Zookeeper节点是否存在出现异常,nodePath:{0}", nodePath), e)
        }

        return false
    }

    /**
     * 获取某个Zookeeper节点的所有子节点
     * @author zifangsky
     * @date 2018/8/1 17:06
     * @since 1.0.0
     * @param nodePath 节点路径
     * @return java.util.List<java.lang.String> 返回所有子节点的节点名
    </java.lang.String> */
    fun getChildren(nodePath: String): List<String>? {
        try {
            return zkCurator.children.forPath(nodePath)
        } catch (e: Exception) {
            log.error(MessageFormat.format("获取某个Zookeeper节点的所有子节点出现异常,nodePath:{0}", nodePath), e)
        }

        return null
    }

    /**
     * 获取某个Zookeeper节点的数据
     * @author zifangsky
     * @date 2018/8/1 17:06
     * @since 1.0.0
     * @param nodePath 节点路径
     * @return java.lang.String
     */
    fun getData(nodePath: String): String? {
        try {
            return String(zkCurator.data.forPath(nodePath))
        } catch (e: Exception) {
            log.error(MessageFormat.format("获取某个Zookeeper节点的数据出现异常,nodePath:{0}", nodePath), e)
        }

        return null
    }

    /**
     * 设置某个Zookeeper节点的数据
     * @author zifangsky
     * @date 2018/8/1 17:06
     * @since 1.0.0
     * @param nodePath 节点路径
     */
    fun setData(nodePath: String, newNodeValue: String) {
        try {
            zkCurator.setData().forPath(nodePath, newNodeValue.toByteArray())
        } catch (e: Exception) {
            log.error(MessageFormat.format("设置某个Zookeeper节点的数据出现异常,nodePath:{0}", nodePath), e)
        }

    }

    /**
     * 删除某个Zookeeper节点
     * @author zifangsky
     * @date 2018/8/1 17:06
     * @since 1.0.0
     * @param nodePath 节点路径
     */
    fun delete(nodePath: String) {
        try {
            zkCurator.delete().guaranteed().forPath(nodePath)
        } catch (e: Exception) {
            log.error(MessageFormat.format("删除某个Zookeeper节点出现异常,nodePath:{0}", nodePath), e)
        }

    }

    /**
     * 级联删除某个Zookeeper节点及其子节点
     * @author zifangsky
     * @date 2018/8/1 17:06
     * @since 1.0.0
     * @param nodePath 节点路径
     */
    fun deleteChildrenIfNeeded(nodePath: String) {
        try {
            zkCurator.delete().guaranteed().deletingChildrenIfNeeded().forPath(nodePath)
        } catch (e: Exception) {
            log.error(MessageFormat.format("级联删除某个Zookeeper节点及其子节点出现异常,nodePath:{0}", nodePath), e)
        }

    }

    /**
     *
     * **注册节点监听器**
     * NodeCache: 对一个节点进行监听，监听事件包括指定路径的增删改操作
     * @author zifangsky
     * @date 2018/8/1 19:08
     * @since 1.0.0
     * @param nodePath 节点路径
     * @return void
     */
    fun registerNodeCacheListener(nodePath: String): NodeCache? {
        try {
            //1. 创建一个NodeCache
            val nodeCache = NodeCache(zkCurator, nodePath)

            //2. 添加节点监听器
            nodeCache.listenable.addListener(NodeCacheListener {
                val childData = nodeCache.currentData
                if (childData != null) {
                    println("Path: " + childData.path)
                    println("Stat:" + childData.stat)
                    println("Data: " + String(childData.data))
                }
            })

            //3. 启动监听器
            nodeCache.start()

            //4. 返回NodeCache
            return nodeCache
        } catch (e: Exception) {
            log.error(MessageFormat.format("注册节点监听器出现异常,nodePath:{0}", nodePath), e)
        }

        return null
    }

    /**
     *
     * **注册子目录监听器**
     * PathChildrenCache：对指定路径节点的一级子目录监听，不对该节点的操作监听，对其子目录的增删改操作监听
     * @author zifangsky
     * @date 2018/8/2 10:01
     * @since 1.0.0
     * @param nodePath 节点路径
     * @param listener 监控事件的回调接口
     * @return org.apache.curator.framework.recipes.cache.PathChildrenCache
     */
    fun registerPathChildListener(nodePath: String, listener: PathChildrenCacheListener): PathChildrenCache? {
        try {
            //1. 创建一个PathChildrenCache
            val pathChildrenCache = PathChildrenCache(zkCurator, nodePath, true)

            //2. 添加目录监听器
            pathChildrenCache.listenable.addListener(listener)

            //3. 启动监听器
            pathChildrenCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE)

            //4. 返回PathChildrenCache
            return pathChildrenCache
        } catch (e: Exception) {
            log.error(MessageFormat.format("注册子目录监听器出现异常,nodePath:{0}", nodePath), e)
        }

        return null
    }

    /**
     *
     * **注册目录监听器**
     * TreeCache：综合NodeCache和PathChildrenCahce的特性，可以对整个目录进行监听，同时还可以设置监听深度
     * @author zifangsky
     * @date 2018/8/2 10:01
     * @since 1.0.0
     * @param nodePath 节点路径
     * @param maxDepth 自定义监控深度
     * @param listener 监控事件的回调接口
     * @return org.apache.curator.framework.recipes.cache.TreeCache
     */
    fun registerTreeCacheListener(nodePath: String, maxDepth: Int, listener: TreeCacheListener): TreeCache? {
        try {
            //1. 创建一个TreeCache
            val treeCache = TreeCache.newBuilder(zkCurator, nodePath)
                    .setCacheData(true)
                    .setMaxDepth(maxDepth)
                    .build()

            //2. 添加目录监听器
            treeCache.listenable.addListener(listener)

            //3. 启动监听器
            treeCache.start()

            //4. 返回TreeCache
            return treeCache
        } catch (e: Exception) {
            log.error(MessageFormat.format("注册目录监听器出现异常,nodePath:{0},maxDepth:{1}", nodePath), e)
        }

        return null
    }


}
