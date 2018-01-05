package com.jeonguk.kafka.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Book {
    
    public enum Genre {
        FANTASY, HORROR, ROMANCE, THRILLER
    }

    private long bookId;
    private String title;
    private Genre genre;
    
}
