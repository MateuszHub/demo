package uj.mateusz.demo;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uj.mateusz.demo.entitiy.Password;
import uj.mateusz.demo.entitiy.User;
import uj.mateusz.demo.repository.PasswordRepository;
import uj.mateusz.demo.repository.RolesRepository;
import uj.mateusz.demo.repository.UserRepository;
import uj.mateusz.demo.services.UserSecurityService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Controller
public class UserController {
    @Autowired
    UserSecurityService userSecurityService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordRepository passwordRepository;


    @CrossOrigin
    @PostMapping(path = "/user/login")
    public @ResponseBody
    String login(@RequestParam String username, @RequestParam String password) {
        User user = userRepository.findByName(username);
        if (user != null) {
            Optional<Password> pass = passwordRepository.findById(user.getId());
            if (pass.get().getPassword().equals(password)) {
                var auth = new UsernamePasswordAuthenticationToken(user.getName(), null, userSecurityService.getAuthorities(user.getId()));
                SecurityContextHolder.getContext().setAuthentication(auth);
                String token = userSecurityService.createJwtToken(username);
                return "{\"token\": \"" + token + "\"}";
            }
        }
        return "Error";
    }


    @CrossOrigin
    @GetMapping(path = "/user/info")
    public @ResponseBody
    String info(Authentication authentication,  @RequestHeader (name="Authorization") String token) {
        userSecurityService.authenticateToken(token);
        if(!userSecurityService.hasRole("admin")) return "";

        User user = userSecurityService.getLoggedUser();
        return "{\"name\": \"" + user.getName() + "\", \"email\": \""+ user.getEmail() + "\"}";
    }


    @PostMapping(path = "/user/add")
    public @ResponseBody
    String addUser(@RequestParam String email, @RequestParam String name) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        userRepository.save(user);
        return "Dodano pomyślnie";
    }

}
