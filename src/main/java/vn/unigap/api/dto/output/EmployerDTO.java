package vn.unigap.api.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class EmployerDTO {
    private Integer id;
    private String email;
    private String name;
    private Integer provinceId;
    private String provinceName;
}
