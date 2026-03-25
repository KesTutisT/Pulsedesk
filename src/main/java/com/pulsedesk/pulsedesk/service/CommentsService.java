package com.pulsedesk.pulsedesk.service;

import com.pulsedesk.pulsedesk.exception.ResourceNotFoundException;
import com.pulsedesk.pulsedesk.model.Comment;
import com.pulsedesk.pulsedesk.model.Ticket;
import com.pulsedesk.pulsedesk.repository.CommentRepository;
import com.pulsedesk.pulsedesk.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.pulsedesk.pulsedesk.service.HuggingFaceService;
import com.pulsedesk.pulsedesk.utils.ClassificationLabels;

import java.util.List;
import java.util.Map;

@Service
public class CommentsService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private HuggingFaceService huggingFaceService;

    @Autowired
    private TicketRepository ticketRepository;

    public Comment saveComment(Comment comment){
        processComment(comment);
        return commentRepository.save(comment);
    }

    public List<Comment> getAllComments(){
        return commentRepository.findAll();
    }

    public Comment getCommentById(Long id){
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with an id:" + id));
    }


    public Comment processComment(Comment comment) {
        //Check if the comment is a ticket worthy
        List<Map<String, Object>> relevanceCheck = huggingFaceService.classify(comment.getText(),
                List.of("real user feedback about a software product or service",
                        "nonsense, gibberish, or unrelated to software"));
        if(relevanceCheck == null) return comment;
        String relevanceLabel = (String) relevanceCheck.get(0).get("label");
        double relevanceScore = (double) relevanceCheck.get(0).get("score");

        if (relevanceLabel.equals("nonsense, gibberish, or unrelated to software") && relevanceScore >= 0.75) {
            return comment;
        }
        else{
            relevanceLabel = "real user feedback about a software product or service";
        }
        List<Map<String, Object>> ticketCheck = huggingFaceService.classify(comment.getText(), List.of("problem or complaint",
                "compliment or praise"));
        if (ticketCheck == null) return comment;

        String topLabel = (String) ticketCheck.get(0).get("label");
        topLabel = topLabel
                .replace("problem or complaint", "support ticket")
                .replace("compliment or praise", "compliment");

        if (topLabel.equals("support ticket")) {
            List<Map<String, Object>> categoryResult = huggingFaceService.classify(comment.getText(),
                    List.of("software or button not working as expected",
                            "payment or invoice issue",
                            "new feature or improvement request",
                            "problem to my specific account, such as being locked, banned, or losing profile data",
                            "other"));
            if(categoryResult == null) return comment;
            String category = (String) categoryResult.get(0).get("label");
            category = category
                    .replace("software or button not working as expected", "bug")
                    .replace("payment or invoice issue", "billing")
                    .replace("new feature or improvement request", "feature")
                    .replace("problem to my specific account, such as being locked, banned, or losing profile data", "account")
                    .replace("other", "other");
            //Check if the ticket contains crutial bug or just medium priority
            List<Map<String, Object>> criticalCheck = huggingFaceService.classify(comment.getText(),
                    List.of("critical system failure or data loss", "minor or moderate issue"));
            if(criticalCheck == null) return comment;
            String criticalLabel = (String) criticalCheck.get(0).get("label");

            String priority;
            if (criticalLabel.equals("critical system failure or data loss")) {
                priority = "high";
            } else {
                //Check if the ticket is about functional bug or just cosmetic (low priority)
                List<Map<String, Object>> cosmeticCheck = huggingFaceService.classify(comment.getText(),
                        List.of("something is not working or broken",
                                "visual or cosmetic issue like font color or spelling"));
                String cosmeticLabel = (String) cosmeticCheck.get(0).get("label");
                priority = cosmeticLabel.equals("visual or cosmetic issue like font color or spelling") ? "low" : "medium";
            }
            String titleText = comment.getText().length() > 50 ? comment.getText().substring(0, 50) + "..." : comment.getText();
            String title = "[" + category.toUpperCase() + "][" + priority.toUpperCase() + "] " + titleText;
            String summary = comment.getText().length() > 150 ? comment.getText().substring(0, 150) + "..." : comment.getText();

            Ticket ticket = new Ticket();
            //Setting ticket information
            {ticket.setText(comment.getText()); ticket.setAuthor(comment.getAuthor()); ticket.setTitle(title); ticket.setSummary(summary); ticket.setPriority(priority); ticket.setCategory(category);}
            ticketRepository.save(ticket);
        }

        return comment;
    }
}
