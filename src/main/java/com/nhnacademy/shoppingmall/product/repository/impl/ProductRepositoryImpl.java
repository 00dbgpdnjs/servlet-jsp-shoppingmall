package com.nhnacademy.shoppingmall.product.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class ProductRepositoryImpl implements ProductRepository {
    @Override
    public int save(Product product) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "insert into Product(p_name, p_price, thumbnail_image, detail_image) values(?,?,?,?)";
        log.debug("sql:{}",sql);

        try(
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ){
            statement.setString(1, product.getpName());
            statement.setInt(2, product.getpPrice());

            if (product.getThumbnailImage() != null) {
                statement.setString(3, product.getThumbnailImage());
            } else {
                statement.setNull(3, Types.VARCHAR);
            }

            if (product.getDetailImage() != null) {
                statement.setString(4, product.getDetailImage());
            } else {
                statement.setNull(4, Types.VARCHAR);
            }

            int result = statement.executeUpdate();
            log.debug("save:{}",result);
            if(result > 0 && !product.getCategoryNames().isEmpty()) {
                // java.sql.SQLException: Before start of result set
//                int generatedId = statement.getGeneratedKeys().getInt(1);
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        saveProductCategories(generatedId, product.getCategoryNames());
                    }
                }
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Product product) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "update Product set p_name=?, p_price=?, thumbnail_image=?, detail_image=? where p_id=?";
        log.debug("sql:{}",sql);

        try(
                PreparedStatement statement = connection.prepareStatement(sql);
        ){
            statement.setString(1, product.getpName());
            statement.setInt(2, product.getpPrice());

            if (product.getThumbnailImage() != null) {
                statement.setString(3, product.getThumbnailImage());
            } else {
                statement.setNull(3, Types.VARCHAR);
            }

            if (product.getDetailImage() != null) {
                statement.setString(4, product.getDetailImage());
            } else {
                statement.setNull(4, Types.VARCHAR);
            }

            statement.setInt(5, product.getpId());

            int result = statement.executeUpdate();
            log.debug("save:{}",result);
            deleteProductCategories(product.getpId());
            if(result > 0 && !product.getCategoryNames().isEmpty()) {
                saveProductCategories(product.getpId(), product.getCategoryNames());
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Product> findById(int productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "select p_id, p_name, p_price, thumbnail_image, detail_image from Product where p_id = ?";
        ResultSet rs = null;

        log.debug("sql:{}",sql);
        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, productId);
            rs= psmt.executeQuery();

            if(rs.next()){
                Product p = new Product(
                        rs.getInt("p_id"),
                        rs.getString("p_name"),
                        rs.getInt("p_price"),
                        rs.getString("thumbnail_image"),
                        rs.getString("detail_image")
                );

                p.setCategoryNames(getCategoryNamesByPId(p.getpId()));
                return Optional.of(p);
            }
        } catch (SQLException e) {
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

    @Override
    public Optional<Product> findByName(String productName) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "select p_id, p_name, p_price, thumbnail_image, detail_image from Product where p_name = ?";
        ResultSet rs = null;

        log.debug("sql:{}",sql);
        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, productName);
            rs= psmt.executeQuery();

            if(rs.next()){
                Product p = new Product(
                        rs.getInt("p_id"),
                        rs.getString("p_name"),
                        rs.getInt("p_price"),
                        rs.getString("thumbnail_image"),
                        rs.getString("detail_image")
                );

                return Optional.of(p);
            }
        } catch (SQLException e) {
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

    private void saveProductCategories(int productId, List<String> categoryNames) throws SQLException {
        Connection connection = DbConnectionThreadLocal.getConnection();
        // ?? productRepo인데 category 테이블 select 이렇게 섞여도 되나
        String categoryIdLookupSql = "SELECT category_id FROM Category WHERE category_name = ?";
        String sql = "INSERT INTO Product_Category (p_id, category_id) VALUES (?, ?)";

        try (PreparedStatement categoryStmt = connection.prepareStatement(categoryIdLookupSql)) {
            for (String categoryName : categoryNames) {
                categoryStmt.setString(1, categoryName);
                try (ResultSet rs = categoryStmt.executeQuery()) {
                    if (rs.next()) {
                        int categoryId = rs.getInt("category_id");
                        try (PreparedStatement insertStmt = connection.prepareStatement(sql)) {
                            insertStmt.setInt(1, productId);
                            insertStmt.setInt(2, categoryId);
                            insertStmt.executeUpdate(); // 카테고리 저장
                        }
                    } else {
                        log.warn("Category not found:{}", categoryName);
                    }
                }
            }
        }
    }

    private void deleteProductCategories(int productId) throws SQLException {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "delete from Product_Category where p_id=?";

        try(
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setInt(1, productId);
            int result = statement.executeUpdate();
            log.debug("result:{}",result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int count() {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "select count(*) from Product";
        ResultSet rs = null;

        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            rs = psmt.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if(Objects.nonNull(rs)) {
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return 0;
    }

    @Override
    public int countByProductId(int productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        int count=0;
        String sql = "select count(*) from Product where p_id=?";
        ResultSet rs;

        try(PreparedStatement psmt = connection.prepareStatement(sql)){
            psmt.setInt(1, productId);
            rs = psmt.executeQuery();
            if(rs.next()){
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return count;
    }

    @Override
    public int countByCategory(String categoryName) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        int count=0;
        String sql = "select count(*) from Product_Category natural join Category where category_name=?";
        ResultSet rs;

        try(PreparedStatement psmt = connection.prepareStatement(sql)){
            psmt.setString(1, categoryName);
            rs = psmt.executeQuery();
            if(rs.next()){
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return count;
    }

    @Override
    public int delete(int productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "delete from Product where p_id=?";

        try(
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setInt(1, productId);
            int result = statement.executeUpdate();
            log.debug("result:{}",result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<Product> findAll(int page, int pageSize) {
        int offset = (page-1) * pageSize;

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "select p_id, p_name, p_price, thumbnail_image, detail_image from Product limit  ?,?";
        ResultSet rs = null;

        log.debug("sql:{}",sql);
        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1,offset);
            psmt.setInt(2,pageSize);
            rs= psmt.executeQuery();
            List<Product> products = new ArrayList<>(pageSize);

            while(rs.next()){
                Product p = new Product(
                                rs.getInt("p_id"),
                                rs.getString("p_name"),
                                rs.getInt("p_price"),
                                rs.getString("thumbnail_image"),
                                rs.getString("detail_image")
                );

                p.setCategoryNames(getCategoryNamesByPId(p.getpId()));
                products.add(p);
            }

            long total =0;

            if(!products.isEmpty()){
                total = count();
            }

            return new Page<Product>(products, total);

        } catch (SQLException e) {
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
    }

    @Override
    public Page<Product> findByCategory(int page, int pageSize, String categoryName) {
        int offset = (page-1) * pageSize;

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "select p.p_id, p.p_name, p.p_price, p.thumbnail_image, p.detail_image from Product p inner join Product_Category pc on p.p_id=pc.p_id inner join Category c on pc.category_id=c.category_id where category_name=? limit  ?,?";
        ResultSet rs = null;

        log.debug("sql:{}",sql);
        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1,categoryName);
            psmt.setInt(2,offset);
            psmt.setInt(3,pageSize);
            rs= psmt.executeQuery();
            List<Product> productList = new ArrayList<>(pageSize);

            while(rs.next()){
                productList.add(
                        new Product(
                                rs.getInt("p_id"),
                                rs.getString("p_name"),
                                rs.getInt("p_price"),
                                rs.getString("thumbnail_image"),
                                rs.getString("detail_image")
                        )
                );
            }

            long total =0;

            if(!productList.isEmpty()){
                total = countByCategory(categoryName);
            }

            return new Page<Product>(productList, total);

        } catch (SQLException e) {
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
    }

    @Override
    public int countByProductName(String productName) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        int count=0;
        String sql = "select count(*) from Product where p_name=? ";
        ResultSet rs = null;

        try(PreparedStatement psmt = connection.prepareStatement(sql)){
            psmt.setString(1, productName);
            rs = psmt.executeQuery();
            if(rs.next()){
                count = rs.getInt(1);
                if(count>1) {
                    log.error("[{}] 상품이 중복됨(총 개수: {})", productName, count);
                    throw new RuntimeException(String.format("[%s] 상품이 중복됨(총 개수: %d)", productName, count));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return count;
    }

    private List<String> getCategoryNamesByPId(int pId) throws SQLException {
        Connection connection = DbConnectionThreadLocal.getConnection();

        List<String> categoryNames = new ArrayList<>();
        String sql = "SELECT category_name FROM Product_Category natural join Category WHERE p_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, pId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                categoryNames.add(resultSet.getString("category_name"));
            }
        }
        return categoryNames;
    }
}
