package vn.nguyenanhtuan.eventapp.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name="regis_event")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
//    @ManyToOne
//    @JoinColumn(name="user_id")
//    User user;
//
//    @ManyToOne
//    @JoinColumn(name="event_id")
//    Event event;
    int user_id;
    int event_id;
    String status;
}
