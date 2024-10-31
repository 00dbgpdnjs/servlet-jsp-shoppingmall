package com.nhnacademy.shoppingmall.user.service.impl;

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

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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

        int result = userRepository.updateLatestLoginAtByUserId(userId, LocalDateTime.now());

        if(result<1){
            throw new RuntimeException("fail-updateLatestLoginAtByUserId:" + userId);
        }

        // ?? 로그인 구현 - 13-2 에서 하는 건가

        return user.get();
    }

    @Override
    public List<User> getUserByRole(int page, String role) {
        return userRepository.findAllByRole(page, 2, role).getContent();
    }

}
