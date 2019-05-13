package je.dvs.echo.registrationPoolapi.Domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RegistrationNumber implements Serializable {


    private String registrationnumber;

    private Long owner_id;

    private String classification;

    public RegistrationNumber(){};


    public RegistrationNumber(String registrationnumber, Long owner_id, String classification) {
        this.registrationnumber = registrationnumber;
        this.owner_id = owner_id;
        this.classification = classification;
    }

    @Override
    public String toString() {
        return "{" +
                "registrationnumber='" + registrationnumber + '\'' +
                ", owner_id=" + owner_id +
                ", classification='" + classification + '\'' +
                '}';
    }
}
