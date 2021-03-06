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
package org.drftpd.master.sitebot;

import org.drftpd.master.GlobalContext;
import org.drftpd.master.sections.SectionInterface;
import org.drftpd.master.sitebot.config.PathMatcher;
import org.drftpd.master.vfs.DirectoryHandle;
import org.drftpd.master.vfs.InodeHandle;

import java.util.ArrayList;

/**
 * @author djb61
 * @version $Id$
 */
public class AnnounceWriter {

    private final PathMatcher _matcher;

    private final ArrayList<OutputWriter> _writers;

    private final String _sectionName;

    public AnnounceWriter(PathMatcher matcher, ArrayList<OutputWriter> writers, String sectionName) {
        _matcher = matcher;
        _writers = writers;
        _sectionName = sectionName;
    }

    public ArrayList<OutputWriter> getOutputWriters() {
        return _writers;
    }

    public boolean pathMatches(InodeHandle inode) {
        return _matcher.checkPath(inode);
    }

    public boolean sectionMatches(DirectoryHandle dir) {
        if (_sectionName.equals("*")) {
            // This is a catchall for sections so must be true
            return true;
        }
        return _sectionName.equalsIgnoreCase(GlobalContext.getGlobalContext()
                .getSectionManager().lookup(dir).getName());
    }

    public String getSectionName(DirectoryHandle dir) {
        if (_sectionName == null || _sectionName.equals("*")) {
            if (dir.getParent().isRoot()) {
                return "/";
            }
            SectionInterface section =
                    GlobalContext.getGlobalContext().getSectionManager().lookup(dir);
            if (section.getCurrentDirectory().isRoot()) {
                return "/";
            }
            return section.getName();
        }
        return _sectionName;
    }

    public String getPath(InodeHandle inode) {
        if (_matcher != null && _sectionName != null) {
            // Here we need to rewrite the path to make it relative
            // to the pseudo section
            return _matcher.getRelativePath(inode);
        }
        // Return path relative to section
        // Special case to handle directories in the root
        if (inode.getParent().isRoot()) {
            return inode.getPath().substring(1);
        }
        DirectoryHandle dir = inode.isDirectory() ? (DirectoryHandle) inode : inode.getParent();
        DirectoryHandle sectionBase =
                GlobalContext.getGlobalContext().getSectionManager().lookup(dir).getBaseDirectory();
        if (sectionBase.isRoot()) {
            return inode.getPath().substring(1);
        }
        return inode.getPath().substring(sectionBase.getPath().length() + 1);
    }
}
