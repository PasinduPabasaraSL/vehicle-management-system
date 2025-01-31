package com.example.javafxapp.dto;

import java.util.ArrayList;

public class OrderDto {
    private String orderDate;
    private double subTotal;
    private ArrayList<OrderDetailDto> orderDetails;

    public OrderDto(String orderDate, double subTotal, ArrayList<OrderDetailDto> orderDetails) {
        this.orderDate = orderDate;
        this.subTotal = subTotal;
        this.orderDetails = orderDetails;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public ArrayList<OrderDetailDto> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(ArrayList<OrderDetailDto> orderDetails) {
        this.orderDetails = orderDetails;
    }

}