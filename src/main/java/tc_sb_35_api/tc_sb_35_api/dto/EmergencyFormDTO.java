package tc_sb_35_api.tc_sb_35_api.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// DTO to receive info from new emergency form in frontend
// Attributes have annotation to avoid invalid input related errors
// Input validation is also made in frontend but it is worth making it here for better decoupling
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class EmergencyFormDTO {

    @Min(value = 1, message = "Id de clínica de la emergencia no válido")
    private Long emergencyClinic;

    @Min(value = 1, message = "Id de suscripción de la emergencia no válido")
    private Long emergencySubscription;

    // If new species are added to database then max value must be modified according to max value of idSpecies
    @Min(value = 1, message = "Id de especie de la emergencia no válido")
    @Max(value = 9, message = "Id de especie de la emergencia no válido")
    private int emergencySpecies;

    // If new main symptoms are added to database then max value must be modified according to max value of idMainSymptom
    @Min(value = 1, message = "Id de síntoma de la emergencia no válido")
    @Max(value = 21, message = "Id de síntoma de la emergencia no válido")
    private int emergencyMainSymptom;

    @NotBlank(message = "Descripción de la emergencia requerida")
    @Size(max = 1000, message = "Descripción de la emergencia no válida, máximo 1000 caracteres permitidos")
    private String emergencyDescription;
    
}
