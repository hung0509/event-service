package vn.nguyenanhtuan.eventapp.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name="category")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name="category_name")
    String categoryName;

    @Column(name="category_logo")
    String categoryLogo;

    @OneToMany(mappedBy = "category")
    List<Event> events;
}
