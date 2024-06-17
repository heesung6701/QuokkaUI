package quokkaui.build.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.bundling.Zip
import org.gradle.kotlin.dsl.extra
import quokkaui.build.commandHelper
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

abstract class CollectArchiveTask : DefaultTask() {

    companion object {
        private val TAG: String = CollectArchiveTask::class.java.simpleName
    }
    private val collectDirName = collectDirName()

    private val collectDir: File =
        File("${project.extra["outDir"]!!}/$collectDirName")

    init {
        println("[$TAG] collectDir = $collectDir")
        collectDir.mkdirs()

        val aarTasks = project.subprojects.map { subproject ->
            subproject.tasks.withType(CollectAarTask::class.java) {
                this.collectDirProperty.set(collectDir.absolutePath)
            }
        }

        val jarTasks = project.subprojects.map { subproject ->
            subproject.tasks.withType(CollectJarTask::class.java) {
                this.collectDirProperty.set(collectDir.absolutePath)
            }
        }

        val pomTasks = project.subprojects.map { subproject ->
            subproject.tasks.withType(CollectPomTask::class.java) {
                this.collectDirProperty.set(collectDir.absolutePath)
            }
        }
        this.dependsOn(aarTasks)
        this.dependsOn(jarTasks)
        this.dependsOn(pomTasks)

        val zipTask = project.tasks.register("createZip", Zip::class.java) {
            this.from("$collectDir")
            this.archiveFileName.set("quokkaui-$collectDirName.zip")
            this.destinationDirectory.set(collectDir.parentFile)
            this.mustRunAfter(aarTasks)
        }
        this.finalizedBy(zipTask.get())

        this.doLast {
            commandHelper().openFolder(collectDir.canonicalPath)
        }
    }

    @TaskAction
    fun action() {

    }

    private fun collectDirName(): String =
        SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
}