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
        "year",
        "month",
        "day",
        "hour",
        "minute",
        "second",
        "millisecond",
        "format"
})
public class EndDate {

    @JsonProperty("year")
    private String year;
    @JsonProperty("month")
    private String month;
    @JsonProperty("day")
    private String day;
    @JsonProperty("hour")
    private String hour;
    @JsonProperty("minute")
    private String minute;
    @JsonProperty("second")
    private String second;
    @JsonProperty("millisecond")
    private String millisecond;
    @JsonProperty("format")
    private String format;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("year")
    public String getYear() {
        return year;
    }

    @JsonProperty("year")
    public void setYear(String year) {
        this.year = year;
    }

    @JsonProperty("month")
    public String getMonth() {
        return month;
    }

    @JsonProperty("month")
    public void setMonth(String month) {
        this.month = month;
    }

    @JsonProperty("day")
    public String getDay() {
        return day;
    }

    @JsonProperty("day")
    public void setDay(String day) {
        this.day = day;
    }

    @JsonProperty("hour")
    public String getHour() {
        return hour;
    }

    @JsonProperty("hour")
    public void setHour(String hour) {
        this.hour = hour;
    }

    @JsonProperty("minute")
    public String getMinute() {
        return minute;
    }

    @JsonProperty("minute")
    public void setMinute(String minute) {
        this.minute = minute;
    }

    @JsonProperty("second")
    public String getSecond() {
        return second;
    }

    @JsonProperty("second")
    public void setSecond(String second) {
        this.second = second;
    }

    @JsonProperty("millisecond")
    public String getMillisecond() {
        return millisecond;
    }

    @JsonProperty("millisecond")
    public void setMillisecond(String millisecond) {
        this.millisecond = millisecond;
    }

    @JsonProperty("format")
    public String getFormat() {
        return format;
    }

    @JsonProperty("format")
    public void setFormat(String format) {
        this.format = format;
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
        return new ToStringBuilder(this).append("year", year).append("month", month).append("day", day).
                append("hour", hour).append("minute", minute).append("second", second).append("millisecond", millisecond)
                .append("format", format).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(minute).append(second).append(additionalProperties).append(millisecond).
                append(month).append(year).append(format).append(hour).append(day).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof EndDate) == false) {
            return false;
        }
        EndDate rhs = ((EndDate) other);
        return new EqualsBuilder().append(minute, rhs.minute).append(second, rhs.second).append(additionalProperties,
                rhs.additionalProperties).append(millisecond, rhs.millisecond).append(month, rhs.month).append(year,
                rhs.year).append(format, rhs.format).append(hour, rhs.hour).append(day, rhs.day).isEquals();
    }

}