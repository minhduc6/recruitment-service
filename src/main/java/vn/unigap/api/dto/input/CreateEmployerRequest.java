package vn.unigap.api.dto.input;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmployerRequest {
  @Email(message = "Invalid email format")
  @NotNull(message = "Email must not be null")
  @NotEmpty(message = "Email must not be empty")
  @Size(max = 255, message = "Email length must be smaller than 255 characters")
  private String email;

  @NotNull(message = "Name must not be null")
  @NotEmpty(message = "Name must not be empty")
  @Size(max = 255, message = "Name length must be smaller than 255 characters")
  private String name;

  @NotNull(message = "Province ID  must not be null")
  @Min(value = 1, message = "Province ID must be greater than 0")
  private Integer provinceId;

  private String description;
}
