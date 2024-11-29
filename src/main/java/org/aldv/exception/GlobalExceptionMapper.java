package org.aldv.exception;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.aldv.dto.ErrorResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import static org.aldv.util.ExceptionConstants.DEFAULT_MESSAGE;
import static org.aldv.util.ExceptionConstants.INVALID_ERROR_FORMAT;
import static org.aldv.util.ExceptionConstants.INVALID_RATING;
import static org.aldv.util.ExceptionConstants.MOVIE_NOT_FOUND;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class GlobalExceptionMapper {

    @ServerExceptionMapper
    public Response handleException(Exception ex) {
        ErrorResponse errorResponse = createErrorResponse(ex);
        Response.Status status = getStatusForException(ex);
        return Response.status(status).entity(errorResponse).build();
    }

    private ErrorResponse createErrorResponse(Exception exception) {
        String message = exception.getMessage();
        String errorType = getErrorType(exception);

        return new ErrorResponse(
                Response.Status.BAD_REQUEST.getStatusCode(),
                message,
                errorType
        );
    }

    public Response.Status getStatusForException(Exception exception) {
        if(exception instanceof InvalidParseYearException){
            return Response.Status.BAD_REQUEST;
        }
        if(exception instanceof RatingInvalidException){
            return Response.Status.BAD_REQUEST;
        }
        if(exception instanceof MovieNotFoundException){
            return Response.Status.NOT_FOUND;
        }
        else{
            return Response.Status.INTERNAL_SERVER_ERROR;
        }
    }

    private String getErrorType(Exception exception) {
        if(exception instanceof InvalidParseYearException){
            return INVALID_ERROR_FORMAT;
        }
        if(exception instanceof RatingInvalidException){
            return INVALID_RATING;
        }
        if(exception instanceof MovieNotFoundException){
            return MOVIE_NOT_FOUND;
        }
        else{
            return DEFAULT_MESSAGE;
        }
    }

}
