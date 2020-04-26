package org.opengraph.lst.core.beans;

import java.time.LocalDateTime;

public class Range {
    private LocalDateTime start;
    private LocalDateTime end;

    Range() {}

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
}
