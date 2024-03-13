package pl.korpetycje.demo.auth;


import jdk.jfr.consumer.RecordingStream;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.korpetycje.demo.configuration.JwtService;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

//    @PostMapping("/register")
//    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
//        return ResponseEntity.ok(authenticationService.register(request));
//    }


//    @PostMapping("/login")
//    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
//        return ResponseEntity.ok(authenticationService.authenticate(request));
//    }

}
