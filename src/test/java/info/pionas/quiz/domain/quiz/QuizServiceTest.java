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

    @Test
    void should_update_question_from_quiz() {
        //given
        final var quizId = UUID.fromString("b83d5c22-7b78-4435-9daa-17bb532c0f63");
        final var questionId = UUID.fromString("435aeee2-5a1f-4723-9359-1137ed820ae7");
        final var questionIdToUpdate = UUID.fromString("45d074e4-7c03-4c8b-9f8a-23ba1e367bce");
        final var quizWithQuestionsAndAnswers = getQuizWithQuestionsAndAnswers(quizId, questionId, questionIdToUpdate);
        final var quizSecondQuestionFirstAnswerId = UUID.fromString("9f5e41ad-9614-4a14-ac8a-dad55d4ce89f");
        final var newAnswerId1 = UUID.fromString("9f46a57f-acbb-4e76-9db8-69c8c5a08a10");
        final var newAnswerId2 = UUID.fromString("a9f950f8-c12f-4c22-83a4-0a4758fbad0a");
        final var newAnswers = List.of(createUpdateAnswer(quizSecondQuestionFirstAnswerId, "content3", false), createUpdateAnswer(newAnswerId1, "New answer 1", false), createUpdateAnswer(null, "New answer 2", true));
        final var updateQuestion = new UpdateQuestion(questionIdToUpdate, "new content", newAnswers);
        when(uuidGenerator.generate()).thenReturn(questionId, newAnswerId2);
        when(quizRepository.findById(quizId)).thenReturn(Optional.of(quizWithQuestionsAndAnswers));
        //when
        final var quiz = service.updateQuestionFromQuiz(quizId, updateQuestion);
        //then
        assertThat(quiz).isNotNull();
        assertThat(quiz.getId()).isEqualTo(quizId);
        assertThat(quiz.getTitle()).isEqualTo("Title");
        assertThat(quiz.getDescription()).isEqualTo("Description");
        List<Question> questions = quiz.getQuestions();
        assertThat(questions).hasSize(2);
        final var question1 = questions.get(0);
        assertThat(question1).isNotNull();
        assertThat(question1.getId()).isEqualTo(questionId);
        assertThat(question1.getContent()).isEqualTo("Spring is the best JAVA framework");
        final var question2 = questions.get(1);
        assertThat(question2).isNotNull();
        assertThat(question2.getId()).isEqualTo(questionIdToUpdate);
        assertThat(question2.getContent()).isEqualTo("new content");
        List<Answer> updatedAnswers2 = question2.getAnswers();
        assertThat(updatedAnswers2).hasSize(3);
        final var answer3 = updatedAnswers2.get(0);
        assertThat(answer3).isNotNull();
        assertThat(answer3.getId()).isEqualTo(quizSecondQuestionFirstAnswerId);
        assertThat(answer3.getContent()).isEqualTo("content3");
        assertThat(answer3.isCorrect()).isFalse();
        final var answer4 = updatedAnswers2.get(1);
        assertThat(answer4).isNotNull();
        assertThat(answer4.getId()).isEqualTo(newAnswerId1);
        assertThat(answer4.getContent()).isEqualTo("New answer 1");
        assertThat(answer4.isCorrect()).isFalse();
        final var answer5 = updatedAnswers2.get(2);
        assertThat(answer5).isNotNull();
        assertThat(answer5.getId()).isEqualTo(newAnswerId2);
        assertThat(answer5.getContent()).isEqualTo("New answer 2");
        assertThat(answer5.isCorrect()).isTrue();
    }

    @Test
    void should_throw_not_found_exception_when_try_update_question_and_quiz_by_id_not_exist() {
        //given
        final var quizId = UUID.fromString("b83d5c22-7b78-4435-9daa-17bb532c0f63");
        final var questionId = UUID.fromString("435aeee2-5a1f-4723-9359-1137ed820ae7");
        final var answer1Id = UUID.fromString("d7e876ae-364d-403e-b537-7bcb2c2841fa");
        final var answers = List.of(UpdateAnswer.builder().id(answer1Id).content("Yes").correct(true).build());
        final var updateQuestion = new UpdateQuestion(questionId, "new content", answers);
        when(quizRepository.findById(quizId)).thenReturn(Optional.empty());
        //when
        QuizNotFoundException exception = assertThrows(QuizNotFoundException.class, () -> service.updateQuestionFromQuiz(quizId, updateQuestion));
        //then
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage())
                .isEqualTo(String.format("Quiz by ID %s not exist", quizId));
    }

    private Quiz getQuiz(UUID quizId) {
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

    private Quiz getQuizWithQuestionsAndAnswers(UUID quizId, UUID questionId, UUID questionIdToUpdate) {
        final var answerId1 = UUID.fromString("1f12ef49-9a35-45f2-9824-edb673602a7f");
        final var answerId2 = UUID.fromString("126b0faa-1de1-4b9e-b79c-8b03361e8839");
        final var answerId3 = UUID.fromString("9f5e41ad-9614-4a14-ac8a-dad55d4ce89f");
        final var answerId4 = UUID.fromString("5e02b9e3-c1d4-43da-b7df-45899da5b9a2");
        final var content1 = "Content 1";
        final var content2 = "Content 2";
        final var content3 = "Content 3";
        final var content4 = "Content 4";

        final var question1 = Question.builder()
                .id(questionId)
                .content("Spring is the best JAVA framework")
                .answers(new ArrayList<>(Arrays.asList(createAnswer(answerId1, content1, true), createAnswer(answerId2, content2, false))))
                .build();
        final var question2 = Question.builder()
                .id(questionIdToUpdate)
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

    private UpdateAnswer createUpdateAnswer(UUID id, String content, Boolean correct) {
        return UpdateAnswer.builder()
                .id(id)
                .content(content)
                .correct(correct)
                .build();
    }
}
