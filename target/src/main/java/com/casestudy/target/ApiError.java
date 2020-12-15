package com.casestudy.target;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

/*Object use to send Not found messages in case of a query not returning results
 *Might want to rename it as "ApiMessage" since it's also use for Cache Refresh successful message
 **/
public class ApiError {


	    private HttpStatus status;
	    private String message;
	    private List<String> errors;

	    public ApiError(HttpStatus status, String message, List<String> errors) {
	        super();
	        this.status = status;
	        this.message = message;
	        this.errors = errors;
	    }

	    public ApiError(HttpStatus status, String message, String error) {
	        super();
	        this.status = status;
	        this.message = message;
	        errors = Arrays.asList(error);
	    }
	    
	    public ApiError(HttpStatus status, String message) {
	        super();
	        this.status = status;
	        this.message = message;	        
	    }

		public HttpStatus getStatus() {
			return status;
		}

		public void setStatus(HttpStatus status) {
			this.status = status;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public List<String> getErrors() {
			return errors;
		}

		public void setErrors(List<String> errors) {
			this.errors = errors;
		}
	    
	    
}
