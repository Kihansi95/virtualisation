package org.ctlv.proxmox.tester;

import org.ctlv.proxmox.api.Constants;
import org.ctlv.proxmox.api.ExhautiveProxmoxAPI;
import org.ctlv.proxmox.api.data.LXC;
import org.json.JSONException;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.List;

public class CleanerMain {
	public static void main(String[] args) throws LoginException, IOException, JSONException {
		ExhautiveProxmoxAPI api = new ExhautiveProxmoxAPI(true);

		// wipe out all CT in server
		List<LXC> cts1 = api.getCTs(Constants.SERVER1);
		for(LXC ct : cts1) {
			if( ct.getVmid().matches("14[0-9][0-9]"))
				api.deleteCT(Constants.SERVER1, ct.getVmid());
		}
		List<LXC> cts2 = api.getCTs(Constants.SERVER2);
		for(LXC ct : cts2) {
			if( ct.getVmid().matches("14[0-9][0-9]"))
				api.deleteCT(Constants.SERVER2, ct.getVmid());
		}
	}
}
