package board.Domain.Dto;

import board.Domain.Entity.board.BoardEntity;
import lombok.*;

@Getter@Setter@ToString
@AllArgsConstructor@NoArgsConstructor@Builder
public class BoardDto {
    private int b_num;
    private String b_title;
    private String b_contents;
    private String b_write;
    private String b_createDate;
    private String b_password;
    private int b_view;


    //dto -> entity 메소드로 가는것
    public BoardEntity toentity(){
        return BoardEntity.builder()
                .b_title(this.b_title)
                .b_contents(this.b_contents)
                .b_write(this.b_write)
                .b_password(this.b_password)
                .build();
    }

}
