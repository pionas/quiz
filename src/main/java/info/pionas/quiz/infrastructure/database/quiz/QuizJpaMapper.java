package info.pionas.quiz.infrastructure.database.quiz;

import info.pionas.quiz.domain.quiz.api.Answer;
import info.pionas.quiz.domain.quiz.api.Question;
import info.pionas.quiz.domain.quiz.api.Quiz;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
interface QuizJpaMapper {

    Quiz map(QuizEntity quizEntity);

    QuizEntity map(Quiz quiz);

    Question map(QuestionEntity questionEntity);

    @Mapping(target = "quiz", ignore = true)
    QuestionEntity map(Question question);

    Answer map(AnswerEntity answerEntity);

    @Mapping(target = "questionEntity", ignore = true)
    AnswerEntity map(Answer answer);

    @AfterMapping
    default void afterQuizToQuizEntityMapping(@MappingTarget QuizEntity quizEntity) {
        quizEntity.getQuestions().forEach(questionEntity -> questionEntity.setQuiz(quizEntity));
    }

    @AfterMapping
    default void afterQuestionToQuestionEntityMapping(@MappingTarget QuestionEntity questionEntity) {
        questionEntity.getAnswers().forEach(answerEntity -> answerEntity.setQuestionEntity(questionEntity));
    }
}
