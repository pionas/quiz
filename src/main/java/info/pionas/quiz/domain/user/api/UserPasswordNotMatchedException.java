package info.pionas.quiz.domain.user.api;

public class UserPasswordNotMatchedException extends RuntimeException {

    public UserPasswordNotMatchedException() {
        super("Password doesn't matched");
    }
}
