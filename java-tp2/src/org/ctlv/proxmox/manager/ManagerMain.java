package org.ctlv.proxmox.manager;


import org.ctlv.proxmox.api.Constants;
import org.ctlv.proxmox.api.ExhautiveProxmoxAPI;
import org.ctlv.proxmox.api.data.LXC;
import org.ctlv.proxmox.api.data.Node;

import java.util.ArrayList;
import java.util.List;

public class ManagerMain {

	public static void main(String[] args) throws Exception {

		boolean verbose = true;

		ExhautiveProxmoxAPI api = new ExhautiveProxmoxAPI(verbose);
		Controller controller = new Controller(api, verbose);
		Analyzer analyzer = new Analyzer(api, controller, verbose);
		Monitor monitor = new Monitor(api, analyzer, verbose);

		new Thread(monitor).start();
	}

}