package com.wimdeblauwe.examples.viewcomponentdemo.payment;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentService {

    public List<PaymentMethod> getPaymentMethods() {
        return List.of(PaymentMethod.values());
    }
}
