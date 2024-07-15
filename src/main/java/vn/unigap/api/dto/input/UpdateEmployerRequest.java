package vn.unigap.api.dto.input;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEmployerRequest {
  @NotNull
  @NotEmpty
  @Size(max = 255, message = "Name length must be smaller than 255 characters")
  private String name;

  @NotNull
  @Min(value = 1, message = "Province ID must be greater than 0")
  private Integer provinceId;

  private String description;
}
