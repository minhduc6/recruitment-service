package vn.unigap.api.dto.input;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseSeekerRequest {
    @NotNull(message = "Name must not be null")
    private String name;
    @NotNull(message = "Birthday must not be null")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Birthday must be in the format yyyy-MM-dd")
    private String birthday;
    private String address;
    @NotNull(message = "Province ID must not be null")
    private Integer provinceId;
}
