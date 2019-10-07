package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping({"/serviceB"})
public class CustomerController {

    private static final int HOVERFLY_PORT = 8500;
    private static final String HOVERFLY_HOST = "localhost";
    private static final String PROXY = "proxy";


    @GetMapping(path = {"/{id}"})
    public String invoke1 (@PathVariable long id) {
        String url = "http://localhost:5080/shipper/";

        String response = restTemplate.exchange(url  + id, HttpMethod.GET, null,
                new ParameterizedTypeReference<String>() {
                }).getBody();
        System.out.println("Actual Response : " + response);
        return response;

    }


    @Bean
    public RestTemplate restTemplate() {

        String mode = System.getProperty("mode");
        System.out.println("##################### Mode ################# " + mode);

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(HOVERFLY_HOST, HOVERFLY_PORT));
        requestFactory.setProxy(proxy);
        RestTemplate template = null;

        if (mode != null && mode.equalsIgnoreCase(PROXY)) {
            System.out.println("######### Running application in PROXY mode so that we can use simulated hoverfly server!!!!");
            template = new RestTemplate(requestFactory);
        } else {
            System.out.println("######### Running application in PRODUCTION mode so that we can use simulated hoverfly server!!!!");
            template = new RestTemplate();
        }

        return template;
    }

    @Autowired
    RestTemplate restTemplate;
}
