package com.codefactory.delivery.payment.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
public class CreatePaymentTest {
    /**
     * 클라이언트 키: test_ck_QbgMGZzorzzw2vaXkbnNrl5E1em4
     * 시크릿 키: test_sk_AQ92ymxN342P5MJNpzXjrajRKXvd
     *        base64:  dGVzdF9za19BUTkyeW14TjM0MlA1TUpOcHpYanJhalJLWHZkOg==
     * 보안 키: b03bbca01566d4bcdb78f5ea27566a86f30333fdc4b1d7fd7171e823f612e214
     *
     * 결제 요청 -> 결제 인증(이 단계에서  paymentKey가 발급 됨) -> 결제 승인
     *
     */
    @Test
    void createPaymentTest() {
        RestClient client = RestClient.builder().baseUrl(URI.create("https://api.tosspayments.com/v1/payments/confirm")).build();
        ResponseEntity<String> body = client.post()
                .header("Authorization", "Basic dGVzdF9za19BUTkyeW14TjM0MlA1TUpOcHpYanJhalJLWHZkOg==")
                .header("Content-Type", "application/json")
                .header("Idempotency-Key", UUID.randomUUID().toString())
                .body("{\"paymentKey\":\"t9080334358c54fc137f\",\"orderId\":\"CU0000DB7C\",\"amount\":15000}")
                .retrieve()
                .toEntity(String.class);
        System.out.println(body);
    }
}
