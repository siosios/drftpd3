/*
 * This file is part of DrFTPD, Distributed FTP Daemon.
 *
 * DrFTPD is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * DrFTPD is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with DrFTPD; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.drftpd.imdb.master.announce;

import org.bushe.swing.event.annotation.AnnotationProcessor;
import org.bushe.swing.event.annotation.EventSubscriber;
import org.drftpd.imdb.master.event.IMDBEvent;
import org.drftpd.master.sitebot.AbstractAnnouncer;
import org.drftpd.master.sitebot.AnnounceWriter;
import org.drftpd.master.sitebot.config.AnnounceConfig;
import org.drftpd.master.util.ReplacerUtils;

import java.util.ResourceBundle;

/**
 * @author lh
 */
public class IMDBAnnouncer extends AbstractAnnouncer {

    private AnnounceConfig _config;

    private ResourceBundle _bundle;

    public void initialise(AnnounceConfig config, ResourceBundle bundle) {
        _config = config;
        _bundle = bundle;

        // Subscribe to events
        AnnotationProcessor.process(this);
    }

    public void stop() {
        // The plugin is unloading so stop asking for events
        AnnotationProcessor.unprocess(this);
    }

    public String[] getEventTypes() {
        return new String[]{"imdb"};
    }

    public void setResourceBundle(ResourceBundle bundle) {
        _bundle = bundle;
    }

    @EventSubscriber
    public void onIMDBEvent(IMDBEvent event) {
        AnnounceWriter writer = _config.getPathWriter("imdb", event.getDir());
        // Check we got a writer back, if it is null do nothing and ignore the event
        if (writer != null) {
            sayOutput(ReplacerUtils.jprintf("imdb.announce", event.getEnv(), _bundle), writer);
        }
    }
}
