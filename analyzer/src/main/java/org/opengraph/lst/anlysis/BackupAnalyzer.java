package org.opengraph.lst.anlysis;

import org.opengraph.lst.core.beans.Stat;
import org.opengraph.lst.core.beans.Summary;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ravi
 *
 * Analyzes Backup
 */
public class BackupAnalyzer extends Analyzer {

    public List<Summary> analyzeBackup(MultiValueMap<String, Stat> backup) {
        return backup.entrySet().stream().map(this::createSummary).filter(Summary::isIncomplete).collect(Collectors.toList());
    }
}
