package id.iztechnology.boilerplate.infrastructure.adapters.http_server.user;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.iztechnology.boilerplate.application.service.UserService;
import id.iztechnology.boilerplate.domain.model.ApiResponse;
import id.iztechnology.boilerplate.infrastructure.adapters.http_server.user.dto.AddUserRequest;
import id.iztechnology.boilerplate.infrastructure.adapters.http_server.user.dto.GetUserListRequest;
import id.iztechnology.boilerplate.infrastructure.adapters.http_server.user.dto.UserResponse;
import id.iztechnology.boilerplate.infrastructure.adapters.http_server.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers(GetUserListRequest request) {
        List<UserResponse> userResponseList = userService.getAllUsers()
        .stream()
        .map(UserMapper.INSTANCE::toUserResponse)
        .toList();
        return ResponseEntity.status(200).body(ApiResponse.success(userResponseList));
    }

    @PostMapping("/users")
    public ResponseEntity<ApiResponse<Void>> createUser(AddUserRequest request) {
        // List<UserResponse> userResponseList = userService.createUser();

        return ResponseEntity.status(200).body(ApiResponse.success(null));
    }
}
