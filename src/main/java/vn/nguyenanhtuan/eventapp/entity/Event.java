package vn.nguyenanhtuan.eventapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="event")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor()
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name ="title", precision = 100)
    String title;

    @Column(name = "description")
    String description;

    @Column(name ="startDate")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date startDate;

    @Column(name ="endDate")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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

//    @OneToMany(mappedBy = "event")
//    List<RegisEvent> regisEvents;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference
    Category category;
}
