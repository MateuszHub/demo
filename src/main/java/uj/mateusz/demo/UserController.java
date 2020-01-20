package uj.mateusz.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/user/add")
    public @ResponseBody
    String addUser(@RequestParam String email, @RequestParam String name) {
        log.info("ASD " + email + " : " + name);
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        userRepository.save(user);
        return "Dodano pomyślnie";
    }

}
