package quokkaui.build.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class CollectAarTask : DefaultTask() {

    companion object {
        private val TAG: String = CollectAarTask::class.java.simpleName
    }

    @get:Input
    abstract val collectDirProperty: Property<String>

    init {
        val arTask = project.tasks.getByName("assembleRelease")
        this.dependsOn(arTask)
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
                    it.name.equals("${project.name}-release.aar")
                }
                .files) {
                include("**/*.aar")
                rename {
                    "${project.name}-${project.version}-release.aar"
                }
            }
            includeEmptyDirs = false
            into(collectDir)
        }
    }
}