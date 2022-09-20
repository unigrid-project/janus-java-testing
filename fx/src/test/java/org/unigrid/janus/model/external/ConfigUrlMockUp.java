/*
	The Janus Wallet
	Copyright © 2021-2022 The Unigrid Foundation, UGD Software AB

	This program is free software: you can redistribute it and/or modify it under the terms of the
	addended GNU Affero General Public License as published by the Free Software Foundation, version 3
	of the License (see COPYING and COPYING.addendum).

	This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
	even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
	GNU Affero General Public License for more details.

	You should have received an addended copy of the GNU Affero General Public License with this program.
	If not, see <http://www.gnu.org/licenses/> and <https://github.com/unigrid-project/janus-java>.
 */

package org.unigrid.janus.model.external;

import mockit.MockUp;
import mockit.Mock;
import org.unigrid.janus.model.UpdateURL;

public class ConfigUrlMockUp extends MockUp<UpdateURL> {

	@Mock
	public String getLinuxUrl() {
		return ConfigUrlMockUp.class.getResource("get_update4j_config.xml").toString();
	}

	@Mock
	public String getBootstrapUrl() {
		return ConfigUrlMockUp.class.getResource("get_bootstrap_version_from_github.xml").toString();
	}
}
