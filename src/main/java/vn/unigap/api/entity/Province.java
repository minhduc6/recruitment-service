package vn.unigap.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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
}
