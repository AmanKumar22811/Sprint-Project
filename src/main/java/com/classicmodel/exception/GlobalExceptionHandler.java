package com.classicmodel.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * Centralized exception handler for the Classic Models REST API.
 *
 * WHY HandlerExceptionResolver instead of @RestControllerAdvice:
 *   Spring Data REST registers its own RepositoryRestHandlerExceptionResolver
 *   at order 0. The standard ExceptionHandlerExceptionResolver (which backs
 *   @RestControllerAdvice) runs at order 1 — AFTER Spring Data REST's resolver
 *   has already swallowed the exception and returned a stack trace or bare 404.
 *
 *   By implementing HandlerExceptionResolver with Ordered.HIGHEST_PRECEDENCE
 *   our handler is guaranteed to run FIRST, before Spring Data REST's resolver
 *   can intercept any exception.
 *
 * WHAT THIS HANDLES:
 *   GET /api/orders/99999  → ResourceNotFoundException (Spring Data REST) → 404 JSON
 *   POST with bad data     → BadRequestException from @HandleBeforeCreate  → 400 JSON
 *   POST with missing FK   → EmployeeNotFoundException etc. from EventHandler → 404 JSON
 *   Duplicate key inserts  → DataIntegrityViolationException               → 409 JSON
 *   Unknown exceptions     → returns null, delegated to next resolver
 */
@Component
public class GlobalExceptionHandler implements HandlerExceptionResolver, Ordered {

    private final ObjectMapper objectMapper;

    public GlobalExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response,
                                         Object handler,
                                         Exception ex) {
        HttpStatus status;
        String message;

        // ── 404 Not Found ─────────────────────────────────────────────────────

        if (ex instanceof EntityNotFoundException) {
            status = HttpStatus.NOT_FOUND;
            message = ex.getMessage();
        }
        else if (ex instanceof org.springframework.data.rest.webmvc.ResourceNotFoundException) {
            status = HttpStatus.NOT_FOUND;
            message = buildNotFoundMessage(request.getRequestURI());
        }
        else if (ex instanceof ResourceNotFoundException) {
            status = HttpStatus.NOT_FOUND;
            message = ex.getMessage();
        }

        // ── 400 Bad Request ───────────────────────────────────────────────────

        else if (ex instanceof BadRequestException) {
            status = HttpStatus.BAD_REQUEST;
            message = ex.getMessage();
        }
        else if (ex instanceof ConstraintViolationException cve) {
            status = HttpStatus.BAD_REQUEST;
            message = "Validation failed: " + cve.getConstraintViolations().stream()
                    .map(v -> v.getPropertyPath() + " " + v.getMessage())
                    .collect(Collectors.joining("; "));
        }
        else if (ex instanceof RepositoryConstraintViolationException rcve) {
            status = HttpStatus.BAD_REQUEST;
            message = "Validation failed: " + rcve.getErrors().getAllErrors().stream()
                    .map(e -> e.getDefaultMessage())
                    .collect(Collectors.joining("; "));
        }
        else if (ex instanceof NumberFormatException || ex instanceof IllegalArgumentException) {
            status = HttpStatus.BAD_REQUEST;
            message = "Invalid value in request — " + ex.getMessage();
        }

        // ── 409 Conflict ──────────────────────────────────────────────────────

        else if (ex instanceof DataIntegrityViolationException dive) {
            status = HttpStatus.CONFLICT;
            String cause = dive.getMostSpecificCause().getMessage();
            if (cause != null && cause.contains("Duplicate entry")) {
                message = "Duplicate key: a record with this ID already exists.";
            } else if (cause != null && (cause.contains("foreign key") || cause.contains("FOREIGN KEY"))) {
                message = "Foreign key violation: the referenced record does not exist.";
            } else {
                message = "Data integrity violation — please check your input values.";
            }
        }

        // ── Unknown — let Spring Data REST / Spring MVC handle it ─────────────

        else {
            return null;
        }

        writeJson(response, status, message, request.getRequestURI());
        return new ModelAndView();
    }

    // ── Private Helpers ───────────────────────────────────────────────────────

    private String buildNotFoundMessage(String uri) {
        if (uri == null) return "Resource not found";
        String path = uri.contains("?") ? uri.substring(0, uri.indexOf('?')) : uri;
        String[] parts = path.split("/");
        if (parts.length >= 2) {
            String resourceRaw = parts[parts.length - 2];
            String id          = parts[parts.length - 1];
            String resourceName = resourceRaw.isEmpty() ? "Resource"
                    : Character.toUpperCase(resourceRaw.charAt(0))
                      + (resourceRaw.length() > 1 && resourceRaw.endsWith("s")
                         ? resourceRaw.substring(1, resourceRaw.length() - 1)
                         : resourceRaw.substring(1));
            return resourceName + " not found with id: " + id;
        }
        return "Resource not found: " + uri;
    }

    private void writeJson(HttpServletResponse response,
                           HttpStatus status,
                           String message,
                           String path) {
        ApiError error = new ApiError(
                LocalDateTime.now().toString(),
                status.value(),
                status.getReasonPhrase(),
                message,
                path);
        try {
            response.setStatus(status.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            objectMapper.writeValue(response.getWriter(), error);
            response.getWriter().flush();
        } catch (IOException ignored) {
        }
    }
}
