package quokkaui.build

/**
 * This object contains the library group, as well as whether libraries
 * in this group are all required to have the same development version.
 */
data class LibraryGroup(
    val group: String = "unspecified",
    val atomicGroupVersion: Version?,
) : java.io.Serializable {

    // Denotes if the LibraryGroup is atomic
    val requireSameVersion = (atomicGroupVersion != null)

    companion object {
        private const val serialVersionUID = 345435634564L
    }
}