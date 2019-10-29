package club.dreamccc.manager

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.scheduling.annotation.EnableAsync

/**
 * <h2>ManagerApplication</h2>
 *
 * 请添加描述
 *
 * @author Daizc
 * @date 2019/9/20
 */

@SpringBootApplication(scanBasePackages = ["club.dreamccc"])
@EnableFeignClients(basePackages = ["club.dreamccc.server.api.interfaces"])
@EnableDiscoveryClient
@EnableAsync
open class ManagerApplication {

    fun main(args: Array<String>) {
        SpringApplication.run(ManagerApplication::class.java, *args)
    }

}

fun main(args: Array<String>) {
    ManagerApplication().main(args)
}
