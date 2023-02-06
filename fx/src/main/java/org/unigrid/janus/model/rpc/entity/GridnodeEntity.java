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

package org.unigrid.janus.model.rpc.entity;

import java.net.InetSocketAddress;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.unigrid.janus.model.Gridnode;

@Data
@EqualsAndHashCode(callSuper = false)
public class GridnodeEntity extends BaseResult<String> {
	private static final String METHOD = "masternode";

	// masternode <start|start-alias|start-many|stop|stop-alias|stop-many|list|list-conf|
	// add-conf|write-conf|count|debug|current|winners|genkey|enforce|outputs> [passphrase]
	public static class Request extends BaseRequest {
		public Request(Object[] args) {
			super(METHOD);
			this.setParams(args);
		}
	}

	public static Request genKey() {
		return new Request((new Object[]{"genkey"}));
	}

	public static Request startAlias(String lockWalletOnFinished, String alias) {

		return new Request((new Object[]{"start-alias",
			lockWalletOnFinished,
			alias
		}));
	}

	public static Request addConf(String alias, InetSocketAddress address, String privateKey, String txHash,
		int outputIdx) {

		return new Request((new Object[]{"add-conf",
			alias,
			String.format("%s:%s", address.getHostString(), address.getPort()),
			privateKey,
			txHash,
			Integer.toString(outputIdx)
		}));
	}

	@Data
	public static class Result extends Gridnode {
		/* Empty on purpose */
	}
}
