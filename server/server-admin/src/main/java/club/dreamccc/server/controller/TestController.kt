package club.dreamccc.server.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestController{

    @RequestMapping("test")
    fun test(): String {

        return "test_OK!";
    }
}
