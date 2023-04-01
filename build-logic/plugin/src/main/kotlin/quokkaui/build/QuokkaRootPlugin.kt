package quokkaui.build

import org.gradle.api.Plugin
import org.gradle.api.Project
import quokkaui.build.tasks.CollectArchiveTask

abstract class QuokkaRootPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register("collectArchive", CollectArchiveTask::class.java)
    }
}