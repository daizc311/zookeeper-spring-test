package club.dreamccc.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * <h2>ManagerApplication</h2>
 * <p>请添加描述</p>
 *
 * @author Daizc
 * @date 2019/9/20
 */

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}
