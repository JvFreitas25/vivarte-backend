package br.com.vivarte.vivarte.controller;

import br.com.vivarte.vivarte.dto.Order.OrderRequestDTO;
import br.com.vivarte.vivarte.dto.Order.OrderResponseDTO;
import br.com.vivarte.vivarte.enums.OrderStatus;
import br.com.vivarte.vivarte.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDTO createOrder(
            @RequestBody OrderRequestDTO request
    ) {

        return orderService.createOrder(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponseDTO> findAll() {

        return orderService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponseDTO findById(
            @PathVariable Integer id
    ) {

        return orderService.findById(id);
    }

    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponseDTO> findByUserId(
            @PathVariable Integer userId
    ) {

        return orderService.findByUserId(userId);
    }

    @PatchMapping("/{orderId}/status")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponseDTO updateStatus(
            @PathVariable Integer orderId,
            @RequestParam OrderStatus status,
            @RequestParam Integer userId
    ) {

        return orderService.updateStatus(
                orderId,
                status,
                userId
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Integer id,
            @RequestParam Integer userId
    ) {

        orderService.delete(id, userId);
    }
}
