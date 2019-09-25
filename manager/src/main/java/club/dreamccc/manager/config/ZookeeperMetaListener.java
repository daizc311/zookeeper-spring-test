package club.dreamccc.manager.config;

import club.dreamccc.manager.service.ZkService;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.stream.IntStream;

@Slf4j
public class ZookeeperMetaListener implements PathChildrenCacheListener {

    @Resource
    ZkService zkService;

    @Override
    public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {

        String childName = pathChildrenCacheEvent.getData().getPath().replace("/meta/", "");

        String testPath = "/test/" + childName;

        if (Objects.equals(pathChildrenCacheEvent.getType(), PathChildrenCacheEvent.Type.CHILD_ADDED)) {
            log.error(childName + ":CHILD_ADDED");

            zkService.createPersistentNode(testPath, null);
            IntStream.range(0,5).boxed().forEach(integer -> {

                zkService.createPersistentNode(testPath+"/"+integer, null);
            });

        } else if (Objects.equals(pathChildrenCacheEvent.getType(), PathChildrenCacheEvent.Type.CHILD_REMOVED)) {
            log.error(childName + ":CHILD_REMOVED");

            zkService.deleteChildrenIfNeeded(testPath);


        }


    }
}
