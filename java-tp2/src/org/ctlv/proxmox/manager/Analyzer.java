package org.ctlv.proxmox.manager;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.corba.se.impl.orbutil.closure.Constant;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import org.ctlv.proxmox.api.Constants;
import org.ctlv.proxmox.api.ExhautiveProxmoxAPI;
import org.ctlv.proxmox.api.ProxmoxAPI;
import org.ctlv.proxmox.api.data.LXC;
import org.json.JSONException;

import javax.security.auth.login.LoginException;

public class Analyzer {
	ExhautiveProxmoxAPI api;
	Controller controller;

	private boolean v;
	
	public Analyzer(ExhautiveProxmoxAPI api, Controller controller, boolean verbose) {
		this.api = api;
		this.controller = controller;
		v = verbose;
	}

	/**
	 *
	 * @param myCTsPerServer: Couple (server name, list of LXC)
	 */
	public void analyze(Map<String, List<LXC>> myCTsPerServer)  {

		try {

			if(v)
				System.out.println("[debug] Analyzer: Server | Usage");


			// Calculer la quantité de RAM utilisée par mes CTs sur chaque serveur
			Map<String, Long> usages = new HashMap<>();
			for(Map.Entry<String, List<LXC>> entry: myCTsPerServer.entrySet()) {

				String server_name = entry.getKey();

				Long memory_use = 0l;
				for(LXC ct : entry.getValue()) {
					memory_use += ct.getMem();
				}

				usages.put(server_name, memory_use);
				if(v) {
					long total = api.getNode(server_name).getMemory_total();
					System.out.println(server_name + " | " + memory_use + " ("+(memory_use/total)*100 +"%)");
				}
			}


			if(v) {
				System.out.println("[debug] Analyzer: Server | Drop | Migration");
			}

			// Mémoire autorisée sur chaque serveur
			Map<String, Long> drop_rate = new HashMap<>();
			Map<String, Long> migration_rate = new HashMap<>();
			for(String server : usages.keySet()) {

				long drop = (long) (api.getNode(server).getMemory_total() * Constants.DROPPING_THRESHOLD);
				drop_rate.put(server, drop);

				long migration = (long) (api.getNode(server).getMemory_total() * Constants.DROPPING_THRESHOLD);
				migration_rate.put(server, migration);

				if(v)
					System.out.println(server + " | " + drop + " | " + migration);
			}

			// Analyse et Actions
			// Si un serveur dépasse 12% tue ce serveur jusqu'à ce que le serveur < 12%
			for(Map.Entry<String, Long> entry: usages.entrySet()) {

				String server_name = entry.getKey();
				Long usage = entry.getValue();

				Long drop = drop_rate.get(server_name);
				Long migration = migration_rate.get(server_name);

				if(usage > drop) {

					System.out.println("[debug] Analyzer: Drop in server ["+ server_name+"]");
					controller.offLoad(server_name);

				} else if(usage > migration) {

					System.out.println("[debug] Analyzer: Possible migration for server ["+ server_name+"]");

					// search for server that has min usage:
					String min_server = null;
					Long min_usage = Long.MAX_VALUE;
					for(String server: usages.keySet()) {
						if(usages.get(server) < min_usage) {
							min_usage = usages.get(server);
							min_server = server;
						}
					}

					if(min_usage < usage) {
						System.out.println("[debug] Analyzer: Migrate from ["+ server_name + "] to ["+min_server+"]");
						controller.migrateFromTo(server_name, min_server);
					}
					// else : no better server

				}
			}




		} catch (LoginException | JSONException | IOException e) {
			e.printStackTrace();
		}
	}

}
