package vn.unigap.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "province")
public class Province implements Serializable {
    @Id
    private Integer id;

    private String name;

    private String slug;

    @OneToMany(mappedBy="province")
    private Set<Employer> employers;

    @OneToMany(mappedBy="province")
    private Set<Seeker> seekers;

    @ManyToMany(mappedBy = "provinces")
    @JsonBackReference
    private Set<Job> jobs = new HashSet<>();

    @ManyToMany(mappedBy = "provinces")
    @JsonBackReference
    private Set<Resume> resumes = new HashSet<>();
}
