package com.pulsedesk.pulsedesk.controller;

import com.pulsedesk.pulsedesk.model.Comment;
import com.pulsedesk.pulsedesk.service.CommentsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentsController {
    private final CommentsService commentsService;

    public CommentsController(CommentsService commentsService){
        this.commentsService = commentsService;
    }

    // POST /comments
    @PostMapping
    public Comment createComment(@RequestBody Comment comment){
        return commentsService.saveComment(comment);
    }

    // GET /comments
    @GetMapping
    public List<Comment> getAllComments() {
        return commentsService.getAllComments();
    }

    // GET /comments/{id}
    @GetMapping("/{id}")
    public Comment getComment(@PathVariable Long id){
        return commentsService.getCommentById(id);
    }
}
