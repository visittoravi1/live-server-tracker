package org.opengraph.lst.anlysis;

import org.opengraph.lst.core.beans.AppAnalysis;
import org.opengraph.lst.core.beans.Summary;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Analyze a summary
 */
public class SummaryAnalyzer {

    private Map<String, AppAnalysis> appAnalysis;
    private Map<String, AppAnalysis> overallAnalysis;

    public SummaryAnalyzer() {
        this.appAnalysis = new ConcurrentHashMap<>();
        this.overallAnalysis = new ConcurrentHashMap<>();
    }

    public Map<String, AppAnalysis> getAppAnalysis(Optional<String> app) {
        return app.isEmpty() ? appAnalysis : Map.of(app.get(), appAnalysis.get(app));
    }

    public void analyzeSummary(Summary summary) {
        if (summary.isIncomplete()) {
            return;
        }
        summary.getTimelines().entrySet().forEach(e -> {
            String app = e.getKey();
            appAnalysis.compute(app, (key, val) -> {
                if (val == null) {
                    val = new AppAnalysis(app);
                }
                val.setSum(val.getSum() + e.getValue().getTimeToComplete());
                val.setCount(val.getCount() + 1);
                val.setAverage(val.getSum() / val.getCount());
                if (val.getRange().getEnd() == null || val.getRange().getEnd().isBefore(e.getValue().getEnd())) {
                    val.getRange().setEnd(e.getValue().getEnd());
                }
                if (val.getRange().getStart() == null || val.getRange().getStart().isAfter(e.getValue().getStart())) {
                    val.getRange().setStart(e.getValue().getStart());
                }
                return val;
            });
        });
        computeOverallAnalysis(summary);
    }

    private void computeOverallAnalysis(Summary summary) {
        String flow = summary.getFlow();
        overallAnalysis.compute(flow, (key, val) -> {
            if (val == null) {
                val = new AppAnalysis();
            }
            val.setSum(val.getSum() + summary.getTimelines().values().stream().map(Summary.Timeline::getTimeToComplete).collect(Collectors.summingLong(Long::longValue)));
            val.setCount(val.getCount() + 1);
            val.setAverage(val.getSum() / val.getCount());
            LocalDateTime maxEndDateTime = summary.getTimelines().values().stream().map(Summary.Timeline::getEnd).max(LocalDateTime::compareTo).orElse(null);
            LocalDateTime minStartDateTime = summary.getTimelines().values().stream().map(Summary.Timeline::getStart).min(LocalDateTime::compareTo).orElse(null);
            if (minStartDateTime != null && (val.getRange().getStart() == null || val.getRange().getStart().isAfter(minStartDateTime))) {
                val.getRange().setStart(minStartDateTime);
            }
            if (maxEndDateTime != null && (val.getRange().getEnd() == null || val.getRange().getEnd().isBefore(maxEndDateTime))) {
                val.getRange().setEnd(maxEndDateTime);
            }
            return val;
        });
    }
}
