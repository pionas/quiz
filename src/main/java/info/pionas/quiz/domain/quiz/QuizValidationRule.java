package info.pionas.quiz.domain.quiz;

import info.pionas.quiz.domain.quiz.api.NewQuiz;

interface QuizValidationRule {

    void validate(NewQuiz newQuiz);
}
