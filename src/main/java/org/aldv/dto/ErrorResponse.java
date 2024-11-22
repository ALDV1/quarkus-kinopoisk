package org.aldv.dto;

public record ErrorResponse(Integer status, String message, String details) {
}
