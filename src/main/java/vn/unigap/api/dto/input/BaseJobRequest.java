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
public class BaseJobRequest {
    @NotNull(message = "Title must not be null")
    private String title;
    @NotNull(message = "Description must not be null")
    private String description;
    @NotNull(message = "Quantity must not be null")
    @Min(value = 0,message = "Quantity > 0")
    private Integer quantity;
    @NotNull(message = "Salary must not be null")
    @Min(value = 0,message = "Salary > 0")
    private Integer salary;
    @NotNull(message = "expiredAt must not be null")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "expiredAt must be in the format YYYY-MM-DD")
    private String expiredAt;
    @NotNull(message = "employerId must not be null")
    private Integer employerId;
    @NotNull(message = "fieldIds must not be null")
    private List<Integer> fieldIds;
    @NotNull(message = "provinceIds must not be null")
    private List<Integer> provinceIds;
}
