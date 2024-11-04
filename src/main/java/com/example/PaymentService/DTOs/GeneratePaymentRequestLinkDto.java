package com.example.PaymentService.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneratePaymentRequestLinkDto {
    private long orderId;
}
