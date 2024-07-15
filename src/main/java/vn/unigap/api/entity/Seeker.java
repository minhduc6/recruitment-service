package vn.unigap.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "seeker")
public class Seeker extends BaseEntity implements Serializable {
  private String name;
  private String birthday;
  private String address;

  @ManyToOne
  @JoinColumn(name = "province_id")
  private Province province;

  @OneToMany(mappedBy = "seeker", cascade = CascadeType.ALL, orphanRemoval = false)
  @JsonBackReference
  private List<Resume> resumes;
}
