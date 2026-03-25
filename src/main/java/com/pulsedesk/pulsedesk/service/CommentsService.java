package com.pulsedesk.pulsedesk.service;

import com.pulsedesk.pulsedesk.exception.ResourceNotFoundException;
import com.pulsedesk.pulsedesk.model.Comment;
import com.pulsedesk.pulsedesk.model.Ticket;
import com.pulsedesk.pulsedesk.repository.CommentRepository;
import com.pulsedesk.pulsedesk.repository.TicketRepository;
import jakarta.validation.constraints.Min;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.pulsedesk.pulsedesk.service.HuggingFaceService;
import static com.pulsedesk.pulsedesk.utils.ClassificationLabels.*;

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

    @Transactional
    public Comment saveComment(Comment comment){
        Comment savedComment = commentRepository.saveAndFlush(comment);
        processComment(savedComment);
        return savedComment;
    }

    public List<Comment> getAllComments(){
        return commentRepository.findAll();
    }

    public Comment getCommentById(Long id){
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with an id:" + id));
    }

    public void processComment(Comment comment){
        if(!isFeedback(comment.getText())){
            return;
        }
        if(isTicket(comment.getText())){
            createTicket(comment);
        }
    }

    //Not need (functionally) but really helps when using this model to get more accurate results
    private boolean isFeedback(String text){
        List<Map<String, Object>> result = huggingFaceService.classify(text,
                List.of(FEEDBACK, NONSENSE));
        if(result == null){
            return false;
        }
        String topLabel = (String) result.getFirst().get("label");
        double topScore = (double) result.getFirst().get("score");
        return !(topLabel.equals(NONSENSE) && topScore >= 0.75);
    }

    private boolean isTicket(String text){
        List<Map<String, Object>> result = huggingFaceService.classify(text,
                List.of(PROBLEM, COMPLIMENT));
        if(result == null){
            return false;
        }
        return PROBLEM.equals(result.getFirst().get("label"));
    }

    private String resolveCategory(String text){
        List<Map<String, Object>> result = huggingFaceService.classify(text,
                List.of(CAT_BUG, CAT_BILLING, CAT_FEATURE, CAT_ACCOUNT, CAT_OTHER));
        if(result == null){
            return "other";
        }
        String topLabel = (String) result.getFirst().get("label");
        return CATEGORY_MAP.getOrDefault(topLabel, "other");
    }

    private String resolvePriority(String text){
        List<Map<String, Object>> result = huggingFaceService.classify(text,
                List.of(CRITICAL, MINOR));
        if(CRITICAL.equals(result.getFirst().get("label"))){
            return "high";
        }

        List<Map<String, Object>> cosmeticResult = huggingFaceService.classify(text,
                List.of(BROKEN, COSMETIC));
        if(cosmeticResult == null){
            return "medium";
        }
        return (COSMETIC.equals(cosmeticResult.getFirst().get("label")) ? "low" : "medium");
    }

    private void createTicket(Comment comment){
        String text = comment.getText();
        String category = resolveCategory(text);
        String priority = resolvePriority(text);

        String titleText = text.length() > 50 ? text.substring(0, 50) + "..." : text;
        String title = "[" + category.toUpperCase() + "] [" + priority.toUpperCase() + "] " + titleText;
        String summary = text.length() > 150 ? text.substring(0, 150) + "..." : text;

        Ticket ticket = new Ticket();
        ticket.setText(text);
        ticket.setCategory(category);
        ticket.setPriority(priority);
        ticket.setTitle(title);
        ticket.setSummary(summary);
        ticket.setAuthor(comment.getAuthor());
        ticketRepository.save(ticket);
    }
}
