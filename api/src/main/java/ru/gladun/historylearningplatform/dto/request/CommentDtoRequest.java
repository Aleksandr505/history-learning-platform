package ru.gladun.historylearningplatform.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDtoRequest {

    @NotEmpty(message = "{content.notempty}")
    private String content;

    @NotEmpty(message = "{date.notempty}")
    private String date;

    @Min(value = 1, message = "{userId.min}")
    private Long userId;

    @Min(value = 1, message = "{articleId.min}")
    private Long articleId;

}
