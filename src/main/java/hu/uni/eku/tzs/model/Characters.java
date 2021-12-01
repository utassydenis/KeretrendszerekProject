package hu.uni.eku.tzs.model;


import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Characters {

    private int id;

    private String charName;

    private String abbreviation;

    private String description;

}
