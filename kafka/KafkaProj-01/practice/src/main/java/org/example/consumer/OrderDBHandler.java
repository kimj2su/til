package org.example.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDBHandler {
    public static final Logger logger = LoggerFactory.getLogger(OrderDBHandler.class.getName());
    private Connection connection = null;
    private PreparedStatement insertPrepared = null;
    private static final String INSERT_ORDER_SQL =
            """
            INSERT INTO orders
            (ord_id, shop_id, menu_name, user_name, phone_number, address, order_time)
            values (?, ?, ?, ?, ?, ?, ?)
            """;

    public OrderDBHandler(String url, String user, String password) {
        try {
            this.connection = DriverManager.getConnection(url, user, password);
            this.insertPrepared = this.connection.prepareStatement(INSERT_ORDER_SQL);
        } catch(SQLException e) {
            logger.error(e.getMessage());
        }

    }

    public void insertOrder(OrderDto OrderDto)  {
        try {
            PreparedStatement pstmt = this.connection.prepareStatement(INSERT_ORDER_SQL);
            pstmt.setString(1, OrderDto.orderId);
            pstmt.setString(2, OrderDto.shopId);
            pstmt.setString(3, OrderDto.menuName);
            pstmt.setString(4, OrderDto.userName);
            pstmt.setString(5, OrderDto.phoneNumber);
            pstmt.setString(6, OrderDto.address);
            pstmt.setTimestamp(7, Timestamp.valueOf(OrderDto.orderTime));

            pstmt.executeUpdate();
        } catch(SQLException e) {
            logger.error(e.getMessage());
        }

    }

    public void insertOrders(List<OrderDto> orders) {
        try {
            PreparedStatement pstmt = this.connection.prepareStatement(INSERT_ORDER_SQL);
            for(OrderDto OrderDto : orders) {
                pstmt.setString(1, OrderDto.orderId);
                pstmt.setString(2, OrderDto.shopId);
                pstmt.setString(3, OrderDto.menuName);
                pstmt.setString(4, OrderDto.userName);
                pstmt.setString(5, OrderDto.phoneNumber);
                pstmt.setString(6, OrderDto.address);
                pstmt.setTimestamp(7, Timestamp.valueOf(OrderDto.orderTime));

                pstmt.addBatch();
            }
            pstmt.executeUpdate();

        } catch(SQLException e) {
            logger.info(e.getMessage());
        }

    }

    public void close()
    {
        try {
            logger.info("###### OrderDBHandler is closing");
            this.insertPrepared.close();
            this.connection.close();
        }catch(SQLException e) {
            logger.error(e.getMessage());
        }
    }

    public static void main(String[] args) {
        String url = "jdbc:mysql://127.0.0.1:3306/kafka";
        String user = "root";
        String password = "1234";
        OrderDBHandler orderDBHandler = new OrderDBHandler(url, user, password);

        LocalDateTime now = LocalDateTime.now();
        OrderDto orderDto = new OrderDto("ord001", "test_shop", "test_menu",
                "test_user", "test_phone", "test_address",
                now);

        orderDBHandler.insertOrder(orderDto);
        orderDBHandler.close();
    }


}