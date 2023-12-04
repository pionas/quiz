package info.pionas.quiz.domain.user.api;

public class UserFoundException extends RuntimeException {

    public UserFoundException(String username) {
        super(String.format("Username %s already in use", username));
    }
}
