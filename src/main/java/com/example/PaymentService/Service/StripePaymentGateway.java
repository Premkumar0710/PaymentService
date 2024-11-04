package com.example.PaymentService.Service;

import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.stripe.model.Price.*;

@Service("StripePaymentGateway")
public class StripePaymentGateway implements PaymentService{
    @Value("${stripe.api.key}")
    private String stripeApiKey;
    @Override
    public String generatePaymentLink(Long orderId) throws RazorpayException, StripeException {
        // make a call to stripe client to generate payment link (Integrating razorpay payment gateway)
        Stripe.apiKey = stripeApiKey;
        PriceCreateParams priceCreateParams =
                PriceCreateParams.builder()
                        .setCurrency("usd")
                        .setUnitAmount(1000L)
                        .setRecurring(
                                PriceCreateParams.Recurring.builder()
                                        .setInterval(PriceCreateParams.Recurring.Interval.MONTH)
                                        .build()
                        )
                        .setProductData(
                                PriceCreateParams.ProductData.builder().setName("Five star chocolate").build()
                        )
                        .build();
        Price price = create(priceCreateParams);


        PaymentLinkCreateParams paymentLinkCreateParams =
                PaymentLinkCreateParams.builder()
                        .addLineItem(
                                PaymentLinkCreateParams.LineItem.builder()
                                        .setPrice(price.getId())
                                        .setQuantity(1L)
                                        .build()
                        )
                        .build();
        PaymentLink paymentLink = PaymentLink.create(paymentLinkCreateParams);
        return paymentLink.toString();
    }
}
