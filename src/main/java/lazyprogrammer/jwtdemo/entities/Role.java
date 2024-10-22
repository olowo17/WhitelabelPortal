package lazyprogrammer.jwtdemo.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Builder
@Table(name = "Roles")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
//@JsonIgnoreProperties(ignoreUnknown = true)
public class Role{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    Long institutionId;
    private String name;
    private String description;
    private boolean authorizer;
    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date dateUpdated;
}
