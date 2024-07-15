package vn.unigap.api.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.*;

@EqualsAndHashCode(
    callSuper = true,
    exclude = {"employer", "jobFields", "provinces"})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "jobs")
public class Job extends BaseEntity implements Serializable {

  @Column(name = "title", columnDefinition = "text")
  private String title;

  @Column(name = "quantity")
  private Integer quantity;

  @Column(name = "description", columnDefinition = "text")
  private String description;

  @Column(name = "salary")
  private Integer salary;

  @Column(name = "expired_at")
  private LocalDateTime expired_at;

  @Column(name = "provinces")
  private String dataProvince;

  @Column(name = "fields")
  private String dataFields;

  @ManyToOne
  @JoinColumn(name = "employer_id", nullable = false)
  @JsonManagedReference
  private Employer employer;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "job_jobfield",
      joinColumns = @JoinColumn(name = "job_id"),
      inverseJoinColumns = @JoinColumn(name = "job_field_id"))
  @JsonManagedReference
  private Set<JobField> jobFields = new HashSet<>();

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "job_province",
      joinColumns = @JoinColumn(name = "job_id"),
      inverseJoinColumns = @JoinColumn(name = "province_id"))
  @JsonManagedReference
  private Set<Province> provinces = new HashSet<>();

  // Method to add a Province to the job
  public void addProvince(Province province) {
    this.provinces.add(province);
    province.getJobs().add(this);
  }

  // Method to remove a Province from the job
  public void removeProvince(Province province) {
    this.provinces.remove(province);
    province.getJobs().remove(this);
  }

  // Method to add a JobField to the job
  public void addJobField(JobField jobField) {
    this.jobFields.add(jobField);
    jobField.getJobs().add(this);
  }

  // Method to remove a JobField from the job
  public void removeJobField(JobField jobField) {
    this.jobFields.remove(jobField);
    jobField.getJobs().remove(this);
  }

  // Method to clear all JobFields associated with the job
  public void clearJobFields() {
    for (JobField jobField : this.jobFields) {
      jobField.getJobs().remove(this);
    }
    this.jobFields.clear();
  }

  @Override
  public String toString() {
    return "Job{"
        + "id="
        + getId()
        + ", title='"
        + title
        + '\''
        + ", quantity="
        + quantity
        + ", description='"
        + description
        + '\''
        + ", salary="
        + salary
        + ", expired_at="
        + expired_at
        + '}';
  }
}
