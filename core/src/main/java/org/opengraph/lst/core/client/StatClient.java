package org.opengraph.lst.core.client;

import org.opengraph.lst.core.beans.Stat;

/**
 * @author ravi
 *
 * Client to send stat to webserver
 */
public interface StatClient {

    void sendStat(Stat stat);
}
