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
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.maven.artifact.ArtifactUtils;
import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.release.ReleaseExecutionException;
import org.apache.maven.shared.release.ReleaseResult;
import org.apache.maven.shared.release.config.ReleaseDescriptor;
import org.apache.maven.shared.release.env.ReleaseEnvironment;
import org.apache.maven.shared.release.phase.MapVersionsPhase;
import org.apache.maven.shared.release.util.ReleaseUtil;

import org.apache.maven.shared.release.versions.DefaultVersionInfo;
import org.apache.maven.shared.release.versions.VersionParseException;

@Singleton
@Named("unigrid-map-development-versions")
public class UnigridMapDevelopmentVersionsPhase extends MapVersionsPhase {

	@Override
	public ReleaseResult execute(
		ReleaseDescriptor releaseDescriptor,
		ReleaseEnvironment releaseEnvironment,
		List<MavenProject> reactorProjects
	) throws ReleaseExecutionException {
		MavenProject rootProject = ReleaseUtil.getRootProject(reactorProjects);

		if (releaseDescriptor.isAutoVersionSubmodules() && ArtifactUtils.isSnapshot(rootProject.getVersion())) {
			super.execute(releaseDescriptor, releaseEnvironment, reactorProjects);
		} else {
			for (MavenProject project : reactorProjects) {
				String projectId = ArtifactUtils.versionlessKey(
					project.getGroupId(),
					project.getArtifactId()
				);

				try {
					String nextVersion = getDevelopmentVersion(project, System.getProperty("module"));
					releaseDescriptor.addDevelopmentVersion(projectId, nextVersion);
				} catch (VersionParseException ex) {
					ex.printStackTrace();
				}
			}
		}

		return new ReleaseResult();
	}

	private String getDevelopmentVersion(
		MavenProject project,
		String module
	) throws VersionParseException {
		if (module.equals(UnigridReleaseStrategy.MODULE_FX) && (project.getArtifactId().equals(
			UnigridReleaseStrategy.MODULE_BOOTSTRAP)
			|| project.getArtifactId().equals(UnigridReleaseStrategy.MODULE_DESKTOP))) {
			return project.getVersion();
		} else if (module.equals(UnigridReleaseStrategy.MODULE_BOOTSTRAP) && project.getArtifactId().equals(
			UnigridReleaseStrategy.MODULE_FX)) {
			return project.getVersion();
		}

		return new DefaultVersionInfo(project.getVersion()).getNextVersion().getSnapshotVersionString();
	}
}
