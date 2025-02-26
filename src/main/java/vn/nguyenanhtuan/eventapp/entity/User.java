package vn.nguyenanhtuan.eventapp.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name="user")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name ="email", unique=true, nullable = false, precision = 50)
    String email;

    @Column(name ="password", nullable = false)
    String password;

    @Column(name ="username")
    String username;

    @Column(name = "phone")
    String phone;

    @Column(name="faculty_name")
    String facultyName;

    @Column(name="faculty_logo")
    String facultyLogo;

    @Column(name="faculty_description")
    String facultyDescription;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @JsonManagedReference
    Role role;

//
//    @OneToMany(mappedBy = "user")
//    List<RegisEvent> regisEvents;
}
