package quokkaui

import org.gradle.api.GradleException
import java.util.*

enum class OperatingSystem {
    LINUX,
    WINDOWS,
    MAC
}

fun getOperatingSystem(): OperatingSystem {
    val os = System.getProperty("os.name").toLowerCase(Locale.US)
    return when {
        os.contains("mac os x") -> OperatingSystem.MAC
        os.contains("darwin") -> OperatingSystem.MAC
        os.contains("osx") -> OperatingSystem.MAC
        os.startsWith("win") -> OperatingSystem.WINDOWS
        os.startsWith("linux") -> OperatingSystem.LINUX
        else -> throw GradleException("Unsupported operating system $os")
    }
}