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

    @Column(name ="username", nullable = false)
    String username;

    @Column(name = "phone")
    String phone;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @JsonManagedReference
    Role role;

    @ManyToMany(mappedBy = "users")
    List<Event> events;
}
