package quokkaui.build

import org.gradle.api.GradleException
import org.gradle.api.provider.Provider
import org.gradle.api.services.BuildService
import org.gradle.api.services.BuildServiceParameters
import org.tomlj.Toml
import org.tomlj.TomlParseResult
import org.tomlj.TomlTable

/**
 * Loads Library groups and versions from a specified TOML file.
 */

abstract class LibraryVersionsService : BuildService<LibraryVersionsService.Parameters> {
    interface Parameters : BuildServiceParameters {
        var tomlFileName: String
        var tomlFileContents: Provider<String>
    }

    private val parsedTomlFile: TomlParseResult by lazy {
        val result = Toml.parse(parameters.tomlFileContents.get())
        if (result.hasErrors()) {
            val issues = result.errors().joinToString(separator = "\n") {
                "${parameters.tomlFileName}:${it.position()}: ${it.message}"
            }
            throw Exception("${parameters.tomlFileName} file has issues.\n$issues")
        }
        result
    }

    private fun getTable(key: String): TomlTable {
        return parsedTomlFile.getTable(key)
            ?: throw GradleException("Library versions toml file is missing [$key] table")
    }

    // map from name of constant to Version
    val libraryVersions: Map<String, Version> by lazy {
        val versions = getTable("versions")
        versions.keySet().associateWith { versionName ->
            val versionValue =
                    versions.getString(versionName)!!
            Version.parseOrNull(versionValue)
                ?: throw GradleException(
                    "$versionName does not match expected format - $versionValue"
                )
        }
    }

    // map of library groups keyed by their variable name in the toml file
    val libraryGroups: Map<String, LibraryGroup> by lazy {
        val result = mutableMapOf<String, LibraryGroup>()
        for (association in libraryGroupAssociations) {
            result[association.declarationName] = association.libraryGroup
        }
        result
    }

    // map of library groups keyed by group name
    val libraryGroupsByGroupId: Map<String, LibraryGroup> by lazy {
        val result = mutableMapOf<String, LibraryGroup>()
        for (association in libraryGroupAssociations) {
            // Check for duplicate groups
            val groupId = association.libraryGroup.group
            val existingAssociation = result[groupId]
            if (existingAssociation != null) {
                if (association.overrideIncludeInProjectPaths.isEmpty()) {
                    throw GradleException(
                        "Duplicate library group $groupId defined in " +
                                "${association.declarationName} does not set overrideInclude. " +
                                "Declarations beyond the first can only have an effect if they set " +
                                "overrideInclude")
                }
            } else {
                result[groupId] = association.libraryGroup
            }
        }
        result
    }

    // map from project name to group override if applicable
    val overrideLibraryGroupsByProjectPath: Map<String, LibraryGroup> by lazy {
        val result = mutableMapOf<String, LibraryGroup>()
        for (association in libraryGroupAssociations) {
            for (overridePath in association.overrideIncludeInProjectPaths) {
                result[overridePath] = association.libraryGroup
            }
        }
        result
    }

    private val libraryGroupAssociations: List<LibraryGroupAssociation> by lazy {
        val groups = getTable("groups")

        fun readGroupVersion(groupDefinition: TomlTable, groupName: String, key: String): Version? {
            val versionRef = groupDefinition.getString(key) ?: return null
            if (!versionRef.startsWith(VersionReferencePrefix)) {
                throw GradleException(
                    "Group entry $key is expected to start with $VersionReferencePrefix"
                )
            }
            // name without `versions.`
            val atomicGroupVersionName = versionRef.removePrefix(
                VersionReferencePrefix
            )
            return libraryVersions[atomicGroupVersionName] ?: error(
                "Group entry $groupName specifies $atomicGroupVersionName, but such version " +
                        "doesn't exist"
            )
        }
        val result = mutableListOf<LibraryGroupAssociation>()
        for (name in groups.keySet()) {
            // get group name
            val groupDefinition = groups.getTable(name)!!
            val groupName = groupDefinition.getString("group")!!
            val finalGroupName = groupName

            // get group version, if any
            val atomicGroupVersion = readGroupVersion(
                groupDefinition = groupDefinition,
                groupName = groupName,
                key = AtomicGroupVersion
            )
            val groupVersion = atomicGroupVersion

            val overrideApplyToProjects = (
                    groupDefinition.getArray("overrideInclude")?.toList() ?: listOf()
                    ).map { it -> it as String }

            val group = LibraryGroup(finalGroupName, groupVersion)
            val association = LibraryGroupAssociation(name, group, overrideApplyToProjects)
            result.add(association)
        }
        result
    }
}

// a LibraryGroupSpec knows how to associate a LibraryGroup with the appropriate projects
data class LibraryGroupAssociation(
    // the name of the variable to which it is assigned in the toml file
    val declarationName: String,
    // the group
    val libraryGroup: LibraryGroup,
    // the paths of any additional projects that this group should be assigned to
    val overrideIncludeInProjectPaths: List<String>
)

private const val VersionReferencePrefix = "versions."
private const val AtomicGroupVersion = "atomicGroupVersion"
