package by.bsuir.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "ratings", schema = "diplom")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp date;
    private Double empRating;
    private Double manRating;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employeesByEmployeeId;
    @ManyToOne
    @JoinColumn(name = "motivation_id", referencedColumnName = "id")
    private Motivation motivationsByMotivationId;

}
