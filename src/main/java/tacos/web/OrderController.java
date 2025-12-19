package tacos.web;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import tacos.data.OrderRepository;
import tacos.model.TacoOrder;
import tacos.model.User;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {
    private OrderRepository orderRepo;

    @Autowired
    public OrderController(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @GetMapping("current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder order,
                               Errors errors,
                               SessionStatus sessionStatus,
                               @AuthenticationPrincipal User user) {
        if (errors.hasErrors()) {
            return "orderForm";
        }

        order.setUser(user);

        log.info("Order submitted: {}", order);
        orderRepo.save(order);
        sessionStatus.setComplete();

        return "redirect:/";
    }

//    // Example: use PreAuthorize
//    @PreAuthorize("hasRole('ADMIN')")
//    public void deleteAllOrders() {
//        orderRepo.deleteAll();
//    }

//    // Example: use PostAuthorize
//    @PostAuthorize("hasRole('ADMIN') || " +
//            "returnObject?.user.username == authentication.name")
//    public TacoOrder getOrder(long id) {
//        return orderRepo.findById(id).orElse(null);
//    }
}
