package org.ctlv.proxmox.api.mode;

public enum CreateMode {
    ERASE,  // delete totaly the ct and recreate a new one
    KEEP,   // if the ct exists already, just shut it down if need
    NORMAL  // raise exception
}
