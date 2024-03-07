package develhope.exercise.Unit.Test.controllers;

import develhope.exercise.Unit.Test.entities.User;
import develhope.exercise.Unit.Test.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/create")
    public User createUser(
            @RequestBody User user
    ) {
        return userService.createUser(user);
    }
    @GetMapping("/get-single/{id}")
    public Optional<User> getSingle(
            @PathVariable Long id
    ) {
        return userService.readSingleUser(id);
    }
    @GetMapping("/get-all")
    public List<User> getAll() {
        return userService.readAllUser();
    }
    @PatchMapping("/update/{id}/name")
    public User updateName(
            @PathVariable Long id,
            @RequestParam String name
    ) {
        return userService.updateName(id, name);
    }
    @PatchMapping("/update/{id}/surname")
    public User updateSurname(
            @PathVariable Long id,
            @RequestParam String surname
    ) {
        return userService.updateSurname(id, surname);
    }
    @PatchMapping("/update/{id}/email")
    public User updateEmail(
            @PathVariable Long id,
            @RequestParam String email
    ) {
        return userService.updateEmail(id, email);
    }
    @PatchMapping("/update/{id}")
    public User updateAll(
            @PathVariable Long id,
            @RequestBody User user
    ) {
        return userService.updateAll(id, user);
    }
    @DeleteMapping("/delete/{id}")
    public void delete(
            @PathVariable Long id
    ) {
        userService.deleteUser(id);
    }
}
