package fit.se2.medicarehub.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "ChatbotInteraction")
@Data
public class Chatbot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interactionID;

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private User user;

    private String message;
    private String response;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
}
