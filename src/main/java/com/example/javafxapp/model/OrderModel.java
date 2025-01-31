package com.example.javafxapp.model;

import com.example.javafxapp.db.DBConnection;
import com.example.javafxapp.dto.OrderDetailDto;
import com.example.javafxapp.dto.OrderDto;

import java.sql.*;

public class OrderModel {
    public static boolean placeOrder(OrderDto orderDto) throws SQLException, ClassNotFoundException {
        boolean status = false;
        Connection connection;

        connection = DBConnection.getDBConnection().getConnection();

        //disable auto commit feature
        connection.setAutoCommit(false);

        PreparedStatement stm1 = connection.prepareStatement("insert into orders (date,total_price) values (?,?)", Statement.RETURN_GENERATED_KEYS);
        stm1.setObject(1, orderDto.getOrderDate());
        stm1.setObject(2, orderDto.getSubTotal());

        int orderSave = stm1.executeUpdate();

        if (orderSave > 0) {
            ResultSet generatedKeys = stm1.getGeneratedKeys();
            if (generatedKeys.next()) {
                int orderId = generatedKeys.getInt(1);
                for (OrderDetailDto dto : orderDto.getOrderDetails()) {
                    PreparedStatement stm2 = connection.prepareStatement("insert into order_detail (o_id,v_id,qty,price) values (?,?,?,?)");
                    stm2.setObject(1, orderId);
                    stm2.setObject(2, dto.getVehicleId());
                    stm2.setObject(3, dto.getQty());
                    stm2.setObject(4, dto.getPrice());

                    int orderDetailSave = stm2.executeUpdate();

                    if (orderDetailSave > 0) {
                        PreparedStatement stm3 = connection.prepareStatement("update vehicle set qty=qty-? where id=?");
                        stm3.setObject(1, dto.getQty());
                        stm3.setObject(2, dto.getVehicleId());

                        int vehicleQtyUpdate = stm3.executeUpdate();

                        if (vehicleQtyUpdate <= 0) {
                            connection.rollback();
                            connection.setAutoCommit(true);
                        }

                    } else {
                        connection.rollback();
                        connection.setAutoCommit(true);
                    }
                }
            }

            connection.commit();
            connection.setAutoCommit(true);
            status = true;

        } else {
            connection.rollback();
            connection.setAutoCommit(true);
        }

        return status;
    }
}