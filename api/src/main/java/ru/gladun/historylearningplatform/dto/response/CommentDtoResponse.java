package ru.gladun.historylearningplatform.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDtoResponse {

    private Long id;

    private String content;

    private String date;

    private Long userId;

    private Long articleId;

}
