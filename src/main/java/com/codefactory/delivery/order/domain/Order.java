package com.codefactory.delivery.order.domain;

import com.codefactory.delivery.global.infrastructure.event.Events;
import com.codefactory.delivery.global.infrastructure.persistence.BaseUserEntity;
import com.codefactory.delivery.global.infrastructure.persistence.Price;
import com.codefactory.delivery.global.infrastructure.persistence.converter.PriceConverter;
import com.codefactory.delivery.order.domain.event.OrderAcceptEvent;
import com.codefactory.delivery.order.domain.event.OrderRefundEvent;
import com.codefactory.delivery.order.domain.exception.OrderItemNotExistException;
import com.codefactory.delivery.user.domain.UserId;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

/**
 * 1. 주문 상품이 1개 이상이어야 주문이 가능
 * 2. 주문 상품의 총 금액은 주문상품 목록을 통해서만 계산된다.
 * 3. 주문 취소는 주문 접수 후 5분 이내 가능
 *      - 입금 확인 전 : 주문 취소 상태(ORDER_CANCEL)
 *      - 입금 완료 후 : 주문 환불 상태(ORDER_REFUND) / 결제 취소 진행(이벤트 발생)
 * 4. 배송중 주문 상태는 입금 확인이 되어야만 변경 가능
 */
@ToString
@Getter
@Entity
@Table(name="P_ORDER")
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseUserEntity {
    @EmbeddedId
    private OrderId id;

    @Version
    private long version;

    @Embedded
    private Orderer orderer;

    @Embedded
    private DeliveryInfo deliveryInfo;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name="P_ORDER_ITEM", joinColumns = @JoinColumn(name="order_id"))
    @OrderColumn(name="item_idx")
    private List<OrderItem> orderItems;

    @Convert(converter = PriceConverter.class)
    private Price totalOrderPrice;

    @Enumerated(EnumType.STRING)
    @Column(length=45)
    private OrderStatus status;

    @Builder
    public Order(OrderId id, UserId ordererId, String ordererName, String ordererEmail, List<OrderItem> orderItems, String deliveryAddress, String deliveryMemo, OrderStatus status) {
        this.id = Objects.requireNonNullElse(id, OrderId.of());
        this.orderer = new Orderer(ordererId, ordererName, ordererEmail);
        this.deliveryInfo = new DeliveryInfo(deliveryAddress, deliveryMemo);
        this.status = status;
        setOrderItems(orderItems);
        calculateTotalOrderPrice();
    }


    private void setOrderItems(List<OrderItem> orderItems) {
        if (orderItems == null || orderItems.isEmpty()) {
                throw new OrderItemNotExistException();
        }

        this.orderItems = orderItems;
    }

    private void calculateTotalOrderPrice() {
        this.totalOrderPrice = new Price(orderItems.stream().mapToInt(x -> x.getTotalPrice().getValue()).sum());
    }

    // 주문 접수
    public void  orderAccept() {
        this.status = OrderStatus.ORDER_ACCEPT;

        // 주문 접수 후 이벤트 발생 시키기 - 메일 전송
        Events.trigger(new OrderAcceptEvent(id));
    }

    // 주문 취소
    public void cancel() {
        // 입금 확인 전이면 주문 취소, 입금 후 주문 접수 후 5분 이내라면 상태라면 환불
        if (this.status == OrderStatus.ORDER_ACCEPT) {
            this.status = OrderStatus.ORDER_CANCEL;
        } else if (createdAt != null && createdAt.isBefore(createdAt.plusMinutes(5L))) {
            this.status = OrderStatus.ORDER_REFUND;

            // 결제 취소 요청
            Events.trigger(new OrderRefundEvent(id));
        }
    }

    /**
     * 배송 시작
     * 배송 시작은 입금 확인 후 진행, 그러나 배송지가 매장에서 배송 가능한 지역이 아니라면 배송 불가 함
     * 매장별 배송 불가 지역 체크 필요, 그러나 이 기능은 다른 도메인 기능이 필요하므로 도메인 서비스로 추가, 단순히 도메인 서비스에 주문 도메인의 delivery상태 변경 로직은 실행
     */
    public void delivery() {
        if (this.status != OrderStatus.PAYMENT_CONFIRM) {
            return;
        }

        this.status = OrderStatus.DELIVERY;
    }
}
