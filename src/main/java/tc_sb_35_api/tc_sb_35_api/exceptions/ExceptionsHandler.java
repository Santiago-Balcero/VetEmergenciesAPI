package tc_sb_35_api.tc_sb_35_api.exceptions;

import java.io.IOException;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ExceptionsHandler {
    
    // SYSTEM EXCEPTIONS

    // This method catches when idClinic or idVeterinarian is 0, null or idClinic/idVeterinarian doesn't exist in DB
    @ExceptionHandler(value = {IllegalArgumentException.class, NoSuchElementException.class, EmptyResultDataAccessException.class})
    public ResponseEntity<Object> notFound(Exception ex) {
        ex.printStackTrace();
        log.info("HTTP RESPONSE: exception handler message, register not found");
        return new ResponseEntity<>("Registro no encontrado en el sistema.", HttpStatus.BAD_REQUEST);
    }

    // This method catches when a new clinic or new veterinarian has a NIT or Document that already exists in DB
    @ExceptionHandler(value = {ConstraintViolationException.class, DataIntegrityViolationException.class})
    public ResponseEntity<Object> existingRegister(Exception ex) {
        ex.printStackTrace();
        log.info("HTTP RESPONSE: exception handler message, register already exists in system");
        return new ResponseEntity<>("El registro ya existe en el sistema, verifica el NIT o número de documento.", HttpStatus.BAD_REQUEST);
    }

    // This method catches when there is an IOException, base exception for when accessing information using streams, files and directories
    @ExceptionHandler(value = IOException.class)
    public ResponseEntity<Object> ioException(Exception ex) {
        ex.printStackTrace();
        log.info("HTTP RESPONSE: exception handler message, unable to read data from file");
        return new ResponseEntity<>("No se puede leer la información del archivo.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // For when calling PL/SQL procedures fails
    @ExceptionHandler(value = SQLException.class)
    public ResponseEntity<Object> sqlException(SQLException ex) {
        ex.printStackTrace();
        log.info("HTTP RESPONSE: exception handler message, sql exception");
        return new ResponseEntity<>("Error en la ejecución de procedimento de conteo de emergencias.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // CREATED EXCEPTIONS

    // This method catches when ClinicNotFoundException is thrown
    @ExceptionHandler(value = ClinicNotFoundException.class)
    public ResponseEntity<Object> clinicNotFoundException(ClinicNotFoundException ex) {
        ex.printStackTrace();
        log.info("HTTP RESPONSE: exception handler message, clinic not found");
        return new ResponseEntity<>("Clínica no registrada en el sistema.", HttpStatus.BAD_REQUEST);
    }

    // This method catches when VeterinarianNotFoundException is thrown
    @ExceptionHandler(value = VeterinarianNotFoundException.class)
    public ResponseEntity<Object> veterinarianNotFoundException(VeterinarianNotFoundException ex) {
        ex.printStackTrace();
        log.info("HTTP RESPONSE: exception handler message, veterinarian not found");
        return new ResponseEntity<>("Veterinario no registrado en el sistema.", HttpStatus.BAD_REQUEST);
    }

    // Method to catch other general exceptions for example no connection with database
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> generalException(Exception ex) {
        ex.printStackTrace();
        log.info("HTTP RESPONSE: exception handler message, general error " + ex.getMessage());
        return new ResponseEntity<>("Error al acceder a los datos: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Method to catch when it was not possible to save objects in DB
    @ExceptionHandler(value = UnableToSaveObjectException.class)
    public ResponseEntity<Object> unableToSaveObjectException(UnableToSaveObjectException ex) {
        ex.printStackTrace();
        log.info("HTTP RESPONSE: exception handler message, unable to save object");
        return new ResponseEntity<>("Error al crear o actualizar el registro en el sistema.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Method to catch when clinic address was invalid
    @ExceptionHandler(value = InvalidAddressException.class)
    public ResponseEntity<Object> invalidAddressException(RuntimeException ex) {
        ex.printStackTrace();
        log.info("HTTP RESPONSE: exception handler message, invalid address");
        return new ResponseEntity<>("Dirección no válida.", HttpStatus.BAD_REQUEST);
    }

    // This method catches when a client with a subscription is trying to create a new one
    @ExceptionHandler(value = ClientWithSubscriptionException.class)
    public ResponseEntity<Object> clientWithSubscriptionException(ClientWithSubscriptionException ex) {
        ex.printStackTrace();
        log.info("HTTP RESPONSE: exception handler message, client already has a subscription, idClient: " + ex.getIdClient());
        return new ResponseEntity<>("El cliente con id " + ex.getIdClient() + " ya tiene una suscripción.", HttpStatus.BAD_REQUEST);
    }

    // This method catches when a Subscription is not active and that subscription wnats register or do something with an emergency
    @ExceptionHandler(value = ExpiredSubscriptionException.class)
    public ResponseEntity<Object> expiredSubscriptionException(ExpiredSubscriptionException ex) {
        ex.printStackTrace();
        log.info("HTTP RESPONSE: exception handler message, expired subscription");
        return new ResponseEntity<>("La suscripción no está activa.", HttpStatus.BAD_REQUEST);
    }

    // This method catches when clientdocument of a new client already exists in DB and that clientDocument is returned to user for correction
    @ExceptionHandler(value = ExistingClientException.class)
    public ResponseEntity<Object> existingClientException(ExistingClientException ex) {
        ex.printStackTrace();
        log.info("HTTP RESPONSE: exception handler message, client already exists in DB, client document: " + ex.getClientDocument());
        return new ResponseEntity<>("El cliente con documento " + ex.getClientDocument() + " ya existe en el sistema.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> notValidException(MethodArgumentNotValidException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }

    // This method catches when InvalidPasswordException is thrown
    @ExceptionHandler(value = InvalidCredentialsException.class)
    public ResponseEntity<Object> invalidCredentialsException(InvalidCredentialsException ex) {
        ex.printStackTrace();
        log.info("HTTP RESPONSE: exception handler message, invalid credentials");
        return new ResponseEntity<>("Número de documento y/o contraseña no válidos.", HttpStatus.BAD_REQUEST);
    }

}
