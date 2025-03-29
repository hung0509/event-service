package vn.nguyenanhtuan.eventapp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name="guest")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name ="email", unique=true, nullable = false, precision = 50)
    String email;

    @Column(name ="username")
    String username;

    @Column(name = "phone")
    String phone;
}
