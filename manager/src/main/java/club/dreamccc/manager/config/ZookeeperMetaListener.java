package club.dreamccc.manager.config;

import club.dreamccc.manager.service.ZkService;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;

import javax.annotation.Resource;
import java.util.Objects;

@Slf4j
public class ZookeeperMetaListener implements PathChildrenCacheListener {

    @Resource
    ZkService zkService;

    @Override
    public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {

        String childName = pathChildrenCacheEvent.getData().getPath().replace("/meta/", "");

        if (Objects.equals(pathChildrenCacheEvent.getType(), PathChildrenCacheEvent.Type.CHILD_ADDED)) {

            log.error(childName+"CHILD_ADDED");

        }else if (Objects.equals(pathChildrenCacheEvent.getType(), PathChildrenCacheEvent.Type.CHILD_REMOVED)){

            log.error(childName+"CHILD_REMOVED");

        }





    }
}
