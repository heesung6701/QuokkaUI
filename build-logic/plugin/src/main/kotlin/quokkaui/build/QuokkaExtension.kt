package quokkaui.build

import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import java.io.File

open class QuokkaExtension(private val project: Project) {
    @JvmField
    val LibraryVersions: Map<String, Version>
    @JvmField
    val AllLibraryGroups: List<LibraryGroup>

    val libraryGroupsByGroupId: Map<String, LibraryGroup>
    val overrideLibraryGroupsByProjectPath: Map<String, LibraryGroup>

    val mavenGroup: LibraryGroup?

    private val versionService: LibraryVersionsService

    init {
        val tomlFileName = "libraryversions.toml"
        val toml = lazyReadFile(tomlFileName)

        versionService = project.gradle.sharedServices.registerIfAbsent(
            "libraryVersionsService",
            LibraryVersionsService::class.java
        ) {
            parameters.tomlFileName = tomlFileName
            parameters.tomlFileContents = toml
        }.get()
        LibraryVersions = versionService.libraryVersions
        AllLibraryGroups = versionService.libraryGroups.values.toList()
        libraryGroupsByGroupId = versionService.libraryGroupsByGroupId
        overrideLibraryGroupsByProjectPath = versionService.overrideLibraryGroupsByProjectPath

        mavenGroup = chooseLibraryGroup()
        chooseProjectVersion()
    }

    var name: Property<String?> = project.objects.property(String::class.java)
    fun setName(newName: String) {
        name.set(newName)
    }

    /**
     * Maven version of the library.
     *
     * Note that, setting this is an error if the library group sets an atomic version.
     * If the build is a multiplatform build, this value will be overridden by
     * the [mavenMultiplatformVersion] property when it is provided.
     *
     * @see mavenMultiplatformVersion
     */
    var mavenVersion: Version? = null
        set(value) {
            field = value
            chooseProjectVersion()
        }
        get() = field

    internal var projectDirectlySpecifiesMavenVersion: Boolean = false

    /**
     * Returns a string explaining the value of mavenGroup
     */
    fun explainMavenGroup(): List<String> {
        val explanationBuilder = mutableListOf<String>()
        chooseLibraryGroup(explanationBuilder)
        return explanationBuilder
    }

    private fun lazyReadFile(fileName: String): Provider<String> {
        val fileProperty = project.objects.fileProperty().fileValue(
            File(project.rootDir.path, fileName)
        )
        return project.providers.fileContents(fileProperty).asText
    }

    private fun chooseLibraryGroup(explanationBuilder: MutableList<String>? = null): LibraryGroup? {
        return getLibraryGroupFromProjectPath(project.path, explanationBuilder)
    }

    private fun substringBeforeLastColon(projectPath: String): String {
        val lastColonIndex = projectPath.lastIndexOf(":")
        return projectPath.substring(0, lastColonIndex)
    }

    // gets the library group from the project path, including special cases
    private fun getLibraryGroupFromProjectPath(
        projectPath: String,
        explanationBuilder: MutableList<String>? = null
    ): LibraryGroup? {
        val overridden = overrideLibraryGroupsByProjectPath.get(projectPath)
        explanationBuilder?.add(
            "Library group (in libraryversions.toml) having" +
                    " overrideInclude=[\"$projectPath\"] is $overridden"
        )
        if (overridden != null)
            return overridden

        val result = getStandardLibraryGroupFromProjectPath(projectPath, explanationBuilder)
        if (result != null)
            return result

        // samples are allowed to be nested deeper
        if (projectPath.contains("samples")) {
            val parentPath = substringBeforeLastColon(projectPath)
            return getLibraryGroupFromProjectPath(parentPath, explanationBuilder)
        }
        return null
    }

    // simple function to get the library group from the project path, without special cases
    private fun getStandardLibraryGroupFromProjectPath(
        projectPath: String,
        explanationBuilder: MutableList<String>?
    ): LibraryGroup? {
        // Get the text of the library group, something like "androidx.core"
        val parentPath = substringBeforeLastColon(projectPath)

        if (parentPath == "") {
            explanationBuilder?.add("Parent path for $projectPath is empty")
            return null
        }
        // convert parent project path to groupId
        val groupIdText =
            "${LibraryConfig.GROUP_ID}.${parentPath.substring(1).replace(':', '.')}"


        // get the library group having that text
        val result = libraryGroupsByGroupId[groupIdText]
        explanationBuilder?.add(
            "Library group (in libraryversions.toml) having group=\"$groupIdText\" is $result"
        )
        return result
    }

    private fun chooseProjectVersion() {
        val version: Version
        val group: String? = mavenGroup?.group
        val groupVersion: Version? = mavenGroup?.atomicGroupVersion
        val mavenVersion: Version? = mavenVersion
        if (mavenVersion != null) {
            projectDirectlySpecifiesMavenVersion = true
            if (groupVersion != null) {
                throw GradleException(
                    "Cannot set mavenVersion (" + mavenVersion +
                            ") for a project (" + project +
                            ") whose mavenGroup already specifies forcedVersion (" + groupVersion +
                            ")"
                )
            } else {
                verifyVersionExtraFormat(mavenVersion)
                version = mavenVersion
            }
        } else {
            projectDirectlySpecifiesMavenVersion = false
            if (groupVersion != null) {
                verifyVersionExtraFormat(groupVersion)
                version = groupVersion
            } else {
                return
            }
        }
        if (group != null) {
            project.group = group
        }
        project.version = if (isSnapshotBuild()) version.copy(extra = "-SNAPSHOT") else version
        versionIsSet = true
    }

    private fun verifyVersionExtraFormat(version: Version) {
        val ALLOWED_EXTRA_PREFIXES = listOf("-alpha", "-beta", "-rc", "-dev", "-SNAPSHOT")
        val extra = version.extra
        if (extra != null) {
            if (!version.isSnapshot()) {
                if (ALLOWED_EXTRA_PREFIXES.any { extra.startsWith(it) }) {
                    for (potentialPrefix in ALLOWED_EXTRA_PREFIXES) {
                        if (extra.startsWith(potentialPrefix)) {
                            val secondExtraPart = extra.removePrefix(
                                potentialPrefix
                            )
                            if (secondExtraPart.toIntOrNull() == null) {
                                throw IllegalArgumentException(
                                    "Version $version is not" +
                                            " a properly formatted version, please ensure that " +
                                            "$potentialPrefix is followed by a number only"
                                )
                            }
                        }
                    }
                } else {
                    throw IllegalArgumentException(
                        "Version $version is not a proper " +
                                "version, version suffixes following major.minor.patch should " +
                                "be one of ${ALLOWED_EXTRA_PREFIXES.joinToString(", ")}"
                    )
                }
            }
        }
    }

    private var versionIsSet = false
    fun isVersionSet(): Boolean {
        return versionIsSet
    }
}

fun isSnapshotBuild() = System.getenv("SNAPSHOT") != null
