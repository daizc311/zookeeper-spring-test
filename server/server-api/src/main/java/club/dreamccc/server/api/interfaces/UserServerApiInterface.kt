package club.dreamccc.server.api

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestMapping

@FeignClient(name = "server",
        contextId = "server#UserServerApiInterface",
        fallback = UserServerApiInterface.UserServerApiInterfaceFallback::class)
interface UserServerApiInterface{


    @RequestMapping("login")
    fun login(userName: String?, userPwd: String?): String ;

    @Component
    class UserServerApiInterfaceFallback : UserServerApiInterface{
        override fun login(userName: String?, userPwd: String?): String {

            return "ERROR:FALLBACK!"
        }
    }
}
