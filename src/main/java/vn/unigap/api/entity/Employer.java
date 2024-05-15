package vn.unigap.api.entity;

import jakarta.persistence.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

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
    private Province province;

    private String description;

}