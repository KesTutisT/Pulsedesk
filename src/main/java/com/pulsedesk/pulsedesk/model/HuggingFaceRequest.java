package com.pulsedesk.pulsedesk.model;

import java.util.List;

public class HuggingFaceRequest {
    private String inputs;
    private Parameters parameters;
    private Options options;


    public HuggingFaceRequest(String text, List<String> labels, boolean multi_label, boolean waitForModel){
        this.inputs = text;
        this.parameters = new Parameters(labels, multi_label);
        this.options = new Options(waitForModel);
    }

    public String getInputs() { return this.inputs; };
    public Parameters getParameters() { return parameters; };

    public static class Parameters {
        private List<String> candidate_labels;
        private boolean multi_label;

        public Parameters(List<String> labels, boolean multiLabel) {
            this.candidate_labels = labels;
            this.multi_label = multiLabel;
        }

        public List<String> getCandidate_labels() {
            return candidate_labels;
        }

        public boolean isMulti_label() {
            return multi_label;
        }
    }
    public static class Options {
        private boolean wait_for_model;

        public Options(boolean wait_for_model) {
            this.wait_for_model = wait_for_model;
        }
    }
}
