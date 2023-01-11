package tc_sb_35_api.tc_sb_35_api.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// DTO to receive veterinarian info from veterinarian form (create method)
// Attributes have annotation to avoid invalid input related errors
// Input validation is also made in frontend but it is worth making it here for better decoupling
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class VeterinarianFormDTO {

    @Min(value = 1, message = "Id de clínica de la emergencia no válido")
    private Long vetClinicId;

    @NotBlank(message = "Documento de veterinario requerido")
    @Size(max = 20, message = "Documento de veterinario no válido, máximo 20 caracteres permitidos")
    private String vetDocument;

    @NotBlank(message = "Nombre de veterinario requerido")
    @Size(max = 50, message = "Nombre de veterinario no válido, máximo 50 caracteres permitidos")
    private String vetName;

    @NotBlank(message = "Apellido de veterinario requerido")
    @Size(max = 50, message = "Apellido de veterinario no válido, máximo 50 caracteres permitidos")
    private String vetLastname;

    @NotBlank(message = "Teléfono de veterinario requerido")
    @Size(min = 10, max = 10, message = "Teléfono de veterinario no válido, 10 caracteres permitidos")
    private String vetTelephone;

    @NotBlank(message = "Correo electrónico de veterinarion requerido")
    @Email(message = "Correo electrónico de veterinarion no válido")
    @Size(max = 50, message = "Correo electrónico de veterinarion no válido, máximo 50 caracteres permitidos")
    private String vetEmail;
    
}
