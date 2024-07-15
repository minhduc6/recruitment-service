package vn.unigap.api.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreateResumeRequest extends BaseResumeRequest {
  @NotNull(message = "Seeker Id must not be null")
  private Integer seekerId;
}
