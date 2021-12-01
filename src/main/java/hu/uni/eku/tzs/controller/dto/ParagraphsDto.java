package hu.uni.eku.tzs.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParagraphsDto {

    private int id;

    private int paragraphNum;

    private String plainText;

    private int characterId;

    private int chapterId;

}
