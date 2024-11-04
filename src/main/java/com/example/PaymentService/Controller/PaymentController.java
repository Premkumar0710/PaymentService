package com.example.PaymentService.Controller;

import com.example.PaymentService.DTOs.GeneratePaymentRequestLinkDto;
import com.example.PaymentService.Service.PaymentService;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {
  private PaymentService paymentService;

  public PaymentController(@Qualifier("StripePaymentGateway") PaymentService paymentService){
    this.paymentService = paymentService;
  }

  //  POST : hhtps://locahost:8080/payments
    @PostMapping()
    public String generatePaymentLink(@RequestBody GeneratePaymentRequestLinkDto generatePaymentRequestLinkDto) throws RazorpayException, StripeException {
    // ideally we should handle the exception, not here use controller advice & implement it
        return paymentService.generatePaymentLink(generatePaymentRequestLinkDto.getOrderId());
    }

    // this will be triggered by razorpay , so we cant use get
  // we need to give app url in razorpay webhook section ; don't use localhost here, coz won't work
    @PostMapping("/webhook")
    public void handleWebhookEvent(@RequestBody Object object){
      System.out.println("Webhook triggered");
      // not implementing webhook conditions as of now , unable to find documentations
    }

}
