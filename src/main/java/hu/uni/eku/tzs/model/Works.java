package hu.uni.eku.tzs.model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Works {

    private int id;

    private String title;

    private String longTitle;

    private int date;

    private String genreType;

}
