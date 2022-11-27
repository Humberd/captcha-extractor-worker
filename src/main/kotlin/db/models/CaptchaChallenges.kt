package db.models

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.text

object CaptchaChallenges: Table<Nothing>("t_captcha_challenge") {
    val id = text("id").primaryKey()
    val imageId = text("image_id")
    val badgeCount = int("badge_count")
    val imageBase64Src = text("image_base64_src")
}
