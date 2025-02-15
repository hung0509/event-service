package vn.nguyenanhtuan.eventapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="event")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name ="title", precision = 100)
    String title;

    @Column(name = "description")
    String description;

    @Column(name ="startDate")
    Date startDate;

    @Column(name ="endDate")
    Date endDate;

    @Column(name = "status")
    String status;

    @Column(name = "totalSeats")
    int totalSeats;

    @Column(name = "availableSeats")
    int availableSeats;

    @Column(name = "createAt")
    Date createAt;

    @Column(name = "imageUrl")
    String imageUrl;

    @Column(name = "bannerUrl")
    String bannerUrl;

    @Column(name = "location")
    String location;

    @Column(name = "comment")
    String comment;

    @ManyToMany
    @JoinTable(
            name = "regis_event",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonManagedReference
    List<User> users;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    @JsonBackReference
    Faculty faculty;
}
