package com.nhnacademy.contoller;

import com.nhnacademy.domain.Customer;
import com.nhnacademy.domain.Manager;
import com.nhnacademy.repositry.CustomerRepository;
import com.nhnacademy.repositry.ManagerRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {
    private final CustomerRepository customerRepository;
    private final ManagerRepository managerRepository;

    @GetMapping
    public String login(HttpServletRequest request) {
        if(Objects.nonNull(request.getSession(false))) {
            return "redirect:/";
        }

        return "loginForm";
    }

    /*
        세션 ID는 서버가 각 클라이언트마다 고유한 값을 생성하여 클라이언트에게 전달합니다. 이 값은 보통 쿠키(JSESSIONID)로 클라이언트에 저장됩니다.
        클라이언트는 이후의 요청에서 이 세션 ID를 포함하여 서버에 보냅니다. 서버는 이 세션 ID를 기반으로 클라이언트를 구분하고, 각 클라이언트에 대한 세션 데이터를 관리합니다.
     */
    @PostMapping
    public String doLogin(@RequestParam("id") String id,
                          @RequestParam("pwd") String pwd,
                          HttpServletRequest request) {
        if(!StringUtils.hasText(id) || !StringUtils.hasText(pwd)) {
            throw new RuntimeException("Invalid ID/PWD");
        }

        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            if (customer.get().login(request, pwd)) {
                return "redirect:/";
            }
        } else {
            Optional<Manager> manager = managerRepository.findById(id);
            if (manager.isPresent()) {
                if (manager.get().login(request, pwd)) {
                    return "redirect:/admin";
                }
            }
        }

        throw new RuntimeException("not exist user");
    }
}
