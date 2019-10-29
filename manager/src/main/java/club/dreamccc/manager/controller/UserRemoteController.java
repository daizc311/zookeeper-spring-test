package club.dreamccc.manager.controller;

import club.dreamccc.server.api.interfaces.UserServerApiInterface;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/remote/user")
public class UserRemoteController {

    private UserServerApiInterface userServerApi;
    private DiscoveryClient discoveryClient;

    public UserRemoteController(UserServerApiInterface userServerApi, DiscoveryClient discoveryClient) {
        this.userServerApi = userServerApi;
        this.discoveryClient = discoveryClient;
    }

    @RequestMapping("/login")
    public Object login(String userName,String userPasspwd){

        val login = userServerApi.login(userName, userPasspwd);
        return login;
    }
    @RequestMapping("/discoveryMsg")
    public List<String> discoveryMsg() {

        val services = discoveryClient.getServices();
        services.forEach(log::info);
        return services;
    }
}
