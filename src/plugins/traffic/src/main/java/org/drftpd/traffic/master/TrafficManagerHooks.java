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
package org.drftpd.traffic.master;

import org.drftpd.common.extensibility.CommandHook;
import org.drftpd.common.extensibility.HookType;
import org.drftpd.master.commands.CommandRequest;
import org.drftpd.master.commands.CommandRequestInterface;
import org.drftpd.master.commands.dataconnection.DataConnectionHandler;
import org.drftpd.master.usermanager.NoSuchUserException;
import org.drftpd.master.usermanager.UserFileException;

/**
 * @author CyBeR
 * @version $Id: TrafficManagerHooks.java 1925 2009-06-15 21:46:05Z CyBeR $
 */

public class TrafficManagerHooks {

    @CommandHook(commands = "doSTOR", type = HookType.PRE)
    public CommandRequestInterface doTrafficManagerSTOR(CommandRequest request) {
        try {
            for (TrafficType trafficType : TrafficManager.getTrafficManager().getTrafficTypes()) {
                if (trafficType.getUpload()) {
                    if ((trafficType.checkInclude(request.getCurrentDirectory().getPath())) && (!trafficType.checkExclude(request.getCurrentDirectory().getPath())) && (trafficType.getPerms().check(request.getUserObject()))) {
                        request.setObject(DataConnectionHandler.MAX_XFER_SPEED, trafficType.getMaxSpeed());
                        request.setObject(DataConnectionHandler.MIN_XFER_SPEED, trafficType.getMinSpeed());
                        break;
                    }
                }
            }
        } catch (RuntimeException e) {
            // No Traffic Manager Loaded - IGNORE
        } catch (NoSuchUserException e) {
            // User does not exist - shouldn't happen, but ignore
        } catch (UserFileException e) {
            // User problem - ignore
        }

        return request;
    }

    @CommandHook(commands = "doRETR", type = HookType.PRE)
    public CommandRequestInterface doTrafficManagerRETR(CommandRequest request) {
        try {
            for (TrafficType trafficType : TrafficManager.getTrafficManager().getTrafficTypes()) {
                if (trafficType.getDownload()) {
                    if ((trafficType.checkInclude(request.getCurrentDirectory().getPath())) && (!trafficType.checkExclude(request.getCurrentDirectory().getPath())) && (trafficType.getPerms().check(request.getUserObject()))) {
                        request.setObject(DataConnectionHandler.MAX_XFER_SPEED, trafficType.getMaxSpeed());
                        request.setObject(DataConnectionHandler.MIN_XFER_SPEED, trafficType.getMinSpeed());
                        break;
                    }
                }
            }
        } catch (RuntimeException e) {
            // No Traffic Manager Loaded - IGNORE
        } catch (NoSuchUserException e) {
            // User does not exist - shouldn't happen, but ignore
        } catch (UserFileException e) {
            // User problem - ignore
        }

        return request;
    }
}