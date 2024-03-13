package pl.korpetycje.demo.demo;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.korpetycje.demo.dto.ExerciseDto;
import pl.korpetycje.demo.repository.ExerciseRepository;
import pl.korpetycje.demo.service.ExerciseService;

@RestController
@RequestMapping("/api/v1/demo-controller")
public class DemoController {


    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello from secured endpoint");
    }




}
