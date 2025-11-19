package Controllers;

import com.company.jobank.Entities.User;
import com.company.jobank.Repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @PostMapping("/add")
    public void addUser(
            @RequestBody User user
    ){
        userRepository.save(user);
    }
}