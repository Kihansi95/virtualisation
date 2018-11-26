package org.ctlv.proxmox.tester;

import java.io.IOException;
import java.util.List;

import javax.security.auth.login.LoginException;

import org.ctlv.proxmox.api.Constants;
import org.ctlv.proxmox.api.ExhautiveProxmoxAPI;
import org.ctlv.proxmox.api.ProxmoxAPI;
import org.ctlv.proxmox.api.data.LXC;
import org.ctlv.proxmox.api.data.Node;
import org.ctlv.proxmox.api.mode.CreateMode;
import org.json.JSONException;

public class Main {
	/*
	public static createCT(ProxmoxAPI api, String node, String ctID, String ctName, long ctMemory) {
		api.createCT(Constants.SERVER1, Long.toString(Constants.CT_BASE_ID), Constants.CT_BASE_NAME, Constants.RAM_SIZE[1]);
	}*/

	public static void main(String[] args) throws LoginException, JSONException, IOException, InterruptedException {

		ExhautiveProxmoxAPI api = new ExhautiveProxmoxAPI(true);

		// Creer un CT
//		api.createCT(Constants.SERVER1, Long.toString(Constants.CT_BASE_ID), Constants.CT_BASE_NAME, Constants.RAM_SIZE[1], CreateMode.ERASE);

		// startCT strategy: start loop until not receive timeoutException
//		api.startCT(Constants.SERVER1, Long.toString(Constants.CT_BASE_ID));

//		api.stopCT(Constants.SERVER1, Long.toString(Constants.CT_BASE_ID));

		// Supprimer un CT
//		api.stopCT(Constants.SERVER1, Long.toString(Constants.CT_BASE_ID));
//		TimeUnit.SECONDS.sleep(30);
//		api.deleteCT(Constants.SERVER1, Long.toString(Constants.CT_BASE_ID));
//		TimeUnit.SECONDS.sleep(1);
//		System.out.println("Le CT bien supprime!");


		// Récupérer les informations relatives  un serveur Proxmox
//		Node node = api.getNode(Constants.SERVER1);
//		System.out.println("Serveur Proxmox: CPU = " + node.getCpu()+", Disque dur = " + node.getRootfs_used()/node.getRootfs_total()+ ", RAM = "+node.getMemory_used()/node.getMemory_total());
//
//		// Cr�er un conteneur et le lancer
//		api.createCT(Constants.SERVER1, Long.toString(Constants.CT_BASE_ID), Constants.CT_BASE_NAME, Constants.RAM_SIZE[0]);
//		TimeUnit.SECONDS.sleep(30);
//		api.startCT(Constants.SERVER1, Long.toString(Constants.CT_BASE_ID));
//
//		// Récupérer les informations relatives à un conteneur
//		LXC ct = api.getCT(Constants.SERVER1, Long.toString(Constants.CT_BASE_ID));
//		System.out.println("CT " + Constants.CT_BASE_NAME +": etat = "+ct.getStatus()+", %CPU = "+ct.getCpu() + ", %RAM= "+ct.getMem() + ", %Disque dur = "+ct.getDisk() + ", Nom du serveur = "+ct.getName());
//
//		System.out.println("toto : " + api.getCTList(Constants.CT_BASE_NAME));


		// TODO to be wiped out when done testing:
//		api.createCT(Constants.SERVER1, Long.toString(Constants.CT_BASE_ID), Constants.CT_BASE_NAME, Constants.RAM_SIZE[1], CreateMode.KEEP);
//		LXC lxc = api.getCT(Constants.SERVER1, Long.toString(Constants.CT_BASE_ID));
//		System.out.println(lxc);
//
//		api.startCT(Constants.SERVER1, Long.toString(Constants.CT_BASE_ID));
//		lxc = api.getCT(Constants.SERVER1, Long.toString(Constants.CT_BASE_ID));
//		System.out.println(lxc);

		Node sv = api.getNode(Constants.SERVER2);
		System.out.println(sv);

	}

}
