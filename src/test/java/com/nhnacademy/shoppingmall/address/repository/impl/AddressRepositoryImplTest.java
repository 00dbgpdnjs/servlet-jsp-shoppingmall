package com.nhnacademy.shoppingmall.address.repository.impl;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.repository.AddressRepository;
import com.nhnacademy.shoppingmall.address.repository.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
class AddressRepositoryImplTest {
    AddressRepository repository = new AddressRepositoryImpl();
    UserRepository userRepository = new UserRepositoryImpl();

    Address testAddress;

    @BeforeEach
    void setUp() throws SQLException {
        DbConnectionThreadLocal.initialize();

        String userId = "nhnacademy-test-user";

        User testUser = new User(userId,"nhn아카데미","nhnacademy-test-password","19900505", User.Auth.ROLE_USER,100_0000,LocalDateTime.now(),null);
        userRepository.save(testUser);

        testAddress = new Address(userId,"조선대학교1234");
        repository.save(testAddress);
    }

    @AfterEach
    void tearDown() throws SQLException {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @DisplayName("조회: address 조회 by userId")
    void findByUserId() {
        List<String> addresses = repository.findByUserId(testAddress.getUserId());
        boolean b = addresses.stream().anyMatch(address -> testAddress.getAddress().equals(address));
        Assertions.assertTrue(b);
    }

    @Test
    @DisplayName("address 등록")
    void save() {
        User testUser = new User("nhnacademy-test-user2","nhn아카데미","nhnacademy-test-password","19900505", User.Auth.ROLE_USER,100_0000,LocalDateTime.now(),null);
        userRepository.save(testUser);

        String addr= "조선대학교123456";
        Address newAddr = new Address(testUser.getUserId(), addr);
        int res = repository.save(newAddr);
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, res),
                () -> Assertions.assertEquals(addr, repository.findByUserId(newAddr.getUserId()).getFirst())
        );
    }

    @Test
    void count() {
        Assertions.assertEquals(1, repository.count(testAddress));
    }
}
