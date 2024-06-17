package quokkaui.build.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.jvm.tasks.Jar
import java.io.File

abstract class CollectJarTask : DefaultTask() {

    companion object {
        private val TAG: String = CollectJarTask::class.java.simpleName
    }

    @get:Input
    abstract val collectDirProperty: Property<String>

    init {
        val jarTask = project.tasks.withType(Jar::class.java)
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
                    it.name.equals("${project.name}-${project.version}-sources.jar")
                }
                .files) {
                include("**/*.jar")
                rename {
                    "${project.name}-${project.version}-sources.jar"
                }
            }
            includeEmptyDirs = false
            into(collectDir)
        }
    }
}