package com.codefactory.delivery.test2;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.mockito.BDDMockito.given;

@SpringBootTest
@ActiveProfiles("test")
public class Ex01 {

    @Mock
    RecSystemClient client;

    @Test
    void productRecommendServiceTest() {
       // RecSystemClient client = mock(RecSystemClient.class);
        given(client.getProducts()).willReturn(List.of(1, 2, 3, 4));

        ProductService service = new ProductService(client);
        service.process();
    }
}
