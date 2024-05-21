package vn.unigap.api.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
