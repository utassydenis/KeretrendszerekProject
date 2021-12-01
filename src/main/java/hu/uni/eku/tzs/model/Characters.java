package hu.uni.eku.tzs.model;


import lombok.*;

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
