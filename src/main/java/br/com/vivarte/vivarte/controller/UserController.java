package br.com.vivarte.vivarte.controller;

import br.com.vivarte.vivarte.dto.User.UserRequestDTO;
import br.com.vivarte.vivarte.dto.User.UserResponseDTO;
import br.com.vivarte.vivarte.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO create(
            @Valid @RequestBody UserRequestDTO request
    ) {

        return userService.create(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseDTO> findAll() {

        return userService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDTO findById(
            @PathVariable Integer id
    ) {

        return userService.findById(id);
    }

    @GetMapping("/email")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDTO findByEmail(
            @RequestParam String email
    ) {

        return userService.findByEmail(email);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Integer id
    ) {

        userService.delete(id);
    }
}
