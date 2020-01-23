package uj.mateusz.demo.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;
import uj.mateusz.demo.entitiy.User;
import uj.mateusz.demo.repository.PasswordRepository;
import uj.mateusz.demo.repository.RolesRepository;
import uj.mateusz.demo.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Scope("singleton")
public class UserSecurityService {

    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordRepository passwordRepository;
    private String secret;
    private int expTime;

    public UserSecurityService(@Value("${jwt.expirationTime}") int expirationTime,
                               @Value("${jwt.secret}") String secret) {
        this.expTime = expirationTime;
        this.secret = secret;
    }

    public void authenticateToken(String token) {
        System.out.println(token);
        String userName = JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token.replace("Baerer ", ""))
                .getSubject();
        if (userName != null) {
            User userDetails = userRepository.findByName(userName);
            if (userDetails != null) {
                var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getName(), null, getAuthorities(userDetails.getId()));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
    }

    public Collection<String> getRoles(Integer id) {
//        List<String> list = new LinkedList<>();
//        rolesRepository.findAll().forEach(roles -> {if(roles.getUser_id() == id) list.add(roles.getRole())});
        return rolesRepository.findRolesById(id).stream().map(roles -> roles.getRole()).collect(Collectors.toList());
    }

    public boolean hasRole(String role) {
        User user = getLoggedUser();
        if(user != null)
                return rolesRepository.findRolesById(user.getId()).stream().map(roles -> roles.getRole()).collect(Collectors.toList()).contains(role);
        return false;
    }

    public Collection<GrantedAuthority> getAuthorities(Integer id) {
        var roles = this.getRoles(id);
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        roles.stream().forEach(role -> {
            GrantedAuthority grantedAuthority = new GrantedAuthority() {
                public String getAuthority() {
                    return role;
                }
            };
            grantedAuthorities.add(grantedAuthority);
        });

        return grantedAuthorities;
    }

    public User getLoggedUser(){
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            var username = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.findByName((String) username);
            if (user != null) {
                return user;
            }
        }
        return null;
    }

    public String createJwtToken(String subject) {
        String token = JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + expTime))
                .sign(Algorithm.HMAC256(secret));
        return token;
    }
}
