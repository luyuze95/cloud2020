package com.luyuze.controller;

import com.luyuze.entity.CommonResult;
import com.luyuze.entity.Payment;
import com.luyuze.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private DiscoveryClient discoveryClient;

    @PostMapping("/payment/create")
    public CommonResult<Object> create(
            @RequestBody Payment payment
    ) {
        int result = paymentService.create(payment);
        if (result > 0) {
            return new CommonResult<>(200, "插入成功, serverPort:" + serverPort, result);
        } else {
            return new CommonResult<>(444, "插入失败");
        }
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(
            @PathVariable("id") Long id
    ) {
        int a = 10;
        Payment payment = paymentService.getPaymentById(id);
        if (payment == null) {
            return new CommonResult<>(404, "查询失败");
        } else {
            return new CommonResult<>(200, "查询成功, serverPort:" + serverPort, payment);
        }
    }

    @GetMapping("/payment/discovery")
    public Object discovery() {
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("*****element: " + service);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info(instance.getServiceId() + "\t" + instance.getHost() + "\t" + instance.getPort() + "\t" + instance.getUri());
        }
        return this.discoveryClient;
    }

    @GetMapping("/payment/lb")
    public String getPaymentLB() {
        return serverPort;
    }

    @GetMapping("/payment/zipkin")
    public String paymentZipkin() {
        return "hi, payment zipkin";
    }
}
