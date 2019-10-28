package club.dreamccc.manager.controller

import club.dreamccc.server.api.interfaces.UserServerApiInterface
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
@RequestMapping("/remote/user")
class UserRemoteController(var userServerApi: UserServerApiInterface,var discoveryClient: DiscoveryClient) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @RequestMapping("login")
    fun login(userName: String?, userPasspwd: String?): String {

        val login = userServerApi.login(userName, userPasspwd);
        return login;
    }
    @RequestMapping("discoveryMsg")
    fun discoveryMsg(): MutableList<String>? {

        val services = discoveryClient.services;
        services.forEach { s: String? -> logger.debug(s) }
        return services;
    }
}
