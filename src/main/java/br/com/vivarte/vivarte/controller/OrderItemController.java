package br.com.vivarte.vivarte.controller;

import br.com.vivarte.vivarte.dto.OrderItem.OrderItemResponseDTO;
import br.com.vivarte.vivarte.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-items")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService
            orderItemService;

    @GetMapping("/order/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderItemResponseDTO>
    findByOrderId(
            @PathVariable Integer orderId
    ) {

        return orderItemService
                .findByOrderId(orderId);
    }
}
