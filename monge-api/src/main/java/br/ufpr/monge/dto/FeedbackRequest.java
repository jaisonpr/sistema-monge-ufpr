package br.ufpr.monge.dto;

public class FeedbackRequest {
    private String feedback;
    
    // Construtores
    public FeedbackRequest() {}
    
    public FeedbackRequest(String feedback) {
        this.feedback = feedback;
    }
    
    // Getters e Setters
    public String getFeedback() {
        return feedback;
    }
    
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}