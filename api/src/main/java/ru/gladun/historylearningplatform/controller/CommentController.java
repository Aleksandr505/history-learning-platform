package ru.gladun.historylearningplatform.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.gladun.historylearningplatform.dto.request.ArticleDtoRequest;
import ru.gladun.historylearningplatform.dto.request.CommentDtoRequest;
import ru.gladun.historylearningplatform.dto.response.ArticleDtoResponse;
import ru.gladun.historylearningplatform.dto.response.CommentDtoResponse;
import ru.gladun.historylearningplatform.exception.ServerException;
import ru.gladun.historylearningplatform.service.CommentService;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:8888")
@AllArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping(value = "/comments", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CommentDtoResponse postComment(@Valid @RequestBody CommentDtoRequest commentDtoRequest) throws ServerException {
        return commentService.postComment(commentDtoRequest);
    }

    @GetMapping(value = "/comments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommentDtoResponse getComment(@PathVariable long id) throws ServerException {
        return commentService.getComment(id);
    }

    @PutMapping(value = "/comments/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CommentDtoResponse editComment(@PathVariable long id, @Valid @RequestBody CommentDtoRequest commentDtoRequest) throws ServerException {
        return commentService.editComment(id, commentDtoRequest);
    }

}
