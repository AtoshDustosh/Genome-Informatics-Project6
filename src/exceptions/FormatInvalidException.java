package exceptions;

@SuppressWarnings("serial")
public class FormatInvalidException extends Exception {

  public FormatInvalidException() {
    super();
  }

  public FormatInvalidException(String message) {
    super(message);
  }

  public FormatInvalidException(String message, Throwable cause) {
    super(message, cause);
  }

  public FormatInvalidException(Throwable cause) {
    super(cause);
  }

}
