package board.Domain.Entity.board;

import board.Domain.Entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table( name= "board")
@Getter@Setter@ToString
@AllArgsConstructor @NoArgsConstructor @Builder
public class BoardEntity extends BaseTimeEntity {
    @Id //pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "b_num")
    private int b_num;

    @Column(name = "b_title")
    private String b_title;

    @Column(name ="b_contents")
    private String b_contents;

    @Column(name = "b_write")
    private String b_write;

    @Column(name= "b_password")
    private String b_password;

    @Column(name= "b_view")
    private  int b_view;


}
