package com.example.demo;

import com.example.demo.User.UserEntity;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HelloController {

    public UserService userService;

    public HelloController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/user")
    public ResponseEntity<UserEntity> createUser(@Valid @RequestBody UserEntity user){
        return ResponseEntity.ok(userService.createUser((user)));
    }
    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable String id){
        return ResponseEntity.ok(userService.getUserById(id));
    }
    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id){
        userService.deleteUser(id);
        return ResponseEntity.ok("Deleted User with ID"+id);
    }
    @GetMapping("/users/page")
    public ResponseEntity<Page<UserEntity>> getUsersWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<UserEntity> pagedUsers = userService.getUsersPaginated(pageable);
        return ResponseEntity.ok(pagedUsers);
    }
    @GetMapping("/users/search")
    public ResponseEntity<List<UserEntity>> filterUsers(@RequestParam String name) {
        return ResponseEntity.ok(userService.filterByName(name));
    }
    @PutMapping("/user/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable String id, @Valid @RequestBody UserEntity user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @PatchMapping("/user/{id}/age")
    public ResponseEntity<UserEntity> updateAge(@PathVariable String id, @RequestParam int age) {
        return ResponseEntity.ok(userService.updateUserAge(id, age));
    }

}
