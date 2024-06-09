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
public class JobInformation {
    private Integer id;
    private String title;
    private String description;
    private List<ProvinceDT0> provinces;
    private List<JobFiedsDTO> fields;
    private Integer quantity;
    private Integer salary;
    private LocalDateTime expiredAt;
    private Integer employerId;
    private String employerName;
    private List<SeekerInfomation> seekerInfomationList;
}
