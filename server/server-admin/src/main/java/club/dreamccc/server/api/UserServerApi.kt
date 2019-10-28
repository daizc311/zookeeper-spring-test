package club.dreamccc.server.api

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/userz`")
class UserServerApi{

    @RequestMapping("login")
    fun login(userName: String?, userPwd: String?): String {

        return userName+userPwd;
    }
}
