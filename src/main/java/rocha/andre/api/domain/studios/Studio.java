package rocha.andre.api.domain.studios;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import rocha.andre.api.domain.country.Country;
import rocha.andre.api.domain.studios.DTO.CreateStudioDTO;

import java.util.UUID;

@Table(name = "studios")
@Entity(name = "Studio")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Studio {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private UUID id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;

    public Studio(CreateStudioDTO data) {
        this.country = data.country();
        this.name = data.name();
    }
}