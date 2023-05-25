package ru.gladun.historylearningplatform.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDtoResponse {

    private Long id;

    private String title;

    private String content;

    private String date;

    private Long userId;

    private List<CommentDtoResponse> comments;

}
