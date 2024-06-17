package quokkaui.build

import quokkaui.OperatingSystem
import quokkaui.getOperatingSystem


interface OsCommandHelper {
    fun openFolder(path: String)
}

fun commandHelper(): OsCommandHelper {
    return when (getOperatingSystem()) {
        OperatingSystem.MAC ->
            DarwinOsCommandHelper
        OperatingSystem.LINUX ->
            LinuxOsCommandHelper
        OperatingSystem.WINDOWS ->
            WindowOsCommandHelper
    }
}

private object DarwinOsCommandHelper : OsCommandHelper {
    override fun openFolder(path: String) {
        Runtime.getRuntime().exec("open $path")
    }
}

private object LinuxOsCommandHelper : OsCommandHelper {
    override fun openFolder(path: String) {
        Runtime.getRuntime().exec("nautilus $path")
    }
}

private object WindowOsCommandHelper : OsCommandHelper {
    override fun openFolder(path: String) {
        Runtime.getRuntime().exec("nautilus $path")
    }
}
