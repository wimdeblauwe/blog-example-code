package com.wimdeblauwe.examples.viewcomponentdemo.payment;

import de.tschuehly.thymeleafviewcomponent.ViewComponent;

@ViewComponent
public class PaymentViewComponent {
    private final PaymentService paymentService;

    public PaymentViewComponent(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
