package vn.unigap.api.dto.output;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class StatisticByDay {
  private LocalDate day;
  private Long numberEmployer;
  private Long numberJob;
  private Long numberSeeker;
  private Long numberResume;
}
