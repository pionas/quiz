package info.pionas.quiz.domain.quiz;

import info.pionas.quiz.domain.quiz.api.*;
import info.pionas.quiz.domain.shared.UuidGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class QuizServiceTest {

    private final QuizRepository quizRepository = Mockito.mock(QuizRepository.class);
    private final UuidGenerator uuidGenerator = Mockito.mock(UuidGenerator.class);
    private final QuizMapper quizMapper = new QuizMapperImpl();

    private final QuizService service = new QuizServiceImpl(quizRepository, uuidGenerator, quizMapper);

    @BeforeEach
    void setUp() {
        when(quizRepository.save(any(Quiz.class))).thenAnswer((InvocationOnMock invocationOnMock) -> invocationOnMock.getArguments()[0]);
    }

    @Test
    void should_create_quiz() {
        //given
        final var quizId = UUID.fromString("b83d5c22-7b78-4435-9daa-17bb532c0f63");
        final var questionId = UUID.fromString("435aeee2-5a1f-4723-9359-1137ed820ae7");
        final var answer1Id = UUID.fromString("d7e876ae-364d-403e-b537-7bcb2c2841fa");
        final var answer2Id = UUID.fromString("45d074e4-7c03-4c8b-9f8a-23ba1e367bce");
        when(uuidGenerator.generate()).thenReturn(answer1Id, answer2Id, questionId, quizId);
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

    @Test
    void should_add_question_to_quiz() {
        //given
        final var quizId = UUID.fromString("b83d5c22-7b78-4435-9daa-17bb532c0f63");
        final var questionId = UUID.fromString("435aeee2-5a1f-4723-9359-1137ed820ae7");
        final var answer1Id = UUID.fromString("d7e876ae-364d-403e-b537-7bcb2c2841fa");
        final var answer2Id = UUID.fromString("45d074e4-7c03-4c8b-9f8a-23ba1e367bce");
        final var newQuestion = new NewQuestion("Spring is the best JAVA framework", List.of(new NewAnswer("Yes", true), new NewAnswer("No", false)));
        when(uuidGenerator.generate()).thenReturn(answer1Id, answer2Id, questionId);
        when(quizRepository.findById(quizId)).thenReturn(Optional.of(getQuiz(quizId)));
        //when
        Quiz quiz = service.addQuestionToQuiz(quizId, newQuestion);
        //then
        assertThat(quiz).isNotNull();
        assertThat(quiz.getId()).isEqualTo(quizId);
        assertThat(quiz.getTitle()).isEqualTo("Title");
        assertThat(quiz.getDescription()).isEqualTo("Description");
        List<Question> questions = quiz.getQuestions();
        assertThat(questions).isNotEmpty();
        assertThat(questions).hasSize(1);
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

    @Test
    void should_throw_not_found_exception_when_quiz_by_id_not_exist() {
        //given
        final var quizId = UUID.fromString("b83d5c22-7b78-4435-9daa-17bb532c0f63");
        final var newQuestion = new NewQuestion("Spring is the best JAVA framework", Collections.emptyList());
        when(quizRepository.findById(quizId)).thenReturn(Optional.empty());
        //when
        QuizNotFoundException exception = assertThrows(QuizNotFoundException.class, () -> service.addQuestionToQuiz(quizId, newQuestion));
        //then
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage())
                .isEqualTo(String.format("Quiz by ID %s not exist", quizId));
    }

    @Test
    void should_remove_question_from_quiz() {
        //given
        final var quizId = UUID.fromString("b83d5c22-7b78-4435-9daa-17bb532c0f63");
        final var questionId = UUID.fromString("435aeee2-5a1f-4723-9359-1137ed820ae7");
        final var answer1Id = UUID.fromString("d7e876ae-364d-403e-b537-7bcb2c2841fa");
        final var answer2Id = UUID.fromString("45d074e4-7c03-4c8b-9f8a-23ba1e367bce");
        final var questionIdToRemove = UUID.fromString("45d074e4-7c03-4c8b-9f8a-23ba1e367bce");
        when(uuidGenerator.generate()).thenReturn(questionId, answer1Id, answer2Id);
        when(quizRepository.findById(quizId)).thenReturn(Optional.of(getQuizWithQuestionAndAnswer(quizId, questionId, questionIdToRemove, answer1Id, answer2Id)));
        //when
        Quiz quiz = service.removeQuestionFromQuiz(quizId, questionIdToRemove);
        //then
        assertThat(quiz).isNotNull();
        assertThat(quiz.getId()).isEqualTo(quizId);
        assertThat(quiz.getTitle()).isEqualTo("Title");
        assertThat(quiz.getDescription()).isEqualTo("Description");
        List<Question> questions = quiz.getQuestions();
        assertThat(questions).hasSize(1);
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

    @Test
    void should_throw_not_found_exception_when_try_remove_question_and_quiz_by_id_not_exist() {
        //given
        final var quizId = UUID.fromString("b83d5c22-7b78-4435-9daa-17bb532c0f63");
        final var questionId = UUID.fromString("435aeee2-5a1f-4723-9359-1137ed820ae7");
        when(quizRepository.findById(quizId)).thenReturn(Optional.empty());
        //when
        QuizNotFoundException exception = assertThrows(QuizNotFoundException.class, () -> service.removeQuestionFromQuiz(quizId, questionId));
        //then
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage())
                .isEqualTo(String.format("Quiz by ID %s not exist", quizId));
    }

    private static Quiz getQuiz(UUID quizId) {
        return Quiz.builder()
                .id(quizId)
                .title("Title")
                .description("Description")
                .build();
    }

    private Quiz getQuizWithQuestionAndAnswer(UUID quizId, UUID questionId, UUID questionIdToRemove, UUID answer1Id, UUID answer2Id) {
        Answer answer1 = Answer.builder().id(answer1Id).content("Yes").correct(true).build();
        Answer answer2 = Answer.builder().id(answer2Id).content("No").build();
        final var answers = List.of(answer1, answer2);
        final var question1 = Question.builder()
                .id(questionId)
                .content("Spring is the best JAVA framework")
                .answers(answers)
                .build();
        final var question2 = Question.builder()
                .id(questionIdToRemove)
                .content("What is the best Front End framework")
                .build();
        return Quiz.builder()
                .id(quizId)
                .title("Title")
                .description("Description")
                .questions(new ArrayList<>(Arrays.asList(question1, question2)))
                .build();
    }
}
