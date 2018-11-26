package org.ctlv.proxmox.manager;

import org.ctlv.proxmox.api.Constants;
import org.ctlv.proxmox.api.ExhautiveProxmoxAPI;
import org.ctlv.proxmox.api.ProxmoxAPI;
import org.ctlv.proxmox.api.data.LXC;
import org.json.JSONException;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class Controller {

	private boolean v;

	ExhautiveProxmoxAPI api;
	public Controller(ExhautiveProxmoxAPI api, boolean verbose){
		this.api = api;
		v = verbose;
	}
	
	// migrer un conteneur du serveur "srcServer" vers le serveur "dstServer"
	public void migrateFromTo(String srcServer, String dstServer)  {

		try {
			//TODO maybe throw any exception
			LXC last_ct = api.getLastCT(srcServer);
			api.migrateCT(srcServer,  last_ct.getVmid(), dstServer);

		} catch (LoginException | IOException | JSONException e) {
			e.printStackTrace();
		}

	}

	// arrêter le plus vieux conteneur sur le serveur "server"
	public void offLoad(String server) {

		try {
			//TODO maybe throw any exception
			LXC last_ct = api.getLastCT(server);
			api.deleteCT(last_ct.getName(), Long.toString(last_ct.getPid()));
		} catch (LoginException | IOException | JSONException e) {
			e.printStackTrace();
		}


	}

}
