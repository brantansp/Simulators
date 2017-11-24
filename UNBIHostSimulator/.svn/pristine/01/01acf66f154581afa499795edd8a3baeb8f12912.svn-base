/**
 * @(#)APIImpl.java	1.0a 12/04/2007
 *
 * Copyright 2007-2008 Financial Software & Systems (P)  Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of Financial Software & Systems (P) Ltd.
 * Use is subject to license terms.
 */
package pool;

import simulator.HostSimulator;

/**
 * Util class used to set the object pool configuration and load the
 * api class objects
 *
 * @author <a href="aravindang@fss.co.in">Aravindan. G</a>
 * @since
 * @version
 * @created date: Apr 12, 2007 5:13:32 PM
 */
public class APIImpl {

	//----------------------------------- Default Constructor

	public APIImpl() throws Exception {

		HostSimulator.apiPool.setFactory(new APIFactory());

		HostSimulator.apiPool.setMaxActive(HostSimulator.conf.getInt("MAXACTIVE"));
		HostSimulator.apiPool.setMaxIdle(HostSimulator.conf.getInt("MINACTIVE"));

		HostSimulator.apiPool.addObject(HostSimulator.conf.getString("APINAME"));
	}
}
