package org.ctlv.proxmox.api;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Constants {
	
	public static String USER_NAME; // votre nom d'utilisateur : XXX
	public static String PASS_WORD; // votre mot de passe . XXX
	
	
	public static String HOST = "srv-px1.insa-toulouse.fr";  // XXX
	public static String REALM = "Ldap-INSA";
	
	public static String SERVER1 = "srv-px7";  // exemple "srv-px2" XXX
	public static String SERVER2 = "srv-px8";  // XXX
	public static String CT_BASE_NAME = "ct-tpgei-virt-A4-ct";  // "XX" � remplacer par votre num�ro de bin�me. Exemple: ct-tpgei-virt-A3-ct � concat�ner avec le num�ro du CT
	public static long CT_BASE_ID = 1400;	 // � modifier (cf. sujet de tp) XXX

	
	public static long GENERATION_WAIT_TIME = 10;
	public static String CT_TEMPLATE = "template:vztmpl/debian-8-turnkey-nodejs_14.2-1_amd64.tar.gz";  // XXX
	public static String CT_PASSWORD = "tpuser";
	public static String CT_HDD = "vm:3";
	public static String CT_NETWORK = "name=eth0,bridge=vmbr1,ip=dhcp,tag=2028,type=veth";
	
	public static float CT_CREATION_RATIO_ON_SERVER1 = 0.66f;
	public static float CT_CREATION_RATIO_ON_SERVER2 = 0.33f;
	public static long RAM_SIZE[] = new long[]{256, 512, 768};
	
	public static long MONITOR_PERIOD = 10;
	public static float MIGRATION_THRESHOLD = 0.08f;
	public static float DROPPING_THRESHOLD = 0.12f;
	public static float MAX_THRESHOLD = 0.16f;
			
	static {
		Properties prop = new Properties();
		try {
			FileInputStream input = new FileInputStream("conf/ident.conf");
			prop.load(input);

			USER_NAME = prop.getProperty("user_name");
			PASS_WORD = prop.getProperty("pass_word");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
