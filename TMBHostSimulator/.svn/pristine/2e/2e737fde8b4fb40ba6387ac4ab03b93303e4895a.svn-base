/**
 * @(#)ConnectionManager.java	1.0a 25/04/2007
 *
 * Copyright 2007-2008 Financial Software & Systems (P)  Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of Financial Software & Systems (P) Ltd.
 * Use is subject to license terms.
 */
package db;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDriver;
import org.apache.commons.pool.impl.GenericObjectPool;

import simulator.HostSimulator;

/**
 * @author <a href="aravindang@fss.co.in">Aravindan. G</a>
 * @version: $
 * @created on Apr 25, 2007 6:04:07 PM
 */
public class ConnectionManager {

	GenericObjectPool pool = null;

	public void createConnection() throws Exception {

		Class.forName(HostSimulator.conf.getString("URL"));

		ConnectionFactory cf = new DriverManagerConnectionFactory(
									HostSimulator.conf.getString("DSN"),
									HostSimulator.conf.getString("USERNAME"),
									HostSimulator.conf.getString("PASSWORD"));

		pool = new GenericObjectPool();

		pool.setMinIdle(HostSimulator.conf.getInt("MAXACTIVE"));
		pool.setMaxIdle(HostSimulator.conf.getInt("MINACTIVE"));

		pool.setWhenExhaustedAction(GenericObjectPool.WHEN_EXHAUSTED_GROW);

		new PoolableConnectionFactory(cf, pool, null, null, false, true);

		for (int j = 0; j < HostSimulator.conf.getInt("MAXACTIVE"); j++) {
			pool.addObject();
		}

		PoolingDriver poolingDriver = new PoolingDriver();
		poolingDriver.registerPool(HostSimulator.conf.getString("TOKEN"), pool);
	}
}
