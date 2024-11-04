package com.example.PaymentService.Service;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service("RazorpayPaymentGateway")
public class RazorpayPaymentGateway implements PaymentService{
    // inject the dependency from razorpayconfig
    private RazorpayClient razorpayClient;
    public RazorpayPaymentGateway(RazorpayClient razorpayClient){
        this.razorpayClient = razorpayClient;
    }
    @Override
    public String generatePaymentLink(Long orderId) throws RazorpayException {
        // make a call to razorpay to generate payment link (Integrating razorpay payment gateway)
        // whenever we are adding any new code from 3rd party we need to add dependency
        // Initialize client
        RazorpayClient instance = new RazorpayClient("key_id", "key_secret");


        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount",1000); // read it as 10.00 bcoz of approximation
        paymentLinkRequest.put("currency","INR");
      //  paymentLinkRequest.put("accept_partial",true);
     //   paymentLinkRequest.put("first_min_partial_amount",100);
        paymentLinkRequest.put("expire_by",System.currentTimeMillis() + 10*60*1000);
        paymentLinkRequest.put("reference_id",orderId.toString());
        paymentLinkRequest.put("description","Payment for chocolate");
        JSONObject customer = new JSONObject();

        // call the order service to get order details
        // here we should not use hardcoded value , since we dont have order service lets do it
        // But actually . we should do like
        // Order order = resTemplate.getForObject("orderservice url",Order.class) something like that

        customer.put("name","Prem Kumar");
        customer.put("contact","+919999999999");
        customer.put("email","Prem.kumar@example.com");
        paymentLinkRequest.put("customer",customer);
        JSONObject notify = new JSONObject();
        notify.put("sms",true);
        notify.put("email",true);
        paymentLinkRequest.put("reminder_enable",true);
        JSONObject notes = new JSONObject();
       // notes.put("policy_name","Jeevan Bima");
       // paymentLinkRequest.put("notes",notes);
        paymentLinkRequest.put("callback_url","https://www.google.com/");
        paymentLinkRequest.put("callback_method","get");

        PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest);
        return payment.toString(); // returns complete object
      //  return payment.get("short_url").toString(); returns only short url
    }
}
