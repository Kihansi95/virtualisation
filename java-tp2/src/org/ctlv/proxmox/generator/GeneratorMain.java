package org.ctlv.proxmox.generator;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.security.auth.login.LoginException;

import org.ctlv.proxmox.api.Constants;
import org.ctlv.proxmox.api.ExhautiveProxmoxAPI;
import org.ctlv.proxmox.api.ProxmoxAPI;
import org.ctlv.proxmox.api.data.LXC;
import org.ctlv.proxmox.api.data.Node;
import org.ctlv.proxmox.api.mode.CreateMode;
import org.json.JSONException;

public class GeneratorMain {
	
	static Random rndTime = new Random(new Date().getTime());
	public static int getNextEventPeriodic(int period) {
		return period;
	}
	public static int getNextEventUniform(int max) {
		return rndTime.nextInt(max);
	}
	public static int getNextEventExponential(int inv_lambda) {
		float next = (float) (- Math.log(rndTime.nextFloat()) * inv_lambda);
		return (int)next;
	}
	
	public static void main(String[] args) throws InterruptedException, LoginException, JSONException, IOException {
		
	
		//long baseID = Constants.CT_BASE_ID;
		int lambda = 30;
		
		Map<String, List<LXC>> myCTsPerServer = new HashMap<String, List<LXC>>();

		ExhautiveProxmoxAPI api = new ExhautiveProxmoxAPI(true);
		Random rndServer = new Random(new Date().getTime());
		Random rndRAM = new Random(new Date().getTime()); 
		
		long memAllowedOnServer1 = (long) (api.getNode(Constants.SERVER1).getMemory_total() * Constants.MAX_THRESHOLD);
		long memAllowedOnServer2 = (long) (api.getNode(Constants.SERVER2).getMemory_total() * Constants.MAX_THRESHOLD);

		final String nodes[] = {Constants.SERVER1, Constants.SERVER2};
		int cpt = 0;

		while (true) {

			cpt++; cpt %= 100; if(cpt == 0) cpt++;

			// 1. Calculer la quantité de RAM utilisée par mes CTs sur chaque serveur
			Map<String, Long> memOnServers = new HashMap<String, Long>();
			memOnServers.put(Constants.SERVER1, 0l);
			memOnServers.put(Constants.SERVER2, 0l);

			// Loop over nodes
			for(String node_id : nodes) {

				List<LXC> cts = api.getCTs(node_id);

				// Loop over ct in each node
				for(LXC ct : cts) {
					memOnServers.put(node_id, memOnServers.get(node_id) + ct.getMem());
				}

			}
			
			// Mémoire autorisée sur chaque serveur
			//TODO need this?
			Map<String, Float> memRatioOnServer = new HashMap<String, Float>();
			memRatioOnServer.put(Constants.SERVER1, .16f);
			memRatioOnServer.put(Constants.SERVER2, .16f);

			if (memOnServers.get(Constants.SERVER1) < memAllowedOnServer1
					&& memOnServers.get(Constants.SERVER2) < memAllowedOnServer2) {  // Exemple de condition de l'arrêt de la génération de CTs
				
				// choisir un serveur aléatoirement avec les ratios spécifiés 66% vs 33%
				String serverName;
				if (rndServer.nextFloat() < Constants.CT_CREATION_RATIO_ON_SERVER1)
					serverName = Constants.SERVER1;
				else
					serverName = Constants.SERVER2;
				
				// créer un contenaire sur ce serveur
				api.createCT(serverName, Integer.toString(1400+cpt), "ct-tpgei-virt-A4-ct"+cpt, Constants.RAM_SIZE[rndRAM.nextInt(3)], CreateMode.ERASE);
								
				// planifier la prochaine création
				int timeToWait = getNextEventExponential(lambda); // par exemple une loi expo d'une moyenne de 30sec
				
				// attendre jusqu'au prochain évènement
				Thread.sleep(1000 * timeToWait);
			}
			else {
				System.out.println("Servers are loaded, waiting ...");
				Thread.sleep(Constants.GENERATION_WAIT_TIME* 1000);
			}
		}
		
	}

}
