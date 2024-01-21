package info.pionas.quiz.web.home;

import info.pionas.quiz.domain.quiz.api.QuizService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring6.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;

@Controller
@AllArgsConstructor
class HomeController {

    private final QuizService quizService;

    @RequestMapping("/")
    public String index(final Model model) {
        IReactiveDataDriverContextVariable reactiveDataDrivenMode =
                new ReactiveDataDriverContextVariable(quizService.getLastAdded(), 1);
        model.addAttribute("quizes", reactiveDataDrivenMode);
        return "home/index";
    }

}
