package quokkaui.build

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create

open class QuokkaPlugin : Plugin<Project> {

    companion object {
        const val EXTENSION_NAME = "quokkaui"
    }
    override fun apply(project: Project) {
        project.extensions.create<QuokkaExtension>(EXTENSION_NAME, project)
    }
}