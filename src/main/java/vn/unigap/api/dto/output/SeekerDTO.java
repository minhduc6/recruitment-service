package vn.unigap.api.dto.output;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SeekerDTO {
    private Integer id;
    private String name;
    private String birthday;
    private String address;
    private Integer provinceId;
    private String provinceName;
}
