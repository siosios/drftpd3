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
package org.drftpd.master.usermanager.encryptedjavabeans;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bushe.swing.event.annotation.AnnotationProcessor;
import org.bushe.swing.event.annotation.EventSubscriber;
import org.drftpd.common.util.ConfigLoader;
import org.drftpd.master.event.ReloadEvent;
import org.drftpd.master.usermanager.User;
import org.drftpd.master.usermanager.UserFileException;
import org.drftpd.master.usermanager.javabeans.BeanUser;
import org.drftpd.master.usermanager.javabeans.BeanUserManager;

import java.lang.ref.SoftReference;
import java.util.Properties;

public class EncryptedBeanUserManager extends BeanUserManager {

    protected static final Logger logger = LogManager.getLogger(EncryptedBeanUserManager.class);

    private int _passcrypt = 0;

    /*
     * Constructor to read encryption type, and subscribe to events
     */
    public EncryptedBeanUserManager() {
        AnnotationProcessor.process(this);
        readPasscrypt();
    }

    @EventSubscriber
    public void onReloadEvent(ReloadEvent event) {
        logger.info("Received reload event, reloading");
        readPasscrypt();
    }

    /*
     * Reads the Password Encryption into memory for user
     */
    private void readPasscrypt() {
        Properties cfg = ConfigLoader.loadConfig("encryptedbeanuser.conf");
        String passcrypt = cfg.getProperty("passcrypt");
        if (passcrypt == null) {
            _passcrypt = 0;
        } else if (passcrypt.equalsIgnoreCase("md2")) {
            _passcrypt = 1;
        } else if (passcrypt.equalsIgnoreCase("md5")) {
            _passcrypt = 2;
        } else if (passcrypt.equalsIgnoreCase("sha-1")) {
            _passcrypt = 3;
        } else if (passcrypt.equalsIgnoreCase("sha-256")) {
            _passcrypt = 4;
        } else if (passcrypt.equalsIgnoreCase("sha-384")) {
            _passcrypt = 5;
        } else if (passcrypt.equalsIgnoreCase("sha-512")) {
            _passcrypt = 6;
        } else if (passcrypt.equalsIgnoreCase("bcrypt")) {
            _passcrypt = 7;
        } else {
            _passcrypt = 0;
        }
    }

    /*
     * Returns current Passcrypt type
     */
    protected int getPasscrypt() {
        return _passcrypt;
    }

    /**
     * Creates a user named 'username' and adds it to the users map.
     */
    protected synchronized User createUserImpl(String username) {
        EncryptedBeanUser buser = new EncryptedBeanUser(this, username);
        _users.put(username, new SoftReference<>(buser));
        return buser;
    }

    @Override
    protected User loadUser(String userName) throws UserFileException {
        User user = super.loadUser(userName);
        if (!(user instanceof EncryptedBeanUser) && (user instanceof BeanUser)) {
            return new EncryptedBeanUser(this, (BeanUser) user);
        }
        return user;
    }
}
