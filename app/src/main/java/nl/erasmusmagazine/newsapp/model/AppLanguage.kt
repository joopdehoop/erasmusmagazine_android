package nl.erasmusmagazine.newsapp.model

enum class AppLanguage(
    val code: String,
    val siteBaseUrl: String,
    val postsApiUrl: String,
    val tipFormUrl: String
) {
    DUTCH(
        code = "nl",
        siteBaseUrl = "https://www.erasmusmagazine.nl",
        postsApiUrl = "https://www.erasmusmagazine.nl/wp-json/wp/v2/posts",
        tipFormUrl = "https://www.erasmusmagazine.nl/tip-ons/"
    ),
    ENGLISH(
        code = "en",
        siteBaseUrl = "https://www.erasmusmagazine.nl/en",
        postsApiUrl = "https://www.erasmusmagazine.nl/en/wp-json/wp/v2/posts",
        tipFormUrl = "https://www.erasmusmagazine.nl/en/tip-the-editor/"
    );

    companion object {
        fun fromCode(raw: String?): AppLanguage = entries.firstOrNull { it.code == raw } ?: DUTCH
    }
}
