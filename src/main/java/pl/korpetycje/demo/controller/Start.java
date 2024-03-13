package pl.korpetycje.demo.controller;

import jakarta.validation.Valid;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.korpetycje.demo.auth.AuthenticationService;
import pl.korpetycje.demo.dto.*;
import pl.korpetycje.demo.model.*;
import pl.korpetycje.demo.repository.ContactRepository;
import pl.korpetycje.demo.service.*;

import java.util.List;
import java.util.stream.Collectors;


@Controller
public class Start {

    private final LessonService lessonService;
    private final ExerciseService exerciseService;
    private final AuthenticationService authenticationService;
    private final MailService mailService;
    private final ContactService contactService;
    private final UserService userService;

    public Start(LessonService lessonService, ExerciseService exerciseService, AuthenticationService authenticationService,
                 MailService mailService, ContactService contactService, UserService userService) {
        this.lessonService = lessonService;
        this.exerciseService = exerciseService;
        this.authenticationService = authenticationService;
        this.mailService = mailService;
        this.contactService = contactService;
        this.userService = userService;
    }

    @GetMapping("/users")
    public String getAllUsers(Model model){
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/send")
    public String mailSite(Model model){
        Mail mail = new Mail();
        model.addAttribute("mail",mail);
        return "mailform";
    }

    @GetMapping("/send/{id}")
    public String mailSite(@PathVariable long id, Model model){
        User user = userService.getUser(id);
        Mail mail = new Mail();
        model.addAttribute("mail",mail);
        model.addAttribute("user", user);
        return "mailformtouser";
    }

    @PostMapping("/send/{id}")
    public String mailSend(@ModelAttribute("mail") Mail mail, @PathVariable long id){
        System.out.println(mail);
        String email = userService.getUser(id).getEmail();
        mailService.sendMail(email, mail);
        return "redirect:/";
    }

    @PostMapping("/send")
    public String sendMail (@ModelAttribute("mail") Mail mail){
        System.out.println(mail);
        String email = "marta.baj297@wp.pl";
        mailService.sendMail(email, mail);
        return "redirect:/";
    }

    @GetMapping("/")
    public String mainPage(Model model){
        ContactDto contactDto = new ContactDto();
        model.addAttribute("contactDto", contactDto);
        return "helloworld";
    }


    @PostMapping("/send_form")
    public String sendContactForm(@ModelAttribute("contactDto") ContactDto contactDto){
        Contact contact = ContactToEntity.contactDtoToEntity(contactDto);
        contactService.addContact(contact);
        String newLine = System.lineSeparator();
        Mail mail = new Mail();
        mail.setSubject("Dane z formularza zgłoszeniowego");
        mail.setMessage("Imię i nazwisko: " + contact.getName() + newLine +
                "Adres email: " + contact.getEmail() + newLine +
                "Numer telefonu: " + contact.getPhone() + newLine +
                "Wiadomość: " + contact.getMessage());
        String email = "przepawel1@gmail.com";
        mailService.sendMail(email, mail);
        return "redirect:/";
    }


    @GetMapping("/add_lesson")
    public String showFormLesson(Model model){
        int number = lessonService.getAllLessons().size();
        model.addAttribute("lessonDto", new LessonDto(number,number+1,"","",""));
        return "add_lesson";
    }

    @PostMapping("/add_lesson")
    public String addLesson(@ModelAttribute("lessonDto") @Valid LessonDto lessonDto, Model model, BindingResult result){
        if (result.hasErrors()) {
            return "add_lesson";
        }
        Lesson lesson = LessonToEntity.lessonDtoToEntity(lessonDto);
        lessonService.saveLessonIfNotExist(lesson);
        return "redirect:/";
    }

    @GetMapping("/exercises")
    public String listExercises(Model model){
        List<Exercise> exercises = exerciseService.getAllExercises();
        List<Exercise> exercisesAfterSort = exercises.stream()
                        .sorted(((o1, o2) -> o1.getExerciseNumber() - o2.getExerciseNumber()))
                        .collect(Collectors.toList());
        model.addAttribute("exercises",exercisesAfterSort);
        return "exercise_list";
    }

    @GetMapping("/exercise/edit/{id}")
    public String editExercise(@PathVariable long id, Model model){
        Exercise exercise = exerciseService.getExercise(id);
        System.out.println(exercise);
        ExerciseDto exerciseDto = ExerciseDtoMapper.mapToExerciseDto(exercise);
        model.addAttribute("exerciseDto",exerciseDto);
        System.out.println(exerciseDto);
        return "editexercise";
    }

    @PostMapping("/exercise/edit/{id}/submit")
    public String updateExercise(@PathVariable long id, @ModelAttribute("exerciseDto") ExerciseDto exerciseDto, Model model){
        Exercise exercise = ExerciseToEntity.exerciseDtoToEntity(exerciseDto);
        System.out.println(exercise);
        exerciseService.updateExercise(exercise);
        return "redirect:/exercises";
    }

    @GetMapping("/exercise/remove/{id}")
    public String removeExercise(@PathVariable long id){
        exerciseService.deleteExercise(id);
        return "redirect:/exercises";
    }

    @GetMapping("/add_exercise")
    public String addExerciseGet(Model model){
        int size = exerciseService.getAllExercises().size();
        System.out.println(size);
        model.addAttribute("size", size);
        model.addAttribute("exerciseDto", new ExerciseDto(size, size+1,"","",""));
        return "addexercise";
    }

    @PostMapping("/add_exercise")
    public String addExercisePost(@ModelAttribute("exerciseDto") ExerciseDto exerciseDto, Model model){
        Exercise exercise = ExerciseToEntity.exerciseDtoToEntity(exerciseDto);
        System.out.println(exercise);
        exerciseService.addExercise(exercise);
        return "redirect:/exercises";
    }

}
