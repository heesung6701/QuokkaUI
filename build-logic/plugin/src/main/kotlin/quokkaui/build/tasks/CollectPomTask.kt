package quokkaui.build.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.publish.maven.tasks.GenerateMavenPom
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class CollectPomTask : DefaultTask() {

    companion object {
        private val TAG: String = CollectPomTask::class.java.simpleName
    }

    @get:Input
    abstract val collectDirProperty: Property<String>

    init {
        val jarTask = project.tasks.withType(GenerateMavenPom::class.java)
        this.dependsOn(jarTask)
    }

    @TaskAction
    fun collect() {
        val collectDir = File(collectDirProperty.get()).apply {
            mkdirs()
        }
        println("[$TAG] copy ${project.buildDir.canonicalPath} -> $collectDir")
        project.copy {
            from(project.fileTree(project.buildDir.absolutePath.toString())
                .filter {
                    it.name.equals("pom-default.xml") and it.path.contains("release")
                }
                .files) {
                include("**/*.xml")
                rename {
                    "${project.name}-${project.version}.xml"
                }
            }
            includeEmptyDirs = false
            into(collectDir)
        }
    }
}