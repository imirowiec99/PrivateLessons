package pl.korpetycje.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.korpetycje.demo.auth.AuthenticationRequest;
import pl.korpetycje.demo.auth.AuthenticationResponse;
import pl.korpetycje.demo.auth.AuthenticationService;
import pl.korpetycje.demo.auth.RegisterRequest;

import pl.korpetycje.demo.model.User;
import pl.korpetycje.demo.repository.UserRepository;
import pl.korpetycje.demo.service.LessonService;
import pl.korpetycje.demo.service.UserService;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class LoginRegisterController {
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final LessonService lessonService;
    private final UserService userService;


    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("request", new AuthenticationRequest("",""));
        return "login";
    }

    @PostMapping("/login")
    public String loginExecute(@ModelAttribute("request") AuthenticationRequest request, HttpServletResponse response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(request);
        System.out.println(json);
        ResponseEntity<AuthenticationResponse> responseEntity = ResponseEntity.ok(authenticationService.authenticate(request));
        String jwtToken = Objects.requireNonNull(responseEntity.getBody()).getToken();
        System.out.println(jwtToken);
//        Optional<User> user = Optional.of(new User());
        Cookie cookie = new Cookie("jwtToken", jwtToken);
        cookie.setMaxAge(Integer.MAX_VALUE);
        response.addCookie(cookie);
//        if (responseEntity.hasBody()) {
//            user = userRepository.findByEmail(request.getEmail());
//        }
//        System.out.println(user);
        return "redirect:/matpodst";

    }


    @GetMapping("/log_out")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies){
            cookie.setMaxAge(0);
            System.out.println(cookie);
            response.addCookie(cookie);
        }
        return "redirect:/";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("request", new RegisterRequest("","","",""));
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("request") RegisterRequest request, HttpServletResponse response, Model model) throws JsonProcessingException {
        if (!userService.getEmails().contains(request.getEmail())) {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(request);
            ResponseEntity<AuthenticationResponse> responseEntity = ResponseEntity.ok(authenticationService.register(request));
            String jwtToken = Objects.requireNonNull(responseEntity.getBody()).getToken();
            System.out.println(jwtToken);
//            Optional<User> user = Optional.of(new User());
            Cookie cookie = new Cookie("jwtToken", jwtToken);
            cookie.setMaxAge(Integer.MAX_VALUE);
            response.addCookie(cookie);
            return "redirect:/matpodst";
        }
        else {
            model.addAttribute("registerError","Podano istniejący adres e-mail");
            return "register";
        }
//        if (responseEntity.hasBody()) {
//            user = userRepository.findByEmail(request.getEmail());
//        }
//        System.out.println(user);

    }
}
