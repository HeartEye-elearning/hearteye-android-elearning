import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit
import java.util.Locale

fun getTimeAgo(postTime: String): String {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
    val postDate: Date = format.parse(postTime) ?: return "Unknown time"
    val currentTime = System.currentTimeMillis()
    val timeDifference = currentTime - postDate.time

    val minutesAgo = TimeUnit.MILLISECONDS.toMinutes(timeDifference)
    val hoursAgo = TimeUnit.MILLISECONDS.toHours(timeDifference)
    val daysAgo = TimeUnit.MILLISECONDS.toDays(timeDifference)
    val weeksAgo = daysAgo / 7

    return when {
        minutesAgo < 1 -> "Less than a minute ago"
        minutesAgo in 1..59 -> "$minutesAgo minute${if (minutesAgo > 1) "s" else ""} ago"
        hoursAgo in 1..23 -> "$hoursAgo hour${if (hoursAgo > 1) "s" else ""} ago"
        daysAgo in 1..6 -> "$daysAgo day${if (daysAgo > 1) "s" else ""} ago"
        weeksAgo in 1..4 -> "$weeksAgo week${if (weeksAgo > 1) "s" else ""} ago"
        else -> "${daysAgo / 7} week${if (daysAgo / 7 > 1) "s" else ""} ago"
    }
}
