package develhope.exercise.Unit.Test.services;

import develhope.exercise.Unit.Test.controllers.UserController;
import develhope.exercise.Unit.Test.entities.User;
import develhope.exercise.Unit.Test.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User createUser(User user) {
        if (user.getEmail() == null) return null;
        return userRepository.saveAndFlush(user);
    }

    public Optional<User> readSingleUser(Long id) {
        return userRepository.findById(id);
    }

    public List<User> readAllUser() {
        return userRepository.findAll();
    }

    public User updateName(Long id, String newName) {
        if (userRepository.findById(id).isEmpty()) return null;
        Optional<User> user = userRepository.findById(id);
        user.get().setName(newName);
        return userRepository.save(user.get());
    }

    public User updateSurname(Long id, String newSurname) {
        if (userRepository.findById(id).isEmpty()) return null;
        Optional<User> user = userRepository.findById(id);
        user.get().setSurname(newSurname);
        return userRepository.save(user.get());
    }

    public User updateEmail(Long id, String newEmail) {
        if (newEmail == null || userRepository.findById(id).isEmpty()) return null;
        Optional<User> user = userRepository.findById(id);
        user.get().setEmail(newEmail);
        return userRepository.save(user.get());
    }
    public User updateAll(Long id, User newUser) {
        if (newUser.getEmail() == null || userRepository.findById(id).isEmpty()) return null;
        Optional<User> user = userRepository.findById(id);
        user.get().setName(newUser.getName());
        user.get().setSurname(newUser.getSurname());
        user.get().setEmail(newUser.getEmail());
        return userRepository.save(user.get());
    }
    public  void deleteUser(Long id) {
        if(userRepository.findById(id).isPresent()) userRepository.delete(userRepository.findById(id).get());
    }
}
