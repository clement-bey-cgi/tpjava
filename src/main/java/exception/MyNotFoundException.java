package exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javassist.NotFoundException;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class MyNotFoundException extends NotFoundException {

	public MyNotFoundException(String msg) {
		super(msg);
	}

}
