package me.bryang.chatlab;

import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings({"UnstableApiUsage"})
public class DependenciesLoader implements PluginLoader {
    @Override
    public void classloader(final @NotNull PluginClasspathBuilder classpathBuilder) {

         MavenLibraryResolver resolver = new MavenLibraryResolver();

         RemoteRepository mavenCentral = new RemoteRepository
                .Builder("central", "default", "https://repo1.maven.org/maven2/")
                .build();

         RemoteRepository unnamedRepo = new RemoteRepository
                .Builder("unnamed-public", "default", "https://repo.unnamed.team/repository/unnamed-public/")
                .build();


         Dependency configurateHocon = new Dependency(
                new DefaultArtifact("org.spongepowered:configurate-hocon:4.0.0"),
                null
        );

        Dependency commandFlow = new Dependency(
                new DefaultArtifact("me.fixeddev:commandflow-bukkit:0.5.2"),
                null);

        Dependency inject = new Dependency(
                new DefaultArtifact("team.unnamed:inject:1.0.1"),
                null);

        resolver.addRepository(mavenCentral);
        resolver.addRepository(unnamedRepo);

        resolver.addDependency(configurateHocon);
        resolver.addDependency(commandFlow);
        resolver.addDependency(inject);

        classpathBuilder.addLibrary(resolver);
    }
}