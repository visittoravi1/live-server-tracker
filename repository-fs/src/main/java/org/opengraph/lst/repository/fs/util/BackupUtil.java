package org.opengraph.lst.repository.fs.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.opengraph.lst.core.beans.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.util.MultiValueMap;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author ravi
 *
 * Backup Util
 */
public class BackupUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(BackupUtil.class);

    /**
     * directory to keep backup in
     */
    private String backupDir;

    @PostConstruct
    public void init() throws IOException {
        createTodayFolder();
    }

    /**
     * Constructor to initialize with a backupDir
     * @param backupDir
     */
    public BackupUtil(@NonNull String backupDir) {
        if (!backupDir.endsWith(File.separator)) {
            this.backupDir = backupDir + File.separator;
        }
    }

    public void handleBackup(@NonNull MultiValueMap<String, Stat> backup) {
        try {
            String json = new ObjectMapper().writeValueAsString(backup);
            FileWriter fw = new FileWriter(backupName());
            fw.write(json);
            fw.flush();
            fw.close();
        } catch (JsonProcessingException ex) {
            LOGGER.error("Error writing backup as json", ex);
        } catch (IOException ex) {
            LOGGER.error("Error writing json to backup file", ex);
        }
    }

    private String backupName() {
        return this.backupDir + todayFolderName() + File.separator + "backup-" + LocalDateTime.now().toString() + ".json";
    }

    private String todayFolderName() {
        return LocalDate.now().toString();
    }

    private void createTodayFolder() throws IOException {
        String folderName = todayFolderName();
        Path path = Path.of(backupDir);
        if (Files.notExists(path)) {
            Files.createDirectory(path);
        }
        Path todayFolderPath = Path.of(backupDir, folderName);
        if (Files.notExists(todayFolderPath)) {
            Files.createDirectory(todayFolderPath);
        }
    }
}
