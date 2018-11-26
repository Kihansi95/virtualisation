package org.ctlv.proxmox.api;

import org.ctlv.proxmox.api.data.LXC;
import org.ctlv.proxmox.api.data.Node;
import org.ctlv.proxmox.api.mode.CreateMode;
import org.json.JSONException;
import org.json.JSONObject;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ExhautiveProxmoxAPI extends ProxmoxAPI {

    private boolean v;

    public ExhautiveProxmoxAPI() {

        // turn off verbose by default
        this(false);
    }

    public ExhautiveProxmoxAPI(boolean verbose) {
        this.v = verbose;
    }

    public boolean isVerbose() {
        return v;
    }

    /**
     * Delete the existing one and recreate a new
     * @param node server name
     * @param ctID 		Id of CT. ex: 14xx, where xx = [00,99]
     * @param ctName	name of CT. ex: ct-tpgei-virt-bA2-ct33
     * @param ctMemory	Ram size for CT. ex: 256, 512, 768
     * @throws LoginException
     * @throws JSONException
     * @throws IOException
     */
    public void createCT(String node, String ctID, String ctName, long ctMemory, CreateMode mode) throws LoginException, JSONException, IOException {

        if(isVerbose()) {
            System.out.println("[debug] Creating CT in mode " + mode +" ...");
        }

        // in NORMAL mode, just call mother class
        if(mode == CreateMode.NORMAL) {
            super.createCT(node, ctID, ctName, ctMemory);
            return;
        }

        // check if the container exists on server already
        LXC ct = super.getCT(node, ctID);

        if(ct == null) {
            // the ct does not exist, create as it has to
            if(isVerbose()) {
                System.out.println("[debug] No such ct exists, creating CT [" +ctName+"] ...");
            }
            super.createCT(node, ctID, ctName, ctMemory);
            System.out.println("[debug] CT [" + ctName +"] created with success!");
            return;
        }

        if(isVerbose()) {
            System.err.println("[debug] CT [" +ctName+ "] exists already");
        }

        // the container created in server, shut it down if needed
        if(ct.getStatus().equalsIgnoreCase("running")) {
            if(isVerbose()) {
                System.out.println("[debug] Stopping CT [" +ctName+"] ...");
            }
            this.stopCT(node, ctID);
        }


        // if we are in mode ERASE, delete the ct by any cost
        if(mode == CreateMode.ERASE) {

            if(isVerbose()) {
                System.out.println("[debug] Deleting CT [" +ctName+"] ...");
            }
            this.deleteCT(node, ctID);

            if(isVerbose()) {
                System.out.println("[debug] Creating CT [" +ctName+"] ...");
            }

            // createCT exhautively
            boolean request_received;
            do {

                try {

                    super.createCT(node, ctID, ctName, ctMemory);
                    request_received = true;

                } catch(IOException e) {
                    if(isVerbose()) {
                        System.err.println("[debug] " +e);
                    }
                    request_received = false;
                }

            } while(!request_received);
        }

        if(isVerbose())
            System.out.println("[debug] CT [" + ctName +"] created with success!");
    }

    /**
     * Send non-stop startCT until Promox server accept it
     * @param node
     * @param ctID
     * @throws LoginException
     * @throws JSONException
     */
    @Override
    public void startCT(String node, String ctID) throws LoginException, JSONException {

        boolean request_received;
        if(isVerbose()) {
            System.out.println("[debug] Starting in LXC "+ node + " CT " + ctID);
        }

        do {

            try {

                super.startCT(Constants.SERVER1, Long.toString(Constants.CT_BASE_ID));
                request_received = true;

            } catch(IOException e) {
                if(isVerbose()) {
                    System.err.println("[debug] " +e);
                }
                request_received = false;
            }

        } while(!request_received);
        if(isVerbose()) {
            System.out.println("[debug] Start CT [" + ctID+ "] success!");
        }
    }

    /**
     * Send non-stop startCT until Promox server accept it
     * @param node
     * @param ctID
     * @throws LoginException
     * @throws JSONException
     * @throws IOException
     */
    @Override
    public void stopCT(String node, String ctID) throws LoginException, JSONException, IOException {

        boolean request_received;
        if(isVerbose()) {
            System.out.println("[debug] Stopping in LXC "+ node + " CT [" + ctID +"] ..");
        }

        do {

            try {

                super.stopCT(node, ctID);
                request_received = true;

            } catch(IOException e) {
                System.err.println("[debug] " +e);
                request_received = false;
            }

        } while(!request_received);
        if(isVerbose()) {
            System.out.println("[debug] Stop CT [" + ctID+ "] success!");
        }
    }

    /**
     * Send non-stop deleteCT until Promox server accept it
     * @param node
     * @param ctID
     * @throws LoginException
     * @throws JSONException
     * @throws IOException
     */
    @Override
    public void deleteCT(String node, String ctID) throws LoginException, JSONException {
        boolean request_received;
        if(isVerbose()) {
            System.out.println("[debug] Deleting CT [" + ctID+ "] ...");
        }
        try {

            // check if container exists
            LXC ct = super.getCT(node, ctID);

            if(ct != null) {

                // the container exists, shut it down before delete
                if(ct.getStatus().equalsIgnoreCase("running")) {
                    if(isVerbose()) {
                        System.out.println("[debug] Stopping CT [" +ct.getName()+"] ...");
                    }
                    this.stopCT(node, ctID);
                }

                do {

                    try {

                        super.deleteCT(node, ctID);
                        request_received = true;

                    } catch(IOException e) {
                        System.err.println("[debug] " +e);
                        request_received = false;
                    }

                } while(!request_received);
            }


        } catch (IOException e) {
            System.err.println("[debug] " +e);
        }

        if(isVerbose()) {
            System.out.println("[debug] Delete CT [" + ctID+ "] success!");
        }
    }

    /**
     * Return the lastest CT
     * @param node
     * @return
     * @throws LoginException
     * @throws IOException
     * @throws JSONException
     */
    public LXC getLastCT(String node) throws LoginException, IOException, JSONException {

        List<LXC> lxcList = super.getCTs(node);
        Collections.sort(lxcList, new Comparator<LXC>() {
            @Override
            public int compare(LXC ct1, LXC ct2) {
                return (int)((ct1.getUptime() - ct2.getUptime()) * 10000000 );  //TODO check the meaning of uptime
            }
        });

        return lxcList.get(0);
    }
}
