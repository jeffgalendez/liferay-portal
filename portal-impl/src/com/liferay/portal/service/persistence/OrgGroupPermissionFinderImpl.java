/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.model.OrgGroupPermission;
import com.liferay.portal.model.impl.OrgGroupPermissionImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.Iterator;

/**
 * <a href="OrgGroupPermissionFinderImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class OrgGroupPermissionFinderImpl
	extends BasePersistenceImpl<OrgGroupPermission>
	implements OrgGroupPermissionFinder {

	public static String FIND_BY_O_G_R =
		OrgGroupPermissionFinder.class.getName() + ".findByO_G_R";

	public void removeByO_G_R(
			long organizationId, long groupId, long resourceId)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_O_G_R);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("OrgGroupPermission", OrgGroupPermissionImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(resourceId);
			qPos.add(organizationId);
			qPos.add(groupId);

			Iterator<OrgGroupPermission> itr = q.list().iterator();

			while (itr.hasNext()) {
				OrgGroupPermission orgGroupPermission = itr.next();

				OrgGroupPermissionUtil.remove(
					orgGroupPermission.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}