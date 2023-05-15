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
import org.apache.maven.shared.release.ReleaseExecutionException;
import org.apache.maven.shared.release.ReleaseFailureException;
import org.apache.maven.shared.release.ReleaseResult;
import org.apache.maven.shared.release.config.ReleaseDescriptor;
import org.apache.maven.shared.release.env.ReleaseEnvironment;
import org.apache.maven.shared.release.phase.ScmTagPhase;

@Singleton
@Named("unigrid-scm-tag")
public class UnigridScmTag extends ScmTagPhase {
	@Override
	public ReleaseResult execute(
		ReleaseDescriptor releaseDescriptor,
		ReleaseEnvironment releaseEnvironment,
		List<MavenProject> reactorProjects
	) throws ReleaseExecutionException, ReleaseFailureException {
		Optional<MavenProject> moduleProject = reactorProjects.stream()
			.filter(project -> project.getArtifactId().equals(UnigridReleaseStrategy.MODULE_PARENT))
			.findFirst();

		String projectId = ArtifactUtils.versionlessKey(
			moduleProject.get().getGroupId(),
			moduleProject.get().getArtifactId()
		);

		String tag = UnigridReleaseStrategy.VERSION_PREFIX
			+ releaseDescriptor.getProjectReleaseVersion(projectId);

		releaseDescriptor.setScmReleaseLabel(tag);

		super.execute(releaseDescriptor, releaseEnvironment, reactorProjects);

		return new ReleaseResult();
	}
}
