package vn.unigap.api.dto.output;

import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class EmployerDTO implements Serializable {
  @Serial private static final long serialVersionUID = 1L;
  private Integer id;
  private String email;
  private String name;
  private Integer provinceId;
  private String provinceName;
}
