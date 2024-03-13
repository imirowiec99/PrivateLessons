package pl.korpetycje.demo.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.korpetycje.demo.auth.AuthenticationRequest;
import pl.korpetycje.demo.auth.AuthenticationResponse;
import pl.korpetycje.demo.dto.LessonDto;
import pl.korpetycje.demo.dto.LessonDtoMapper;
import pl.korpetycje.demo.model.Access;
import pl.korpetycje.demo.model.Exercise;
import pl.korpetycje.demo.model.Lesson;
import pl.korpetycje.demo.model.User;
import pl.korpetycje.demo.repository.UserRepository;
import pl.korpetycje.demo.service.ExerciseService;
import pl.korpetycje.demo.service.LessonService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class MatPodst {

    private final LessonService lessonService;
    private final ExerciseService exerciseService;
    private final UserRepository userRepository;

    @Autowired
    public MatPodst(LessonService lessonService, ExerciseService exerciseService, UserRepository userRepository) {
        this.lessonService = lessonService;
        this.exerciseService = exerciseService;
        this.userRepository = userRepository;
    }

    @GetMapping("/matpodst")
    public String getLessons(Model model){
        List<String> enable = returnAccess();
        String firstname = activeUser();
        List<LessonDto> lessons = LessonDtoMapper.mapToLessonDtos(lessonService.getAllLessons());
        int size = lessons.size();
        model.addAttribute("enable", enable);
        model.addAttribute("username",firstname);
        model.addAttribute("size",size);
        model.addAttribute("lessons", lessons);
        return "matpodst";
    }

    private List<String> returnAccess(){
        List<String> enable = new ArrayList<>();
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser") {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List<Access> accesses = user.getAccess();
            for (Access access : accesses){
                enable.add(access.getMp());
                enable.add(access.getMr());
                enable.add(access.getFp());
                enable.add(access.getFr());
            }
        }
        return enable;
    }
    private String activeUser(){
        String firstname="";
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser") {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            firstname = user.getFirstname();
        }
        return firstname;
    }


    @GetMapping("/matpodst/{id}")
    public String showLesson(@PathVariable long id, Model model){
        List<String> enable = returnAccess();
        String firstname = activeUser();
        Lesson lesson = lessonService.getLesson(id);
        System.out.println(lesson);
        List<Exercise> exercises = lesson.getExercises();
        model.addAttribute("exercises", exercises);
        model.addAttribute("enable", enable);
        model.addAttribute("username",firstname);
        return "lesson";
    }

    @GetMapping("/matpodst/{id1}/exercise/remove/{id2}")
    public String removeExercise(@PathVariable long id1, @PathVariable long id2){
        exerciseService.deleteExercise(id2);
        return "redirect:/matpodst/edit/{id1}";
    }



//    @GetMapping("/add_exercise")
//    public String showFormExercise(Model model){
//        List<Exercise> exercises = exerciseService.getAllExercises();
//        List<ExerciseDto> exerciseDtos = exercises.stream()
//                        .map(exercise -> new ExerciseDto(exercise.getExerciseNumber(), exercise.getExerciseTopic(),exercise.getExerciseText(),exercise.getExerciseLink()))
//                        .collect(Collectors.toCollection(ArrayList::new));
//        model.addAttribute("exerciseDtos", exerciseDtos);
//        model.addAttribute("exerciseDto",new ExerciseDto());
//        return "add_exercise";
//    }


//    @GetMapping("/zadania")
//    public String zadania(Model model){
//        List<Exercise> exercises = exerciseService.getAllExercises();
//        List<ExerciseDto> exerciseDtos = exercises.stream()
//                .map(exercise -> new ExerciseDto(exercise.getExerciseNumber(), exercise.getExerciseTopic(),exercise.getExerciseText(),exercise.getExerciseLink()))
//                .collect(Collectors.toCollection(ArrayList::new));
//        model.addAttribute("exerciseDtos",exerciseDtos);
//        return "zadania";
//    }
//
//    @GetMapping("/edit_exercise")
//    public String editExercise(@RequestParam long exerciseId){
//        ExerciseDto exerciseDto = new ExerciseDto();
//        return
//    }
//
//    @PostMapping("add_exercise")
//    public String addExercise(@ModelAttribute("exerciseDto") ExerciseDto exerciseDto){
//        Exercise exercise = new Exercise();
//        exercise.setExerciseNumber(exerciseDto.getExerciseNumber());
//        exercise.setExerciseTopic(exerciseDto.getExerciseTopic());
//        exercise.setExerciseText(exerciseDto.getExerciseText());
//        exercise.setExerciseLink(exerciseDto.getExerciseLink());
//        exerciseService.addExercise(exercise);
//        return "redirect:/matpodst";
//
//    }
//
    @GetMapping("/matpodst/delete/{id}")
    public String removeLesson(@PathVariable long id){
        lessonService.deleteLesson(id);
        return "redirect:/matpodst";
    }
//
//    @PostMapping("/matpodst/edit/{id}")
//    public void updateLesson(@PathVariable("id") Long id, @ModelAttribute("lesson") LessonDto editedLesson){
//        Lesson lesson = lessonService.getLesson(id);
//        lesson.setLessonNumber(editedLesson.getLessonNumber());
//        lesson.setLessonTopic(editedLesson.getLessonTopic());
//        lesson.setLessonText(editedLesson.getLessonText());
//        lesson.setLessonLevel(editedLesson.getLessonLevel());
//        lessonService.saveLesson(lesson);
//    }

    @GetMapping("/matpodst/edit/{id}")
    public String listExercisesOfLesson(@PathVariable long id, Model model){
        Lesson lesson = lessonService.getLesson(id);
        List<Exercise> exercises = lesson.getLessonExercises();
        List<Exercise> sortedExercises = exercises.stream()
                        .sorted(((o1, o2) -> o1.getExerciseNumber() - o2.getExerciseNumber()))
                        .collect(Collectors.toList());
        model.addAttribute("exercises",sortedExercises);
        model.addAttribute("id",id);
        return "editlesson";
    }



//
//    @GetMapping("/matpodst/edit/{id}")
//    public String getLesson(@PathVariable long id, Model model){
//
//        Lesson lesson = lessonService.getLesson(id);
//        System.out.println(lesson);
//        List<Exercise> exercises = lesson.getExercises();
//        //List<ExerciseDto> exercises = ExerciseDtoMapper.mapToExerciseDtos(lessonService.getLessonExercise(id));
//        model.addAttribute("exercises", exercises);
//        return "lesson";
//
//        List<Exercise> exercises = lessonService.getLesson(id).getLessonExercises();
//        Lesson lesson = lessonService.getLesson(id);
//
//        List<ExerciseDto> exerciseDtos = exercises.stream()
//                        .map(exercise -> new ExerciseDto(exercise.getExerciseNumber(), exercise.getExerciseTopic(),
//                                exercise.getExerciseText(), exercise.getExerciseLink()))
//                                .collect(Collectors.toCollection(ArrayList::new));
//
//        LessonDto lessonDto = new LessonDto(lesson.getLessonNumber(), lesson.getLessonTopic(), lesson.getLessonText(), lesson.getLessonLevel(), exerciseDtos);
//        System.out.println(lessonDto.getExerciseDtos());
//        System.out.println(lessonDto);
//        model.addAttribute("lessonDto",lessonDto);
        //return "edit_lesson";
  //  }
//
//    @PostMapping("/matpodst/edit/{id}/submit")
//    public String saveLesson(@PathVariable long id, @ModelAttribute("lessonDto") LessonDto lessonDto){
//        List<Exercise> exercises = new ArrayList<>();
//        List<ExerciseDto> exerciseDtos = lessonDto.getExerciseDtos();
//        for (ExerciseDto exerciseDto : exerciseDtos){
//            Exercise exercise = new Exercise();
//            exercise.setExerciseNumber(exerciseDto.getExerciseNumber());
//            exercise.setExerciseLink(exerciseDto.getExerciseLink());
//            exercise.setExerciseText(exerciseDto.getExerciseText());
//            exercise.setExerciseTopic(exerciseDto.getExerciseTopic());
//            exercises.add(exercise);
//        }
//
//        List<Exercise> exercisesDatabase = exerciseService.getAllExercises();
//        for (Exercise exercise : exercisesDatabase){
//            for (Exercise exercise1 : exercises){
//                if (exercise.getExerciseNumber() == exercise1.getExerciseNumber()){
//                    exercise.setExerciseTopic(exercise1.getExerciseTopic());
//                    exercise.setExerciseText(exercise1.getExerciseText());
//                    exercise.setExerciseLink(exercise1.getExerciseLink());
//                    exerciseService.updateExercise(exercise);
//                }
//            }
//        }
//
//        Lesson lesson = lessonService.getLesson(id);
//        lesson.setLessonNumber(lessonDto.getLessonNumber());
//        lesson.setLessonText(lessonDto.getLessonText());
//        lesson.setLessonLevel(lessonDto.getLessonLevel());
//        lesson.setLessonTopic(lessonDto.getLessonTopic());
//        System.out.println(lesson);
//        lessonService.updateLesson(lesson);
//
//        return "redirect:/matpodst";
//    }


}
