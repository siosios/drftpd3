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
package org.drftpd.master.commands.pre;

import org.drftpd.master.sections.SectionInterface;
import org.drftpd.master.vfs.DirectoryHandle;

/**
 * @author scitz0
 * @version $Id$
 */
public class PreEvent {

    private final DirectoryHandle _dir;
    private final SectionInterface _section;
    private final String _files;
    private final String _bytes;

    public PreEvent(DirectoryHandle dir, SectionInterface section, String files, String bytes) {
        _dir = dir;
        _section = section;
        _files = files;
        _bytes = bytes;
    }

    public DirectoryHandle getDir() {
        return _dir;
    }

    public SectionInterface getSection() {
        return _section;
    }

    public String getFiles() {
        return _files;
    }

    public String getBytes() {
        return _bytes;
    }

}
