package com.diplom.sptor.timeline.domain;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "start_date",
        "end_date",
        "background",
        "text",
        "type",
        "unique_id"
})
public class Event {

    @JsonProperty("start_date")
    private StartDate startDate;
    @JsonProperty("end_date")
    private EndDate endDate;
    @JsonProperty("background")
    private Background background;
    @JsonProperty("text")
    private Text text;
    @JsonProperty("type")
    private String type;
    @JsonProperty("unique_id")
    private String uniqueId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("start_date")
    public StartDate getStartDate() {
        return startDate;
    }

    @JsonProperty("start_date")
    public void setStartDate(StartDate startDate) {
        this.startDate = startDate;
    }

    @JsonProperty("end_date")
    public EndDate getEndDate() {
        return endDate;
    }

    @JsonProperty("end_date")
    public void setEndDate(EndDate endDate) {
        this.endDate = endDate;
    }

    @JsonProperty("background")
    public Background getBackground() {
        return background;
    }

    @JsonProperty("background")
    public void setBackground(Background background) {
        this.background = background;
    }

    @JsonProperty("text")
    public Text getText() {
        return text;
    }

    @JsonProperty("text")
    public void setText(Text text) {
        this.text = text;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("unique_id")
    public String getUniqueId() {
        return uniqueId;
    }

    @JsonProperty("unique_id")
    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("startDate", startDate).append("endDate", endDate).append("background",
                background).append("text", text).append("type", type).append("uniqueId", uniqueId)
                .append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(startDate).append(text).append(additionalProperties).append(background)
                .append(endDate).append(type).append(uniqueId).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Event) == false) {
            return false;
        }
        Event rhs = ((Event) other);
        return new EqualsBuilder().append(startDate, rhs.startDate).append(text, rhs.text).append(additionalProperties,
                rhs.additionalProperties).append(background, rhs.background).append(endDate, rhs.endDate).append(type,
                rhs.type).append(uniqueId, rhs.uniqueId).isEquals();
    }

}