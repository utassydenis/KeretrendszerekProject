package hu.uni.eku.tzs.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Chapters {

    private int id;

    private int act;

    private int scene;

    private String description;

    private int work_ID;

}
