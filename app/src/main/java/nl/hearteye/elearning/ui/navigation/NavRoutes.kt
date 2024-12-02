package nl.hearteye.elearning.ui.navigation

enum class NavRoutes(val route: String) {
    HOME("home"),
    COURSES("courses"),
    DISCUSSIONS("discussions"),
    MORE("more"),
    COURSE_DETAIL("courseDetail/{courseId}"),
    ANSWER_OVERVIEW("answerOverview/{courseId}")
}
