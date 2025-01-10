package nl.hearteye.elearning.ui.utils

enum class CategoryOption(val displayName: String) {
    FINDINGS("Findings"),
    CASE_STUDIES("Case studies"),
    INTERPRETATION("Interpretation"),
    TROUBLESHOOTING("Troubleshooting"),
    BEST_PRACTICES("Best practices");

    companion object {
        fun fromDisplayName(name: String): CategoryOption? {
            return values().find { it.displayName == name }
        }
    }
}
