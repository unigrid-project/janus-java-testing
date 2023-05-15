/*
	The Janus Wallet
	Copyright © 2023 The Unigrid Foundation

	This program is free software: you can redistribute it and/or modify it under the terms of the
	addended GNU Affero General Public License as published by the Free Software Foundation, version 3
	of the License (see COPYING and COPYING.addendum).

	This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
	even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
	GNU Affero General Public License for more details.

	You should have received an addended copy of the GNU Affero General Public License with this program.
	If not, see <http://www.gnu.org/licenses/> and <https://github.com/unigrid-project/janus-java>.
 */

package org.unigrid.janus;

import java.util.Objects;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import org.apache.maven.shared.release.strategy.Strategy;

@Named("unigrid-release-strategy")
@Singleton
public class UnigridReleaseStrategy implements Strategy {
	public static final String VERSION_PREFIX = "v";
	public static final String VERSION_DELIMETER = "-";
	public static final String MODULE_FX = "fx";
	public static final String MODULE_BOOTSTRAP = "bootstrap";
	public static final String MODULE_DESKTOP = "desktop";
	public static final String MODULE_PARENT = "parent";
	public static final String PHASE_DEFAULT_DEV_VERSION = "map-development-versions";
	public static final String PHASE_DEFAULT_SCM_COMMIT = "scm-commit-release";
	public static final String PHASE_DEFAULT_TAG = "scm-tag";
	public static final String PHASE_DEV_VERSION = "unigrid-map-development-versions";
	public static final String PHASE_SCM_COMMIT = "unigrid-scm-pre-commit-release";
	public static final String PHASE_TAG = "unigrid-scm-tag";

	@Named("default")
	@Inject
	Strategy defaultStrategy;

	@Override
	public List<String> getPreparePhases() {
		if (Objects.isNull(System.getProperty("module"))) {
			return defaultStrategy.getPreparePhases();
		}

		List<String> phases = new ArrayList<>(defaultStrategy.getPreparePhases());
		System.out.println("::::::::::::::::::::::::::::::::::");
		System.out.println(":::::------------------------:::::");
		System.out.println("::::: UnigridReleaseStrategy :::::");
		System.out.println(":::::------------------------:::::");
		System.out.println("::::::::::::::::::::::::::::::::::");
		System.out.println("Default Phases: " + phases);

		phases.replaceAll(str -> str.equals(PHASE_DEFAULT_DEV_VERSION) ? PHASE_DEV_VERSION : str);

		int index = phases.indexOf(PHASE_DEFAULT_SCM_COMMIT);
		if (index != -1) {
			phases.add(index, PHASE_SCM_COMMIT);
		}

		index = phases.indexOf(PHASE_DEFAULT_TAG);
		if (index != -1) {
			phases.add(index + 1, PHASE_TAG);
		}

		System.out.println("Unigrid phases: " + phases);
		System.out.println("Module: " + System.getProperty("module"));

		return phases;
	}

	@Override
	public List<String> getPerformPhases() {
		return new ArrayList<>(defaultStrategy.getPerformPhases());
	}

	@Override
	public List<String> getBranchPhases() {
		return new ArrayList<>(defaultStrategy.getPerformPhases());
	}

	@Override
	public List<String> getRollbackPhases() {
		return defaultStrategy.getRollbackPhases();
	}

	@Override
	public List<String> getUpdateVersionsPhases() {
		return new ArrayList<>(defaultStrategy.getPerformPhases());
	}
}
