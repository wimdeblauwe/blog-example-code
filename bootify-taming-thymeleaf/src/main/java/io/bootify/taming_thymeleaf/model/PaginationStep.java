package io.bootify.taming_thymeleaf.model;


public class PaginationStep {

    private boolean active = false;
    private boolean disabled = false;
    private String label;
    private String url;

    public boolean getActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(final boolean disabled) {
        this.disabled = disabled;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

}
