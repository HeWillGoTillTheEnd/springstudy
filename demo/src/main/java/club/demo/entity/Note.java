package club.demo.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Note extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;

    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private ClubMember writer;

    private LocalDateTime regDate = LocalDateTime.now();
    private LocalDateTime modDate = LocalDateTime.now();

    public void changeTitle(String title){
        this.title = title;
    }
    public void changeContent(String content){
        this.content = content;
    }

}
