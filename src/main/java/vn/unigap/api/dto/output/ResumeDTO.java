package vn.unigap.api.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ResumeDTO {
  private Integer id;
  private Integer seekerId;
  private String seekerName;
  private String careerObj;
  private String title;
  private Integer salary;
}
