package com.codefactory.delivery.order.application.service;

import com.codefactory.delivery.menu.domain.Item;
import com.codefactory.delivery.menu.domain.ItemId;
import com.codefactory.delivery.menu.domain.ItemOption;
import com.codefactory.delivery.menu.infrastructure.persistence.ItemDetailsDao;
import com.codefactory.delivery.order.application.service.dto.OrderInfoDto;
import com.codefactory.delivery.order.application.service.dto.OrderItemDto;
import com.codefactory.delivery.order.application.service.dto.OrderItemOptionDto;
import com.codefactory.delivery.order.domain.OrderId;
import com.codefactory.delivery.order.domain.OrderItem;
import com.codefactory.delivery.order.domain.OrderItemOption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderCreateService {

    private final ItemDetailsDao itemDetailsDao;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public OrderId create(OrderInfoDto orderInfo, List<OrderItemDto> items) {

        List<OrderItem> orderItems = toOrderItem(items);

        return null;
    }

    private List<OrderItem> toOrderItem(List<OrderItemDto> items) {
        Map<ItemId, OrderItemDto> data = items.stream()
                .collect(Collectors.toMap(i -> ItemId.of(i.itemId()), i -> i, (i1, i2) -> i2));

        List<ItemId> ids = new ArrayList<>(data.keySet());

        List<Item> storeItems = itemDetailsDao.findAllById(ids);
        if (storeItems != null) {
            List<OrderItem> orderItems = storeItems.stream()
                    .map(i -> {
                        OrderItemDto dto = data.get(i.getId());
                        List<OrderItemOptionDto> _options = dto.options();

                        List<OrderItemOption> options = _options == null ? null : _options.stream()
                                .map(o -> {
                                     ItemOption io = i.getItemOptions().get(o.idx());
                                     return OrderItemOption.builder()
                                             .name(io.getOptionName())
                                             .price(io.getAddPrice())
                                             .quantity(o.quantity())
                                             .build();
                                }).toList();

                        return OrderItem.builder()
                                .itemId(i.getId())
                                .price(i.getPrice())
                                .quantity(dto.quantity())
                                .options(options)
                                .build();
                    })
                    .toList();
            return orderItems;
        }

        return null;
    }
}
