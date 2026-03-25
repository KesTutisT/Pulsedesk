package com.pulsedesk.pulsedesk.model;

import java.util.List;

public class HuggingFaceResponse {
    private List<String> labels;
    private List<Double> scores;

    public List<String> getLabels(){ return labels; };
    public List<Double> getScores(){ return scores; };
}
