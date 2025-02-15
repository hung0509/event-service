package vn.nguyenanhtuan.eventapp.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name="faculty")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name="facultyName")
    String facultyName;

    @Column(name="facultyLogo")
    String facultyLogo;

    @OneToMany(mappedBy = "faculty")
    @JsonManagedReference
    List<Event> events;
}
