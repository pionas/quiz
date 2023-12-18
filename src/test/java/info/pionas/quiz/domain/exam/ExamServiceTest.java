package info.pionas.quiz.domain.exam;

import info.pionas.quiz.domain.exam.api.*;
import info.pionas.quiz.domain.quiz.QuizNotFoundException;
import info.pionas.quiz.domain.quiz.api.*;
import info.pionas.quiz.domain.shared.DateTimeProvider;
import info.pionas.quiz.domain.shared.UuidGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class ExamServiceTest {

    private final QuizRepository quizRepository = Mockito.mock(QuizRepository.class);
    private final ExamRepository examRepository = Mockito.mock(ExamRepository.class);
    private final ExamAnswerRepository examAnswerRepository = Mockito.mock(ExamAnswerRepository.class);
    private final QuizAnswerRepository quizAnswerRepository = Mockito.mock(QuizAnswerRepository.class);
    private final UuidGenerator uuidGenerator = Mockito.mock(UuidGenerator.class);
    private final DateTimeProvider dateTimeProvider = Mockito.mock(DateTimeProvider.class);
    private final EndExamValidator endExamValidator = new EndExamValidator(List.of(new EndExamQuizExistValidationRule(quizRepository), new EndExamAnswerForAllQuestionValidationRule(quizRepository)));
    private final ExamService service = new ExamServiceImpl(examRepository, examAnswerRepository, quizAnswerRepository, endExamValidator, uuidGenerator, dateTimeProvider);

    @Test
    void should_throw_not_found_exception_when_quiz_by_id_not_exist() {
        //given
        final var quizId = UUID.fromString("b83d5c22-7b78-4435-9daa-17bb532c0f63");
        final var answers = Collections.<PassExamAnswer>emptyList();
        when(quizRepository.existById(quizId)).thenReturn(false);
        //when
        QuizNotFoundException exception = assertThrows(QuizNotFoundException.class, () -> service.endExam("username", quizId, answers));
        //then
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage())
                .isEqualTo(String.format("Quiz by ID %s not exist", quizId));
        Mockito.verify(quizRepository, times(0)).findById(quizId);
    }

    @Test
    void should_throw_answer_for_question_not_found_exception_when_question_has_not_answer() {
        //given
        final var quizId = UUID.fromString("b83d5c22-7b78-4435-9daa-17bb532c0f63");
        final var answers = Collections.<PassExamAnswer>emptyList();
        when(quizRepository.existById(quizId)).thenReturn(true);
        when(quizRepository.findById(quizId)).thenReturn(Optional.of(getQuiz(quizId)));
        //when
        AnswerForQuestionNotFoundException exception = assertThrows(AnswerForQuestionNotFoundException.class, () -> service.endExam("username", quizId, answers));
        //then
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage())
                .isEqualTo("There is no answer to the question 03df4c0e-a760-4b23-aebe-ac0fd8761804");
    }

    @Test
    void should_end_exam() {
        //given
        final var examResultId = UUID.fromString("7a398eb6-1d20-4a05-b13b-c752c3c7c5d3");
        final var quizId = UUID.fromString("b83d5c22-7b78-4435-9daa-17bb532c0f63");
        final var answers = getAnswers();
        when(quizRepository.existById(quizId)).thenReturn(true);
        when(quizRepository.findById(quizId)).thenReturn(Optional.of(getQuiz(quizId)));
        when(uuidGenerator.generate()).thenReturn(examResultId);
        when(examRepository.getById(examResultId)).thenReturn(Optional.of(createExamResult(examResultId, getQuiz(quizId), answers)));
        //when
        final var examResult = service.endExam("username", quizId, answers);
        //then
        assertThat(examResult).isNotNull();
        assertThat(examResult).isEqualTo(examResultId);
        Mockito.verify(examRepository, times(1)).save(Mockito.any());
        Mockito.verify(examAnswerRepository, times(1)).saveAll(Mockito.any());
    }

    @Test
    void should_return_exam_by_id() {
        //given
        final var examResultId = UUID.fromString("7a398eb6-1d20-4a05-b13b-c752c3c7c5d3");
        final var quizId = UUID.fromString("b83d5c22-7b78-4435-9daa-17bb532c0f63");
        final var answers = getAnswers();
        final var quiz = getQuiz(quizId);
        when(examRepository.getById(examResultId)).thenReturn(Optional.of(createExamResult(examResultId, quiz, answers)));
        //when
        final var examResult = service.getExamDetails(examResultId);
        //then
        assertThat(examResult).isNotNull();
        assertThat(examResult.getId()).isEqualTo(examResultId);
        assertThat(examResult.getUsername()).isEqualTo("username");
        assertThat(examResult.getCorrectAnswer()).isEqualTo(1);
        final var quizResult = examResult.getQuiz();
        assertThat(quizResult).isNotNull();
        assertThat(quizResult.getId()).isEqualTo(quiz.getId());
        assertThat(quizResult.getDescription()).isEqualTo(quiz.getDescription());
        assertThat(quizResult.getQuestions()).isEqualTo(quiz.getQuestions());
    }

    @Test
    void should_throw_exam_not_found_exception_when_exam_by_id_not_exist() {
        //given
        final var examResultId = UUID.fromString("7a398eb6-1d20-4a05-b13b-c752c3c7c5d3");
        when(examRepository.getById(examResultId)).thenReturn(Optional.empty());
        //when
        ExamResultNotFoundException exception = assertThrows(ExamResultNotFoundException.class, () -> service.getExamDetails(examResultId));
        //then
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage())
                .isEqualTo("Exam result by ID 7a398eb6-1d20-4a05-b13b-c752c3c7c5d3 not exist");
    }

    private Quiz getQuiz(UUID quizId) {
        final var questionId1 = UUID.fromString("03df4c0e-a760-4b23-aebe-ac0fd8761804");
        final var questionId2 = UUID.fromString("0feb6e91-a25c-4c9a-8f6c-acf489a5af6b");
        final var answerId1 = UUID.fromString("1f12ef49-9a35-45f2-9824-edb673602a7f");
        final var answerId2 = UUID.fromString("126b0faa-1de1-4b9e-b79c-8b03361e8839");
        final var answerId3 = UUID.fromString("9f5e41ad-9614-4a14-ac8a-dad55d4ce89f");
        final var answerId4 = UUID.fromString("5e02b9e3-c1d4-43da-b7df-45899da5b9a2");
        final var content1 = "Content 1";
        final var content2 = "Content 2";
        final var content3 = "Content 3";
        final var content4 = "Content 4";

        final var question1 = Question.builder()
                .id(questionId1)
                .content("Spring is the best JAVA framework")
                .answers(new ArrayList<>(Arrays.asList(createAnswer(answerId1, content1, true), createAnswer(answerId2, content2, false))))
                .build();
        final var question2 = Question.builder()
                .id(questionId2)
                .content("What is the best Front End framework")
                .answers(new ArrayList<>(Arrays.asList(createAnswer(answerId3, content3, false), createAnswer(answerId4, content4, true))))
                .build();
        return Quiz.builder()
                .id(quizId)
                .title("Title")
                .description("Description")
                .questions(new ArrayList<>(Arrays.asList(question1, question2)))
                .build();
    }

    private Answer createAnswer(UUID id, String content, Boolean correct) {
        return Answer.builder()
                .id(id)
                .content(content)
                .correct(correct)
                .build();
    }

    private List<PassExamAnswer> getAnswers() {
        final var questionId1 = UUID.fromString("03df4c0e-a760-4b23-aebe-ac0fd8761804");
        final var answerId1 = UUID.fromString("1f12ef49-9a35-45f2-9824-edb673602a7f");
        final var questionId2 = UUID.fromString("0feb6e91-a25c-4c9a-8f6c-acf489a5af6b");
        final var answerId2 = UUID.fromString("9f5e41ad-9614-4a14-ac8a-dad55d4ce89f");
        return List.of(
                createPassExamAnswer(questionId1, answerId1),
                createPassExamAnswer(questionId2, answerId2)
        );
    }

    private PassExamAnswer createPassExamAnswer(UUID questionId, UUID answerId) {
        return PassExamAnswer.builder()
                .questionId(questionId)
                .answerId(answerId)
                .build();
    }

    private ExamResult createExamResult(UUID examResultId, Quiz quiz, List<PassExamAnswer> answers) {
        final var questions = quiz.getQuestions();
        return ExamResult.builder()
                .id(examResultId)
                .username("username")
                .quiz(quiz)
                .answers(List.of(
                        crateExamAnswer(questions.getFirst(), answers.getFirst()),
                        crateExamAnswer(questions.getLast(), answers.getLast())
                ))
                .build();
    }

    private ExamAnswer crateExamAnswer(Question question, PassExamAnswer passExamAnswer) {
        return ExamAnswer.of(question, passExamAnswer);
    }
}