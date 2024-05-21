package vn.unigap.api.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ResumeByIdDTO {
    private Integer id;
    private Integer seekerId;
    private String seekerName;
    private String careerObj;
    private String title;
    private Integer salary;
    private List<ProvinceDT0> provinces;
    private List<JobFiedsDTO> fields;
}
