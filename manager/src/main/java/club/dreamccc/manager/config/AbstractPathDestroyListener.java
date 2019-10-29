package club.dreamccc.manager.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * <h2>PathDestroyListener</h2>
 * <p>路径销毁监听器</p>
 * <p>可循环监听，监听失败会再次创建</p>
 *
 * @author Daizc
 * @date 2019/10/29
 */
public abstract class AbstractPathDestroyListener implements CuratorWatcher {

    private CuratorFramework curatorFramework;

    public AbstractPathDestroyListener(CuratorFramework curatorFramework) {
        this.curatorFramework = curatorFramework;
    }

    @Override
    public void process(WatchedEvent event) {
        // lock销毁触发事件
        if (event.getType() == Watcher.Event.EventType.NodeDeleted) {
            try {
                // do some thing
                processEvent(event);
            } catch (Exception e) {
                // 创建节点失败 重新注册销毁监听器
                this.reRegisterPathDestroyListener(0,event);
            }
        }
    }

    public abstract void processEvent(WatchedEvent event) throws Exception;

    /**
     * <h3>reRegisterPathDestroyListener</h3>
     * <p>重新注册销毁监听器</p>
     *
     * @param num 重试次数
     * @param event
     */
    private void reRegisterPathDestroyListener(int num, WatchedEvent event) {

        try {
            // 反过去创建Lock销毁监听
            curatorFramework.getData().usingWatcher(this).forPath(event.getPath());
        } catch (Exception e) {
            if (num < 10) {
                this.reRegisterPathDestroyListener(++num, event);
            } else {
                throw new RuntimeException("zookeeper监听lock销毁事件失败");
            }
        }
    }
}
