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
package org.drftpd.zipscript.common.mp3;

import org.drftpd.common.dynamicdata.Key;

import java.io.Serializable;

/**
 * @author djb61
 * @version $Id$
 */
@SuppressWarnings("serial")
public class MP3Info implements Serializable {

    public static final Key<MP3Info> MP3INFO = new Key<>(MP3Info.class, "mp3");

    private ID3Tag _id3tag;

    private int _bitrate = 0;

    private String _samplerate = "";

    private String _encodingtype = "";

    private String _stereoMode = "";

    private float _runtime = 0;

    public MP3Info() {
    }

    public ID3Tag getID3Tag() {
        return _id3tag;
    }

    public void setID3Tag(ID3Tag id3tag) {
        _id3tag = id3tag;
    }

    public int getBitrate() {
        return _bitrate;
    }

    public void setBitrate(int bitrate) {
        _bitrate = bitrate;
    }

    public String getSamplerate() {
        return _samplerate;
    }

    public void setSamplerate(String samplerate) {
        _samplerate = samplerate;
    }

    public String getEncodingtype() {
        return _encodingtype;
    }

    public void setEncodingtype(String encodingtype) {
        _encodingtype = encodingtype;
    }

    public String getStereoMode() {
        return _stereoMode;
    }

    public void setStereoMode(String stereoMode) {
        _stereoMode = stereoMode;
    }

    public float getRuntime() {
        return _runtime;
    }

    public void setRuntime(float runtime) {
        _runtime = runtime;
    }
}
