package ru.gladun.historylearningplatform.exception;

public enum ServerErrorCode {

    UNEXPECTED_EXCEPTION("Oops, something went wrong"),
    LOGIN_BUSY("User with this login already exists"),
    USER_NOT_FOUND("Invalid login or such user does not exist"),
    WRONG_PASSWORD("Invalid password"),
    WRONG_PASSWORD_CONFIRM("Password confirm and password must match"),
    ARTICLE_NOT_FOUND("Article not found"),
    COMMENT_NOT_FOUND("Comment not found");

    private final String message;

    ServerErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
