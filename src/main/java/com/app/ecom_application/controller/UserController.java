package com.app.ecom_application.controller;

import com.app.ecom_application.dto.UserRequest;
import com.app.ecom_application.dto.UserResponse;
import com.app.ecom_application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUser(){
        return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUSer (@PathVariable Long id){
        return userService.fetchUser(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> addUser (@RequestBody UserRequest userRequest){
        userService.addUser(userRequest);
        return ResponseEntity.ok("Usuario adicionado com sucesso");
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updatedUser (@PathVariable Long id, @RequestBody UserRequest userRequest) {

        boolean updated = userService.UpdateUser(id, userRequest);

        if (updated) return ResponseEntity.ok("Feito o updated com sucesso");

        return ResponseEntity.notFound().build();
    }

    }

