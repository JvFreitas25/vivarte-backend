package br.com.vivarte.vivarte.service;

import br.com.vivarte.vivarte.dto.Login.LoginRequestDTO;
import br.com.vivarte.vivarte.dto.User.UserRequestDTO;
import br.com.vivarte.vivarte.dto.User.UserResponseDTO;
import br.com.vivarte.vivarte.entity.User;
import br.com.vivarte.vivarte.exception.BadRequestException;
import br.com.vivarte.vivarte.exception.NotFoundException;
import br.com.vivarte.vivarte.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final IUserRepository userRepository;

    public UserResponseDTO create(
            UserRequestDTO request
    ) {

        boolean emailExists =
                userRepository.existsByEmail(
                        request.email()
                );

        if (emailExists) {

            throw new BadRequestException(
                    "Email já cadastrado"
            );
        }

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(request.password())
                .role(request.role())
                .phone(request.phone())
                .createdAt(LocalDateTime.now())

                .build();

        userRepository.save(user);

        return toDto(user);
    }

    public List<UserResponseDTO> findAll() {

        return userRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public UserResponseDTO findById(Integer id) {

        return userRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Usuário não encontrado"
                        ));
    }

    public UserResponseDTO findByEmail(
            String email
    ) {

        return userRepository.findByEmail(email)
                .map(this::toDto)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Usuário não encontrado"
                        ));
    }

    public UserResponseDTO login(LoginRequestDTO request) {
        User user = userRepository
                .findByEmail(request.email())
                .orElseThrow(() ->
                        new NotFoundException("Usuário não encontrado"));

        if (!user.getPassword().equals(request.password())) {
            throw new BadRequestException("Senha inválida");
        }

        return toDto(user);
    }

    public void delete(Integer id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Usuário não encontrado"
                        ));

        userRepository.delete(user);
    }

    private UserResponseDTO toDto(User user) {

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getRole()
        );
    }
}
