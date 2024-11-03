package com.nhnacademy.shoppingmall.user.service.impl;

import com.nhnacademy.shoppingmall.pointHistory.domain.PointHistory;
import com.nhnacademy.shoppingmall.pointHistory.repository.PointHistoryRepositoryImpl;
import com.nhnacademy.shoppingmall.pointHistory.service.PointHistoryService;
import com.nhnacademy.shoppingmall.pointHistory.service.impl.PointHistoryServiceImpl;
import com.nhnacademy.shoppingmall.user.exception.UserAlreadyExistsException;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PointHistoryService pointHistoryService;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.pointHistoryService = new PointHistoryServiceImpl(new PointHistoryRepositoryImpl());
    }

    @Override
    public User getUser(String userId){
        //todo#4-1 회원조회
        Optional<User> UserOptional = userRepository.findById(userId);

        // ?? if 문 없이 null 반환 -> 테스트 코드
//        if(UserOptional.isEmpty()){
//            throw new UserNotFoundException(userId);
//        }
//        return UserOptional.get();
        return UserOptional.orElse(null);
    }

    @Override
    public void saveUser(User user) {
        //todo#4-2 회원등록
        if (userRepository.countByUserId(user.getUserId()) > 0)
            throw new UserAlreadyExistsException(user.getUserId());

        int result = userRepository.save(user);

        // ?? 첨에 if문 했으니까 생략 (update, del 등 다른 메서드도)
        if(result<1){
            throw new RuntimeException("fail-saveUser:" + user.getUserId());
        }
    }

    @Override
    public void updateUser(User user) {
        //todo#4-3 회원수정
        if (userRepository.countByUserId(user.getUserId()) < 1)
            throw new UserNotFoundException(user.getUserId());

        int result = userRepository.update(user);

        if(result<1){
//            throw new UserNotFoundException(user.getUserId());
            throw new RuntimeException("fail-updateUser:" + user.getUserId());
        }
    }

    @Override
    public void deleteUser(String userId) {
        //todo#4-4 회원삭제
        if (userRepository.countByUserId(userId) < 1)
            throw new UserNotFoundException(userId);

        int result = userRepository.deleteByUserId(userId);

        if(result<1){
            throw new RuntimeException("fail-deleteUser:" + userId);
//            throw new UserNotFoundException(userId);
        }
    }

    @Override
    public User doLogin(String userId, String userPassword) {
        //todo#4-5 로그인 구현, userId, userPassword로 일치하는 회원 조회
        Optional<User> user = userRepository.findByUserIdAndUserPassword(userId, userPassword);
        if(user.isEmpty()){
            throw new UserNotFoundException(userId);
        }

        if (userRepository.isFirstLoginOfTheDay(userId)) {
            userRepository.updateUserPointByUserId(userId, 10000);
            pointHistoryService.savePointHistory(new PointHistory(userId, 10000));
        }

        int result = userRepository.updateLatestLoginAtByUserId(userId, LocalDateTime.now());

        if(result<1){
            throw new RuntimeException("fail-updateLatestLoginAtByUserId:" + userId);
        }

        // ?? 로그인 구현 - 13-2 에서 하는 건가

        return user.get();
    }

    @Override
    public List<User> getUserByRole(int page, String role) {
        return userRepository.findAllByRole(page, 3, role).getContent();
    }

    @Override
    public int getCountByRole(String role) {
        return userRepository.countByRole(role);
    }

}
