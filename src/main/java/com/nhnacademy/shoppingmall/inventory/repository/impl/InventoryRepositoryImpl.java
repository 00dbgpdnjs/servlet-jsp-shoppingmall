package com.nhnacademy.shoppingmall.inventory.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.inventory.domain.Inventory;
import com.nhnacademy.shoppingmall.inventory.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class InventoryRepositoryImpl implements InventoryRepository {

    @Override
    public Optional<Inventory> findByProductId(int productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "SELECT p_id, inventory_quantity FROM Inventory WHERE p_id = ?";
        ResultSet rs = null;

        log.debug("sql:{}",sql);
        // ?? try-catch-finally 계속 반복
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, productId);
            rs= psmt.executeQuery();

            if (rs.next()) {
                Inventory inventory = new Inventory(
                        rs.getInt("p_id"),
                        rs.getInt("inventory_quantity")
                );

                return Optional.of(inventory);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if(Objects.nonNull(rs)){
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return Optional.empty();
    }
}
