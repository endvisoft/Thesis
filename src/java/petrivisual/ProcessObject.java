/**
 * BeehiveZ is a business process model and instance management system.
 * Copyright (C) 2011  
 * Institute of Information System and Engineering, School of Software, Tsinghua University,
 * Beijing, China
 *
 * Contact: jintao05@gmail.com 
 *
 * This program is a free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation with the version of 2.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package petrivisual;


/**
 * @author Tao Jin 2009.9.3
 * 
 */
public class ProcessObject {
	public static final String TYPEPNML = "pnml";
	public static final String TYPEYAWL = "yawl";

	private long process_id = -1;
	private String name = null;
	private String description = null;
	private String type = null;
	private long catalog_id = -1;
	private long petrinet_id = -1;
	private long addTime = -1;
	private byte[] definition = null;

	// object for the specific process model
	private Object object = null;

	public Object getObject() {
		return object;
	}

	public void setObject(Object o) {
		this.object = o;
	}

	/**
	 * @return the definition
	 */
	public byte[] getDefinition() {
		return definition;
	}

	/**
	 * @param definition
	 *            the definition to set
	 */
	public void setDefinition(byte[] definition) {
		this.definition = definition;
	}

	/**
	 * @return the addTime
	 */
	public long getAddTime() {
		return addTime;
	}

	/**
	 * @param addTime
	 *            the addTime to set
	 */
	public void setAddTime(long addTime) {
		this.addTime = addTime;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the process_id
	 */
	public long getProcess_id() {
		return process_id;
	}

	/**
	 * @param processId
	 *            the process_id to set
	 */
	public void setProcess_id(long processId) {
		process_id = processId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the catalog_id
	 */
	public long getCatalog_id() {
		return catalog_id;
	}

	/**
	 * @param catalogId
	 *            the catalog_id to set
	 */
	public void setCatalog_id(long catalogId) {
		catalog_id = catalogId;
	}

	/**
	 * @return the petrinet_id
	 */
	public long getPetrinet_id() {
		return petrinet_id;
	}

	/**
	 * @param petrinetId
	 *            the petrinet_id to set
	 */
	public void setPetrinet_id(long petrinetId) {
		petrinet_id = petrinetId;
	}

}
