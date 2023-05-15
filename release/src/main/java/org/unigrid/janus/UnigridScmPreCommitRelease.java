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

import java.util.List;
import java.util.Optional;
import javax.inject.Named;
import javax.inject.Singleton;
import org.apache.maven.artifact.ArtifactUtils;
import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.release.ReleaseResult;
import org.apache.maven.shared.release.config.ReleaseDescriptor;
import org.apache.maven.shared.release.env.ReleaseEnvironment;
import org.apache.maven.shared.release.phase.ScmCommitPreparationPhase;

@Singleton
@Named("unigrid-scm-pre-commit-release")
public class UnigridScmPreCommitRelease extends ScmCommitPreparationPhase {
	@Override
	public ReleaseResult execute(
		ReleaseDescriptor releaseDescriptor,
		ReleaseEnvironment releaseEnvironment,
		List<MavenProject> reactorProjects
	) {
		String module = System.getProperty("module").equals(UnigridReleaseStrategy.MODULE_FX)
			? UnigridReleaseStrategy.MODULE_FX
			: UnigridReleaseStrategy.MODULE_BOOTSTRAP;

		Optional<MavenProject> moduleProject = reactorProjects.stream()
			.filter(project -> project.getArtifactId().equals(module))
			.findFirst();

		String projectId = ArtifactUtils.versionlessKey(
			moduleProject.get().getGroupId(),
			moduleProject.get().getArtifactId()
		);

		String tag = UnigridReleaseStrategy.VERSION_PREFIX
			+ releaseDescriptor.getProjectReleaseVersion(projectId)
			+ UnigridReleaseStrategy.VERSION_DELIMETER
			+ module;

		releaseDescriptor.setScmReleaseLabel(tag);

		return new ReleaseResult();
	}
}
