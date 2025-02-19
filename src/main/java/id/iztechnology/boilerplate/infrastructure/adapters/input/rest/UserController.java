package id.iztechnology.boilerplate.infrastructure.adapters.input.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.iztechnology.boilerplate.application.services.UserService;
import id.iztechnology.boilerplate.domain.model.ApiResponse;
import id.iztechnology.boilerplate.infrastructure.adapters.input.rest.dto.AddUserRequest;
import id.iztechnology.boilerplate.infrastructure.adapters.input.rest.dto.UserResponse;
import id.iztechnology.boilerplate.infrastructure.adapters.input.rest.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        List<UserResponse> userResponseList = userService.getAllUsers().stream().map(UserMapper.INSTANCE::toUserResponse).toList();
        log.info("anjg {}", userResponseList);
        return ResponseEntity.status(200).body(ApiResponse.success(userResponseList));
    }

    @PostMapping("/users")
    public ResponseEntity<ApiResponse<Void>> createUser(AddUserRequest request) {
        // List<UserResponse> userResponseList = userService.createUser();

        return ResponseEntity.status(200).body(ApiResponse.success(null));
    }
}
