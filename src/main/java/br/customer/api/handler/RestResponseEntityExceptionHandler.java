package br.customer.api.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.customer.api.model.error.ApiError;
import br.customer.api.model.error.ErrorDetails;



@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  private static String errorMessage = "reques.error";
  private static String servletWebRequestMessage = "ServletWebRequest: uri=/";

  // 400

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
    final MethodArgumentNotValidException ex,
    final HttpHeaders headers,
    final HttpStatus status,
    final WebRequest request
  ) {
    final List<ErrorDetails> errors = new ArrayList<>();
    for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.add(
        errorFieldErrorMessage(
          error,
          request,
          errorMessage,
          Arrays.asList(ex.getMessage().split(";")[2])
        )
      );
    }
    for (final ObjectError objectError : ex
      .getBindingResult()
      .getGlobalErrors()) {
      errors.add(
        errorObjectErrorMessage(
          objectError,
          request,
          errorMessage,
          Arrays.asList(ex.getMessage().split(";")[2])
        )
      );
    }

    final ApiError apiError = createApiError(
      request,
      ex.getLocalizedMessage(),
      errors,
      ex.getStackTrace()[0].getMethodName()
    );
    return handleExceptionInternal(
      ex,
      apiError,
      headers,
      apiError.getError(),
      request
    );
  }

  @Override
  protected ResponseEntity<Object> handleBindException(
    final BindException ex,
    final HttpHeaders headers,
    final HttpStatus status,
    final WebRequest request
  ) {
    final List<ErrorDetails> errors = new ArrayList<>();
    for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.add(
        errorFieldErrorMessage(
          error,
          request,
          errorMessage,
          Arrays.asList(ex.getMessage().split(";")[2])
        )
      );
    }
    for (final ObjectError objectError : ex
      .getBindingResult()
      .getGlobalErrors()) {
      errors.add(
        errorObjectErrorMessage(
          objectError,
          request,
          errorMessage,
          Arrays.asList(ex.getMessage().split(";")[2])
        )
      );
    }
    final ApiError apiError = createApiError(
      request,
      ex.getLocalizedMessage(),
      errors,
      ex.getStackTrace()[0].getMethodName()
    );
    return handleExceptionInternal(
      ex,
      apiError,
      headers,
      apiError.getError(),
      request
    );
  }

  @Override
  protected ResponseEntity<Object> handleTypeMismatch(
    final TypeMismatchException ex,
    final HttpHeaders headers,
    final HttpStatus status,
    final WebRequest request
  ) {
    final List<ErrorDetails> errors = new ArrayList<>();
    errors.add(
      createErrorDetail(
        ex.getValue() +
        " value for " +
        ex.getPropertyName() +
        " should be of type " +
        ex.getRequiredType(),
        errorMessage,
        request,
        new ArrayList<>()
      )
    );

    final ApiError apiError = createApiError(
      request,
      ex.getLocalizedMessage(),
      errors,
      ex.getStackTrace()[0].getMethodName()
    );
    return new ResponseEntity<>(
      apiError,
      new HttpHeaders(),
      apiError.getError()
    );
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestPart(
    final MissingServletRequestPartException ex,
    final HttpHeaders headers,
    final HttpStatus status,
    final WebRequest request
  ) {
    final List<ErrorDetails> errors = new ArrayList<>();
    errors.add(
      createErrorDetail(
        ex.getRequestPartName() + " part is missing",
        errorMessage,
        request,
        new ArrayList<>()
      )
    );

    final ApiError apiError = createApiError(
      request,
      ex.getLocalizedMessage(),
      errors,
      ex.getStackTrace()[0].getMethodName()
    );
    return new ResponseEntity<>(
      apiError,
      new HttpHeaders(),
      apiError.getError()
    );
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
    final MissingServletRequestParameterException ex,
    final HttpHeaders headers,
    final HttpStatus status,
    final WebRequest request
  ) {
    final List<ErrorDetails> errors = new ArrayList<>();
    errors.add(
      createErrorDetail(
        ex.getParameterName() + " part is missing",
        errorMessage,
        request,
        new ArrayList<>()
      )
    );

    final ApiError apiError = createApiError(
      request,
      ex.getLocalizedMessage(),
      errors,
      ex.getStackTrace()[0].getMethodName()
    );
    return new ResponseEntity<>(
      apiError,
      new HttpHeaders(),
      apiError.getError()
    );
  }

  @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
  public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
    final MethodArgumentTypeMismatchException ex,
    final WebRequest request
  ) {
    final List<ErrorDetails> errors = new ArrayList<>();
    errors.add(
      createErrorDetail(
        ex.getName() + " should be of type " + ex.getRequiredType().getName(),
        errorMessage,
        request,
        new ArrayList<>()
      )
    );

    final ApiError apiError = createApiError(
      request,
      ex.getLocalizedMessage(),
      errors,
      ex.getStackTrace()[0].getMethodName()
    );
    return new ResponseEntity<>(
      apiError,
      new HttpHeaders(),
      apiError.getError()
    );
  }

  @ExceptionHandler({ ConstraintViolationException.class })
  public ResponseEntity<Object> handleConstraintViolation(
    final ConstraintViolationException ex,
    final WebRequest request
  ) {
    final String[] er = ex.getMessage().split(";");
    final List<ErrorDetails> errors = new ArrayList<>();
    for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
      errors.add(
        createErrorDetail(
          violation.getRootBeanClass().getName() +
          " " +
          violation.getPropertyPath() +
          ": " +
          violation.getMessage(),
          errorMessage,
          request,
          Arrays.asList(er[2])
        )
      );
    }

    final ApiError apiError = createApiError(
      request,
      ex.getLocalizedMessage(),
      errors,
      ex.getStackTrace()[0].getMethodName()
    );
    return new ResponseEntity<>(
      apiError,
      new HttpHeaders(),
      apiError.getError()
    );
  }

  // 404

  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(
    final NoHandlerFoundException ex,
    final HttpHeaders headers,
    final HttpStatus status,
    final WebRequest request
  ) {
    final List<ErrorDetails> errors = new ArrayList<>();
    errors.add(
      createErrorDetail(
        "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL(),
        errorMessage,
        request,
        new ArrayList<>()
      )
    );
    final ApiError apiError = createApiError(
      request,
      ex.getLocalizedMessage(),
      errors,
      ex.getStackTrace()[0].getMethodName()
    );
    return new ResponseEntity<>(
      apiError,
      new HttpHeaders(),
      apiError.getError()
    );
  }

  // 405

  @Override
  protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
    final HttpRequestMethodNotSupportedException ex,
    final HttpHeaders headers,
    final HttpStatus status,
    final WebRequest request
  ) {
    final StringBuilder builder = new StringBuilder();
    final List<ErrorDetails> errors = new ArrayList<>();
    builder.append(ex.getMethod());
    builder.append(
      " method is not supported for this request. Supported methods are "
    );
    ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));
    errors.add(
      createErrorDetail(
        builder.toString(),
        errorMessage,
        request,
        new ArrayList<>()
      )
    );

    final ApiError apiError = new ApiError(
      HttpStatus.METHOD_NOT_ALLOWED,
      ex.getLocalizedMessage(),
      errors
    );
    apiError.setMethod(ex.getStackTrace()[0].getMethodName());
    apiError.setPath(
      request.toString().replace(servletWebRequestMessage, "").split(";")[0]
    );
    return new ResponseEntity<>(
      apiError,
      new HttpHeaders(),
      apiError.getError()
    );
  }

  // 415

  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
    final HttpMediaTypeNotSupportedException ex,
    final HttpHeaders headers,
    final HttpStatus status,
    final WebRequest request
  ) {
    final StringBuilder builder = new StringBuilder();
    builder.append(ex.getContentType());
    builder.append(" media type is not supported. Supported media types are ");
    ex.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));

    final List<ErrorDetails> errors = new ArrayList<>();
    errors.add(
      createErrorDetail(
        builder.substring(0, builder.length() - 2),
        errorMessage,
        request,
        new ArrayList<>()
      )
    );

    final ApiError apiError = new ApiError(
      HttpStatus.UNSUPPORTED_MEDIA_TYPE,
      ex.getLocalizedMessage(),
      errors
    );
    apiError.setMethod(ex.getStackTrace()[0].getMethodName());
    apiError.setPath(
      request.toString().replace(servletWebRequestMessage, "").split(";")[0]
    );
    return new ResponseEntity<>(
      apiError,
      new HttpHeaders(),
      apiError.getError()
    );
  }

  // 500

  @ExceptionHandler({ Exception.class })
  public ResponseEntity<Object> handleAll(
    final Exception ex,
    final WebRequest request
  ) {
    final List<ErrorDetails> errors = new ArrayList<>();
    errors.add(
      createErrorDetail(
        ex.getLocalizedMessage(),
        errorMessage,
        request,
        new ArrayList<>()
      )
    );

    final ApiError apiError = new ApiError(
      HttpStatus.INTERNAL_SERVER_ERROR,
      "error occurred",
      errors
    );
    apiError.setMethod(ex.getStackTrace()[0].getMethodName());
    apiError.setPath(
      request.toString().replace(servletWebRequestMessage, "").split(";")[0]
    );
    return new ResponseEntity<>(
      apiError,
      new HttpHeaders(),
      apiError.getError()
    );
  }

  private ErrorDetails errorFieldErrorMessage(
    FieldError fieldError,
    final WebRequest request,
    String errorMessage,
    List<String> listString
  ) {
    return createErrorDetail(
      fieldError.getField() + ": " + fieldError.getDefaultMessage(),
      errorMessage,
      request,
      listString
    );
  }

  private ErrorDetails createErrorDetail(
    String messageError,
    String informationCode,
    WebRequest request,
    List<String> listString
  ) {
    return ErrorDetails.builder().message(messageError).build();
  }

  private ErrorDetails errorObjectErrorMessage(
    ObjectError objectError,
    final WebRequest request,
    String errorMessage,
    List<String> listString
  ) {
    return createErrorDetail(
      objectError.getObjectName() + ": " + objectError.getDefaultMessage(),
      errorMessage,
      request,
      listString
    );
  }

  private ApiError createApiError(
    final WebRequest request,
    String message,
    List<ErrorDetails> errors,
    String methodName
  ) {
    final ApiError apiError = new ApiError(
      HttpStatus.BAD_REQUEST,
      message,
      errors
    );
    apiError.setMethod(methodName);
    apiError.setPath(
      request.toString().replace(servletWebRequestMessage, "").split(";")[0]
    );
    return apiError;
  }
}
