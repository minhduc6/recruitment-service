package vn.unigap.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "job_field")
public class JobField extends BaseEntity implements Serializable {
  private String name;

  private String slug;

  @ManyToMany(mappedBy = "jobFields")
  @JsonBackReference
  private Set<Job> jobs = new HashSet<>();

  @ManyToMany(mappedBy = "jobFields")
  @JsonBackReference
  private Set<Resume> resumes = new HashSet<>();

  @Override
  public String toString() {
    return "JobField{" + "id=" + getId() + ", name='" + name + '\'' + '}';
  }
}
