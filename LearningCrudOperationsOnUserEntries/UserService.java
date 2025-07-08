package com.example.demo;

import com.example.demo.User.UserEntity;
import com.example.demo.exception.UserNotFoundException;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository repo;
    public UserService(UserRepository repo){
        this.repo=repo;
    }
    public UserEntity createUser(UserEntity user){
        return repo.save(user);
    }
    public List<UserEntity> getAllUsers(){
        return repo.findAll();
    }
    public UserEntity getUserById(String id){
        return repo.findById(id).orElse(null);
    }
    public void deleteUser(String id){
        repo.deleteById(id);
    }
    public Page<UserEntity> getUsersPaginated(Pageable pageable){
        return repo.findAll(pageable);
    }
    public List<UserEntity> filterByName(String name) {
        return repo.findByNameContainingIgnoreCase(name);
    }
    public UserEntity updateUser(String id, UserEntity updatedUser) {
        UserEntity user = repo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.setName(updatedUser.getName());
        user.setAge(updatedUser.getAge());
        return repo.save(user);
    }

    public UserEntity updateUserAge(String id, int age) {
        UserEntity user = repo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.setAge(age);
        return repo.save(user);
    }


}
