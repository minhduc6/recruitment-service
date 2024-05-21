package vn.unigap.api.dto.input;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResumeRequest {
    @NotNull(message = "Title must not be null")
    private String title;
    @NotNull(message = "careerObj must not be null")
    private String careerObj;
    @NotNull(message = "Salary must not be null")
    @Min(value = 0,message = "Salary > 0")
    private Integer salary;
    @NotNull(message = "fieldIds must not be null")
    private List<Integer> fieldIds;
    @NotNull(message = "provinceIds must not be null")
    private List<Integer> provinceIds;
}
