package com.nhnacademy.shoppingmall.controller.order;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.controller.cart.Cart;
import com.nhnacademy.shoppingmall.inventory.domain.Inventory;
import com.nhnacademy.shoppingmall.inventory.exception.InventoryNotFoundException;
import com.nhnacademy.shoppingmall.inventory.repository.impl.InventoryRepositoryImpl;
import com.nhnacademy.shoppingmall.inventory.service.InventoryService;
import com.nhnacademy.shoppingmall.inventory.service.impl.InventoryServiceImpl;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.repository.impl.OrderRepositoryImpl;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import com.nhnacademy.shoppingmall.order.service.impl.OrderServiceImpl;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.exception.ProductNotFoundException;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.transaction.Transactional;
import java.util.Objects;

@Transactional
@RequestMapping(method = RequestMapping.Method.POST, value = "/orderAction.do")
public class OrderPostController implements BaseController {
    private final InventoryService inventoryService = new InventoryServiceImpl(new InventoryRepositoryImpl());
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private final OrderService orderService = new OrderServiceImpl(new OrderRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String productIdParam = req.getParameter("product_id");
        String quantityParam = req.getParameter("quantity");

        if (!isValid(productIdParam) || !isValid(quantityParam)) {
            throw new RuntimeException("product_id or quantity parameter is empty");
        }

        int productId = Integer.parseInt(productIdParam);
        int quantity = Integer.parseInt(quantityParam);

        if(productId < 1) {
            throw new RuntimeException("quantity parameter less than 1");
        }

        String userId = (String) req.getSession(false).getAttribute("id");
        // ?? user 존재 체크

        // 주문 - 재고 체크
        // ?? 재고만 체크하는데 굳이 Inventory 가져올 필요 없이 getQuantity 로 int 반환하게 바로가져와도 되나?
            // 근데 확장성을 생각해서 나중에 재고 페이지 만드려면 이 메서드가 필요하긴 할 듯
        Inventory inventory = inventoryService.getInventory(productId);

        if(Objects.isNull(inventory)) {
            throw new InventoryNotFoundException(productId);
        }

        if(inventory.getInventoryQuantity() < quantity) {
            throw new RuntimeException("inventory quantity less than quantity");
        }

        // 회원의 포인트 < 결제금액
        User user = userService.getUser(userId);
        Product product = productService.getProduct(productId);

        if(Objects.isNull(product)) {
            throw new ProductNotFoundException(productId);
        }

        if(user.getUserPoint() < product.getpPrice()*quantity) {
            throw new RuntimeException(String.format("user point %d less than price %d", user.getUserPoint(), product.getpPrice()*quantity));
        }

        // ?? orderService.saveOrder(new Order(userId, productId, quantity)); vs orderService.saveOrder(userId, productId, quantity);
        orderService.saveOrder(new Order(userId, productId, quantity));

        user.setUserPoint(user.getUserPoint() - product.getpPrice()*quantity);
        userService.updateUser(user);

        // ?? 주문 수량 만큼 재고 감소

        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if(Objects.isNull(cart)) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        cart.removeItem(productId);

        return "redirect:/cart/cart.do";
    }

    public boolean isValid(String s) {
        return Objects.nonNull(s) && !s.trim().isEmpty();
    }
}
