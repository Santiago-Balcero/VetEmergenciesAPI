package tc_sb_35_api.tc_sb_35_api.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tc_sb_35_api.tc_sb_35_api.model.ServiceOfClinic;

// DTO to receive info of new clinic to be registered
// Attributes have annotation to avoid invalid input related errors
// Input validation is also made in frontend but it is worth making it here for better decoupling
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ClinicFormDTO {
    
    @NotBlank(message = "Nombre de clínica requerido")
    @Size(max = 50, message = "Nombre de clínica no válido, máximo 50 caracteres permitidos")
    private String clinicName;

    @NotBlank(message = "NIT de clínica requerido")
    @Size(max = 9, message = "NIT de clínica no válido, máximo 9 caracteres permitidos")
    private String clinicNit;

    @Min(value = 1, message = "Id de departamento de clínica no válido")
    @Max(value = 33, message = "Id de departamento de clínica no válido")
    private Long clinicDepartment;

    @NotBlank(message = "Ciudad de clínica requerida")
    @Size(max = 40, message = "Ciudad de clínica no válida, máximo 40 caracteres permitidos")
    private String clinicCity;

    @NotBlank(message = "Dirección de clínica requerida")
    @Size(min = 8, max = 50, message = "Dirección de clínica no válida, mínimo 8 y máximo 50 caracteres permitidos")
    // Minimum size valid address will be like "Cl 1 2-3"
    private String clinicAddress;

    @NotBlank(message = "Teléfono de clínica requerido")
    @Size(min = 10, max = 10, message = "Teléfono de clínica no válido, 10 caracteres permitidos")
    private String clinicTelephone;

    @NotBlank(message = "Correo electrónico de clínica requerido")
    @Email(message = "Correo electrónico de clínica no válido")
    @Size(max = 50, message = "Correo electrónico de clínica no válido, máximo 50 caracteres permitidos")
    private String clinicEmail;

    @Size(min = 1, message = "La clínica debe registrar al menos un servicio")
    private List<ServiceOfClinic> clinicServices;

}
