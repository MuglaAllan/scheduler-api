package je.dvs.echo.registrationPoolapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason="Access Denied")
public class UnAuthorizedException extends RuntimeException  {
}
