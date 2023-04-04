package quokkaui.build

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.extra
import quokkaui.build.tasks.CollectAarTask
import quokkaui.build.tasks.CollectJarTask
import quokkaui.build.tasks.CollectPomTask
import java.io.File

open class QuokkaPlugin : Plugin<Project> {

    companion object {
        const val EXTENSION_NAME = "quokkaui"
    }
    override fun apply(project: Project) {
        project.extensions.create<QuokkaExtension>(EXTENSION_NAME, project)

        project.tasks.register("collectAar", CollectAarTask::class.java) {
            this.collectDirProperty.set("${project.extra["outDir"]}/collect")

            this.doLast {
                val collectDir = File(collectDirProperty.get())
                commandHelper().openFolder(collectDir.canonicalPath)
            }
        }
        project.tasks.register("collectJar", CollectJarTask::class.java) {
            this.collectDirProperty.set("${project.extra["outDir"]}/collect")

            this.doLast {
                val collectDir = File(collectDirProperty.get())
                commandHelper().openFolder(collectDir.canonicalPath)
            }
        }
        project.tasks.register("collectPom", CollectPomTask::class.java) {
            this.collectDirProperty.set("${project.extra["outDir"]}/collect")

            this.doLast {
                val collectDir = File(collectDirProperty.get())
                commandHelper().openFolder(collectDir.canonicalPath)
            }
        }
    }
}