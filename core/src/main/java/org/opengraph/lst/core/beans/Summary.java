package org.opengraph.lst.core.beans;

import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author ravi
 *
 * Summary for a request id
 */
public class Summary {
    private String id;
    private String flow;
    private Map<String, Map<String, String>> data;
    private Set<String> progress;
    private Map<String, Timeline> timelines;

    public Summary() {
        progress = new HashSet<>();
        timelines = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }

    public Map<String, Map<String, String>> getData() {
        return data;
    }

    public void setData(Map<String, Map<String, String>> data) {
        this.data = data;
    }

    public Set<String> getProgress() {
        return progress;
    }

    public void setProgress(Set<String> progress) {
        this.progress = progress;
    }

    public Map<String, Timeline> getTimelines() {
        return timelines;
    }

    public void setTimelines(Map<String, Timeline> timelines) {
        this.timelines = timelines;
    }

    public boolean isIncomplete() {
        return !CollectionUtils.isEmpty(this.progress);
    }

    public static class Timeline {

        private LocalDateTime start;
        private LocalDateTime end;
        private long timeToComplete;

        public Timeline() { }

        public LocalDateTime getStart() {
            return start;
        }

        public void setStart(LocalDateTime start) {
            this.start = start;
        }

        public LocalDateTime getEnd() {
            return end;
        }

        public void setEnd(LocalDateTime end) {
            this.end = end;
        }

        public long getTimeToComplete() {
            return timeToComplete;
        }

        public void setTimeToComplete(long timeToComplete) {
            this.timeToComplete = timeToComplete;
        }

        public Timeline(LocalDateTime start, LocalDateTime end) {
            this.start = start;
            this.end = end;
            if (this.start != null && this.end !=null) {
                this.timeToComplete = this.start.until(this.end, ChronoUnit.MILLIS);
            }
        }
    }
}
