package com.codefactory.delivery.order.domain.service;

import java.util.List;


public interface DeliveryCoordinate {
    List<Double> getCoordinate(String address);
}
