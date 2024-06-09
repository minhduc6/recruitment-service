package vn.unigap.api.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class StatisticTheSystemDTO {
    private Long numberEmployer;
    private Long numberJob;
    private Long numberSeeker;
    private Long numberResume;
    private List<StatisticByDay> chart;
}
