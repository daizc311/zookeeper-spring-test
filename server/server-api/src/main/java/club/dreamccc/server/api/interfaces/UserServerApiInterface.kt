package club.dreamccc.server.api.interfaces

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

//@FeignClient(name = "server",
//        contextId = "server#UserServerApiInterface",
//        fallback = UserServerApiInterface.UserServerApiInterfaceFallback::class)
        @FeignClient(name = "server",
                path = "/api/user",
        contextId = "server#UserServerApiInterface" )
interface UserServerApiInterface{


    @RequestMapping("/login")
    fun login(@RequestParam("userName") userName: String?,@RequestParam("userPwd")  userPwd: String?): String ;

//    @Component
//    class UserServerApiInterfaceFallback : UserServerApiInterface {
//        override fun login(userName: String?, userPwd: String?): String {
//
//            return "ERROR:FALLBACK!"
//        }
//    }
}
