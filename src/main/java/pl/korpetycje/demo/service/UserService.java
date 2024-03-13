package pl.korpetycje.demo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.korpetycje.demo.model.Role;
import pl.korpetycje.demo.model.User;
import pl.korpetycje.demo.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Role getUserRole(User user){
        return user.getRole();
    }

    public List<User> getAllUsers(){
         return userRepository.findAll();
    }

    public User getUser(long id){
        return userRepository.findById(id);
    }

    public List<String> getEmails(){
        List<User> users = userRepository.findAll();
        return  users.stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
    }
}
