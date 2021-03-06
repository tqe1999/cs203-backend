package csd.cs203project.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class NewsArticle {
    @Id @GeneratedValue
    private Long id;

    private String title;
    @Lob
    private String description;
    private LocalDate date;
    private String url;

    @Lob
    private String imageUrl;

    public NewsArticle(String title, String description, LocalDate date, String url, String imageUrl) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.url = url;
        this.imageUrl = imageUrl;
    }
}
