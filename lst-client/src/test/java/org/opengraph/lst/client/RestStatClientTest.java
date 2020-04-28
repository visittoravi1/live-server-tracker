package org.opengraph.lst.client;

import org.junit.Test;
import org.opengraph.lst.core.beans.Stat;

import java.time.LocalDateTime;
import java.util.UUID;

public class RestStatClientTest {

    @Test
    public void testSendStat() {
        Stat stat = new Stat();
        stat.setApp("MyApp");
        stat.setFlow("test");
        UUID uuid = UUID.randomUUID();
        stat.setId(uuid.toString());
        stat.setReceivedOn(LocalDateTime.now().minusSeconds(5));
        stat.setType(Stat.Type.START);
        RestStatClient client = new RestStatClient("http://localhost:8080/lst");
        client.sendStat(stat);
    }
}
