package hu.uni.eku.tzs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
