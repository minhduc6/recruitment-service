package vn.unigap.api.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true,  exclude = {"seeker","jobFields", "provinces"})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "resume")
public class Resume extends BaseEntity implements Serializable{
    private String careerObj;
    private String title;
    private Integer salary;

    @Column(name = "provinces")
    private String dataProvince;

    @Column(name = "fields")
    private String dataFields;

    @ManyToOne
    @JoinColumn(name="seeker_id", nullable=true)
    @JsonManagedReference
    private Seeker seeker;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "resume_jobfield",
            joinColumns = @JoinColumn(name = "resume_id"),
            inverseJoinColumns = @JoinColumn(name = "job_field_id")
    )
    @JsonManagedReference
    private Set<JobField> jobFields = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "resume_province",
            joinColumns = @JoinColumn(name = "resume_id"),
            inverseJoinColumns = @JoinColumn(name = "province_id")
    )
    @JsonManagedReference
    private Set<Province> provinces = new HashSet<>();


    // Method to add a Province to the job
    public void addProvince(Province province) {
        this.provinces.add(province);
        province.getResumes().add(this);
    }

    // Method to remove a Province from the job
    public void removeProvince(Province province) {
        this.provinces.remove(province);
        province.getResumes().add(this);
    }


    // Method to add a JobField to the job
    public void addJobField(JobField jobField) {
        this.jobFields.add(jobField);
        jobField.getResumes().add(this);
    }

    // Method to remove a JobField from the job
    public void removeJobField(JobField jobField) {
        this.jobFields.remove(jobField);
        jobField.getResumes().remove(this);
    }

    // Method to clear all JobFields associated with the job
    public void clearJobFields() {
        for (JobField jobField : this.jobFields) {
            jobField.getResumes().remove(this);
        }
        this.jobFields.clear();
    }

}
