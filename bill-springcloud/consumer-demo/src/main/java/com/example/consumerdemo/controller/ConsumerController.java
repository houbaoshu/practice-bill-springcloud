package com.example.consumerdemo.controller;

import com.example.consumerdemo.model.Bill;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {
    private RestTemplate restTemplate;
    private DiscoveryClient discoveryClient;

    public ConsumerController(RestTemplate restTemplate, DiscoveryClient discoveryClient) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
    }

    @GetMapping("/{id}")
    public Bill queryById(@PathVariable Long id) {
    /*    String url = "http://localhost:4000/bills/" + id;

        List<ServiceInstance> serviceInstances = discoveryClient.getInstances("bill-service");;
        ServiceInstance serviceInstance = serviceInstances.get(0);
        url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/bills/" + id;*/

        String url = "http://bill-service/bills/" + id;

        return restTemplate.getForObject(url, Bill.class);
    }

}
