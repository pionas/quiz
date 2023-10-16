package info.pionas.quiz.infrastructure.quiz;

import info.pionas.quiz.domain.quiz.QuizMapper;
import info.pionas.quiz.domain.quiz.QuizService;
import info.pionas.quiz.domain.quiz.api.*;
import info.pionas.quiz.domain.shared.UuidGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class QuizServiceTest {

    private final UuidGenerator uuidGenerator = Mockito.mock(UuidGenerator.class);
    private final QuizMapper quizMapper = new QuizMapperImpl();

    private final QuizService service = new QuizServiceImpl(uuidGenerator, quizMapper);

    @Test
    void should_create_quiz() {
        //given
        final var quizId = UUID.fromString("b83d5c22-7b78-4435-9daa-17bb532c0f63");
        final var questionId = UUID.fromString("435aeee2-5a1f-4723-9359-1137ed820ae7");
        final var answer1Id = UUID.fromString("d7e876ae-364d-403e-b537-7bcb2c2841fa");
        final var answer2Id = UUID.fromString("45d074e4-7c03-4c8b-9f8a-23ba1e367bce");
        when(uuidGenerator.generate()).thenReturn(quizId, questionId, answer1Id, answer2Id);
        final var newQuiz = new NewQuiz("Title", "Description", List.of(new NewQuestion("Spring is the best JAVA framework", List.of(new NewAnswer("Yes", true), new NewAnswer("No", false)))));
        //when
        final var quiz = service.createQuiz(newQuiz);
        //then
        assertThat(quiz).isNotNull();
        assertThat(quiz.getId()).isEqualTo(quizId);
        assertThat(quiz.getTitle()).isEqualTo("Title");
        assertThat(quiz.getDescription()).isEqualTo("Description");
        List<Question> questions = quiz.getQuestions();
        assertThat(questions).isNotEmpty();
        final var question = questions.get(0);
        assertThat(question).isNotNull();
        assertThat(question.getId()).isEqualTo(questionId);
        assertThat(question.getContent()).isEqualTo("Spring is the best JAVA framework");
        List<Answer> answers = question.getAnswers();
        assertThat(answers).isNotEmpty();
        final var answer1 = answers.get(0);
        assertThat(answer1).isNotNull();
        assertThat(answer1.getId()).isEqualTo(answer1Id);
        assertThat(answer1.getContent()).isEqualTo("Yes");
        assertThat(answer1.isCorrect()).isTrue();
        final var answer2 = answers.get(1);
        assertThat(answer2).isNotNull();
        assertThat(answer2.getId()).isEqualTo(answer2Id);
        assertThat(answer2.getContent()).isEqualTo("No");
        assertThat(answer2.isCorrect()).isFalse();
    }
}
