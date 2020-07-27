package com.luyuze.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class PaymentFallbackService implements PaymentHystrixService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "--------PaymentFallbackService fall back, paymentInfo_OK ------";
    }

    @Override
    public String paymentInfo_Timeout(Integer id) {
        return "--------PaymentFallbackService fall back, paymentInfo_Timeout ------";
    }
}
