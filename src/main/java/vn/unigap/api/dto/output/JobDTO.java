package vn.unigap.api.dto.output;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class JobDTO {
  private Integer id;
  private String title;
  private Integer quantity;
  private Integer salary;
  private LocalDateTime expiredAt;
  private Integer employerId;
  private String employerName;
}
