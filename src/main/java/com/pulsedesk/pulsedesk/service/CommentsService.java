package com.pulsedesk.pulsedesk.service;

import com.pulsedesk.pulsedesk.exception.ResourceNotFoundException;
import com.pulsedesk.pulsedesk.model.Comment;
import com.pulsedesk.pulsedesk.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentsService {
    private final CommentRepository commentRepository;

    public CommentsService(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    public Comment saveComment(Comment comment){
        return commentRepository.save(comment);
    }

    public List<Comment> getAllComments(){
        return commentRepository.findAll();
    }

    public Comment getCommentById(Long id){
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with an id:" + id));
    }
}
