package org.ctlv.proxmox.api.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Node represent the VM in proxmox
 */
public class Node {
	private float cpu;
	private int uptime;
	
	private long memory_used;
	private long memory_free;
	private long memory_total;
	
	private long rootfs_free;
	private long rootfs_total;
	private long rootfs_used;

	public Node(JSONObject data) throws JSONException {
		JSONObject o;
		
		cpu = (float) data.getDouble("cpu");
		uptime = data.getInt("uptime");
		
		o = data.getJSONObject("memory");
		memory_free = o.getLong("free");
		memory_total = o.getLong("total");
		memory_used = o.getLong("used");
		
		o = data.getJSONObject("rootfs");
		rootfs_free = o.getLong("free");
		rootfs_total = o.getLong("total");
		rootfs_used = o.getLong("used");
	}

	public float getCpu() {
		return cpu;
	}

	public int getUptime() {
		return uptime;
	}

	public long getMemory_free() {
		return memory_free;
	}

	public long getMemory_total() {
		return memory_total;
	}

	public long getRootfs_free() {
		return rootfs_free;
	}

	public long getRootfs_total() {
		return rootfs_total;
	}

	public long getRootfs_used() {
		return rootfs_used;
	}

	public long getMemory_used() {
		return memory_used;
	}

	//TODO test print as json
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		String newLine = System.getProperty("line.separator");

		result.append( this.getClass().getName() );
		result.append( " Object {" );
		result.append(newLine);

		//determine fields declared in this class only (no fields of superclass)
		Field[] fields = this.getClass().getDeclaredFields();

		//print field names paired with their values
		for ( Field field : fields  ) {
			result.append("  ");
			try {
				result.append( field.getName() );
				result.append(": ");
				//requires access to private field:
				result.append( field.get(this) );
			} catch ( IllegalAccessException ex ) {
				System.out.println(ex);
			}
			result.append(newLine);
		}
		result.append("}");

		return result.toString();
	}
}
