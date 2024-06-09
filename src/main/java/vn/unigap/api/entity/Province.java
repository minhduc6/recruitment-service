package vn.unigap.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
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
    @JsonBackReference
    private Set<Employer> employers;

    @OneToMany(mappedBy="province")
    @JsonBackReference
    private Set<Seeker> seekers;

    @ManyToMany(mappedBy = "provinces")
    @JsonBackReference
    private Set<Job> jobs = new HashSet<>();

    @ManyToMany(mappedBy = "provinces")
    @JsonBackReference
    private Set<Resume> resumes = new HashSet<>();

    // Avoid including collections in hashCode and equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Province province = (Province) o;
        return Objects.equals(id, province.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public String toString() {
        return "Province{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", slug='" + slug + '\'' +
                '}';
    }


}
