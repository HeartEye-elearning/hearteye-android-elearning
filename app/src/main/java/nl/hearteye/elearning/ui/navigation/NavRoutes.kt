package nl.hearteye.elearning.ui.navigation

enum class NavRoutes(val route: String) {
    PRELOGIN("prelogin"),
    LOGIN("login"),
    HOME("home"),
    ONBOARDING("onboarding"),
    COURSES("courses"),
    DISCUSSIONS("discussions"),
    MORE("more"),
    COURSE_DETAIL("courseDetail/{courseId}"),
    ANSWER_OVERVIEW("answerOverview/{courseId}"),
    DISCUSSIONS_UPLOAD("discussionsUploadScreen")
}

