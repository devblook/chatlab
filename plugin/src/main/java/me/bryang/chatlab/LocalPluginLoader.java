package me.bryang.chatlab;

import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("deprecation")
public class LocalPluginLoader implements PluginLoader {

	@Override
	public void classloader(final @NotNull PluginClasspathBuilder classpathBuilder) {

		MavenLibraryResolver resolver = new MavenLibraryResolver();

		List<RemoteRepository> repositories = mapRepositories(
			new RepoInput(
				"central",
				"https://repo1.maven.org/maven2/"),

			new RepoInput(
				"unnamed-public",
				"https://repo.unnamed.team/repository/unnamed-public/"),

			new RepoInput(
				"sonatype-snapshot",
				"https://oss.sonatype.org/content/repositories/snapshots/")

		);

		repositories.forEach(resolver::addRepository);

		List<Dependency> dependencies = mapDependencies(
			"org.spongepowered:configurate-hocon:4.0.0",
			"me.fixeddev:commandflow-bukkit:0.6.0",
			"team.unnamed:inject:1.0.1");

		dependencies.forEach(resolver::addDependency);
		classpathBuilder.addLibrary(resolver);
	}

	private List<Dependency> mapDependencies(String... dependencyCoords) {
		return Arrays
			.stream(dependencyCoords)
			.map(coords -> new Dependency(new DefaultArtifact(coords), null))
			.toList();
	}

	private List<RemoteRepository> mapRepositories(RepoInput... repositoryCoords) {
		return Arrays.stream(repositoryCoords)
			.map(value -> new RemoteRepository.Builder
				(value.id, "default", value.url).build())
			.toList();
	}

	private record RepoInput(String id, String url) {

	}
}

