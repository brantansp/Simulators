/**
 * @(#)APIFactory.java	1.0a 12/04/2007
 *
 * Copyright 2007-2008 Financial Software & Systems (P)  Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of Financial Software & Systems (P) Ltd.
 * Use is subject to license terms.
 */
package pool;

import org.apache.commons.pool.BaseKeyedPoolableObjectFactory;

/**
 * Factory class to create pooling of api object
 *
 * @author <a href="aravindang@fss.co.in">Aravindan. G</a>
 * @since
 * @version
 * @created date: Apr 12, 2007 5:13:32 PM
 */
public class APIFactory extends BaseKeyedPoolableObjectFactory {

	@Override
	public Object makeObject(Object className) throws Exception {

		ClassLoader clazz = Thread.currentThread().getContextClassLoader();
		if (clazz == null) {
			clazz = this.getClass().getClassLoader();
		}
		return (clazz.loadClass(className.toString()).newInstance());
	}
}
