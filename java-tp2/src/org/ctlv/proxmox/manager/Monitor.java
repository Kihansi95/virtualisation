package org.ctlv.proxmox.manager;

import org.ctlv.proxmox.api.Constants;
import org.ctlv.proxmox.api.ExhautiveProxmoxAPI;
import org.ctlv.proxmox.api.ProxmoxAPI;
import org.ctlv.proxmox.api.data.LXC;
import org.ctlv.proxmox.api.data.Node;
import org.json.JSONException;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Monitor implements Runnable {

	private boolean v;
	Analyzer analyzer;
	ExhautiveProxmoxAPI api;
	
	public Monitor(ExhautiveProxmoxAPI api, Analyzer analyzer, boolean verbose) {
		this.api = api;
		this.analyzer = analyzer;
		v = verbose;
	}
	

	@Override
	public void run() {

		if(v)
			System.out.println("[debug] Monitor: starting...");

		while(true) {

			try {

				// Récupérer les données sur les serveurs
				List<LXC> cts_server1 = api.getCTs(Constants.SERVER1);
				List<LXC> cts_server2 = api.getCTs(Constants.SERVER2);
				System.out.println("[debug] Monitor: get info from ["+Constants.SERVER1+"], ["+Constants.SERVER2+"]");


				Map<String, List<LXC>> myCTsPerServer = new HashMap<>();
				myCTsPerServer.put(Constants.SERVER1, cts_server1);
				myCTsPerServer.put(Constants.SERVER2, cts_server2);

				// Lancer l'analyse
				analyzer.analyze(myCTsPerServer);


			} catch (LoginException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}


			


			
			// attendre une certaine période
			try {
				Thread.sleep(Constants.MONITOR_PERIOD * 1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}
