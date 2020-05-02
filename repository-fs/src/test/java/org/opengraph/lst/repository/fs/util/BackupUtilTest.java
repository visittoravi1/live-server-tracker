package org.opengraph.lst.repository.fs.util;

import org.junit.AfterClass;
import org.junit.Test;
import org.opengraph.lst.core.beans.Stat;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Comparator;

import static org.junit.Assert.assertTrue;

public class BackupUtilTest {

    private static final String BACKUP_DIR = "/home/ravi/Documents/backup/test";
    private BackupUtil util = new BackupUtil(BACKUP_DIR);

    @AfterClass
    public static void tearDown() throws IOException {
        Files.walk(Path.of(BACKUP_DIR)).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
    }

    @Test
    public void testInit() throws IOException {
        util.init();
        Path backupPath = Path.of(BACKUP_DIR);
        assertTrue(Files.exists(backupPath));
        assertTrue(Files.isDirectory(backupPath));
        Path todayDatePath = Path.of(BACKUP_DIR, LocalDate.now().toString());
        assertTrue(Files.exists(todayDatePath));
        assertTrue(Files.isDirectory(todayDatePath));
    }

    @Test
    public void testHandleBackup() {
        MultiValueMap<String, Stat> backup = new LinkedMultiValueMap<>();
        backup.add("abc", new Stat());
        backup.add("bcd", new Stat());
        util.handleBackup(backup);
    }
}
