package vn.unigap.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;


@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employer")
public class Employer extends BaseEntity implements Serializable {

    private String email;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "province_id")
    @JsonManagedReference
    private Province province;

    @OneToMany(mappedBy = "employer", cascade = CascadeType.ALL, orphanRemoval = false)
    @JsonBackReference
    private List<Job> jobs;

    private String description;

    @Override
    public String toString() {
        return "Employer{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}