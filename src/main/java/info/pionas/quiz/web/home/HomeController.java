package info.pionas.quiz.web.home;

import info.pionas.quiz.domain.quiz.api.QuizService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
class HomeController {

    private final QuizService quizService;

    @RequestMapping("/")
    public String index(final Model model) {
        model.addAttribute("quizes", quizService.getLastAdded());
        return "home/index";
    }

}
