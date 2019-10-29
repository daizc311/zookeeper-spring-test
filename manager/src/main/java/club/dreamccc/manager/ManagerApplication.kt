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

    // 编译后变成一个内部类 默认名称就是Companion
    companion object {
        // java的main方法 在kotlin里就是个普通方法 放在伴生对象里 打上@JvmStatic注解 声明为真正的JVM静态方法
        // 被 @JvmStatic 注解的 fun 和 field 会在外部类中 生成对应的代理方法
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(ManagerApplication::class.java, *args)
        }
    }
}
