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
package org.drftpd.master.commands.dataconnection.hooks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.drftpd.common.dynamicdata.KeyNotFoundException;
import org.drftpd.common.extensibility.CommandHook;
import org.drftpd.common.extensibility.HookType;
import org.drftpd.master.GlobalContext;
import org.drftpd.master.commands.CommandRequest;
import org.drftpd.master.commands.CommandResponse;
import org.drftpd.master.commands.dataconnection.DataConnectionHandler;
import org.drftpd.master.event.TransferEvent;
import org.drftpd.master.network.BaseFtpConnection;
import org.drftpd.master.slavemanagement.RemoteSlave;
import org.drftpd.master.vfs.FileHandle;

import java.net.InetAddress;

/**
 * @author djb61
 * @version $Id$
 */
public class DataConnectionPostHooks {
    private static final Logger logger = LogManager.getLogger(DataConnectionPostHooks.class);

    @CommandHook(commands = "doSTOR", priority = 9999999, type = HookType.POST)
    public void doTransferEvent(CommandRequest request, CommandResponse response) {
        if (response.getCode() != 226) {
            // Transfer failed, skip event
            return;
        }
        try {
            FileHandle transferFile = response.getObject(DataConnectionHandler.TRANSFER_FILE);
            String eventType = request.getCommand().equalsIgnoreCase("RETR") ? "RETR" : "STOR";
            if (transferFile.exists() || eventType.equals("RETR")) {
                try {
                    BaseFtpConnection conn = (BaseFtpConnection) request.getSession();
                    RemoteSlave transferSlave = response.getObject(DataConnectionHandler.TRANSFER_SLAVE);
                    InetAddress transferSlaveInetAddr = response.getObject(DataConnectionHandler.TRANSFER_SLAVE_INET_ADDRESS);
                    char transferType = response.getObject(DataConnectionHandler.TRANSFER_TYPE);
                    GlobalContext.getEventService().publishAsync(new TransferEvent(conn, eventType, transferFile,
                            conn.getClientAddress(), transferSlave, transferSlaveInetAddr, transferType));
                } catch (KeyNotFoundException e1) {
                    // one or more bits of information didn't get populated correctly, have to skip the event
                    logger.warn("Unable to fire TransferEvent for [{}]", transferFile, e1);
                }
            }
        } catch (KeyNotFoundException e) {
            // shouldn't have got a 226 response and still ended up here
            logger.error("Unexpected error while we are status '226'", e);
        }
    }
}
