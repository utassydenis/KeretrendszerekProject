package hu.uni.eku.tzs.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Paragraphs {

    private int id;

    private int paragraphNum;

    private String plainText;

    private int character_ID;

    private int chapter_ID;
}
