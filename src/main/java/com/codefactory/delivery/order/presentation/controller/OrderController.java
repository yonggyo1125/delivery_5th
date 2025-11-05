package com.codefactory.delivery.order.presentation.controller;

import com.codefactory.delivery.order.application.service.OrderCreateService;
import com.codefactory.delivery.order.application.service.dto.OrderInfoDto;
import com.codefactory.delivery.order.application.service.dto.OrderItemDto;
import com.codefactory.delivery.order.application.service.dto.OrderItemOptionDto;
import com.codefactory.delivery.order.domain.OrderId;
import com.codefactory.delivery.order.presentation.dto.CreateRequest;
import com.codefactory.delivery.order.presentation.dto.CreateResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderCreateService createService;

    // 주문 등록
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateResponse createOrder(@Valid @RequestBody CreateRequest req, @AuthenticationPrincipal Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        String name = jwt.getClaim("family_name") + (String)jwt.getClaim("given_name");
        String email = jwt.getClaim("email");

        OrderInfoDto orderInfo = OrderInfoDto
                .builder()
                .ordererId(userId)
                .ordererEmail(StringUtils.hasText(req.email()) ? req.email() : email)
                .ordererName(StringUtils.hasText(req.name()) ? req.name() : name)
                .deliveryMemo(req.memo())
                .deliveryAddress(req.address())
                .build();

        List<OrderItemDto> orderItems = req.items() == null ? null : req.items().stream()
                .map(i -> {
                    List<OrderItemOptionDto> options = i.options() == null ? null : i.options().stream()
                            .map(o -> new OrderItemOptionDto(o.idx(), o.quantity())).toList();

                    return OrderItemDto.builder()
                            .itemId(i.itemId())
                            .quantity(i.quantity())
                            .options(options)
                            .build();
                })
                .toList();

        OrderId orderId = createService.create(orderInfo, orderItems);

        return new CreateResponse(orderId.getId());
    }

}
