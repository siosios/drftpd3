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
package org.drftpd.master.commands;

import org.drftpd.common.dynamicdata.Key;
import org.drftpd.common.dynamicdata.KeyedMap;
import org.drftpd.master.vfs.DirectoryHandle;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Vector;

/**
 * @author djb61
 * @version $Id$
 */
@SuppressWarnings("serial")
public class CommandResponse extends KeyedMap<Key<?>, Object> implements CommandResponseInterface {

    public static final Key<Integer> CODE = new Key<>(CommandResponse.class, "code");

    public static final Key<Vector<String>> COMMENT = new Key<>(CommandResponse.class, "comment");

    public static final Key<DirectoryHandle> CURRENTDIRECTORY = new Key<>(CommandResponse.class, "currentDirectory");

    public static final Key<String> MESSAGE = new Key<>(CommandResponse.class, "message");

    public static final Key<String> USER = new Key<>(CommandResponse.class, "user");

    public CommandResponse(int code) {
        setCode(code);
    }

    public CommandResponse(int code, String message) {
        setCode(code);
        setMessage(message);
    }

    public CommandResponse(int code, DirectoryHandle directory, String user) {
        setCode(code);
        setCurrentDirectory(directory);
        setUser(user);
    }

    public CommandResponse(int code, String message, DirectoryHandle directory, String user) {
        setCode(code);
        setMessage(message);
        setCurrentDirectory(directory);
        setUser(user);
    }

    public void addComment(BufferedReader in) throws IOException {
        String line;

        while ((line = in.readLine()) != null) { // throws IOException
            this.addComment(line);
        }
    }

    public void addComment(Object comment) {
        Vector<String> _comments = getComment();
        String resp = String.valueOf(comment);

        if (resp.indexOf('\n') != -1) {
            String[] lines = resp.split("\n");

            for (String line : lines) {
                _comments.add(line);
            }
        } else {
            _comments.add(resp);
        }
        setObject(CommandResponse.COMMENT, _comments);
    }

    public int getCode() {
        return getObject(CommandResponse.CODE, 500);
    }

    public void setCode(int code) {
        setObject(CommandResponse.CODE, code);
    }

    public Vector<String> getComment() {
        return getObject(CommandResponse.COMMENT, new Vector<>());
    }

    public DirectoryHandle getCurrentDirectory() {
        return getObject(CommandResponse.CURRENTDIRECTORY, null);
    }

    public void setCurrentDirectory(DirectoryHandle currentDirectory) {
        setObject(CommandResponse.CURRENTDIRECTORY, currentDirectory);
    }

    public String getMessage() {
        return getObject(CommandResponse.MESSAGE, "");
    }

    public void setMessage(String message) {
        setObject(CommandResponse.MESSAGE, message);
    }

    public String getUser() {
        return getObject(CommandResponse.USER, null);
    }

    public void setUser(String currentUser) {
        if (currentUser != null) {
            setObject(CommandResponse.USER, currentUser);
        }
    }
}
