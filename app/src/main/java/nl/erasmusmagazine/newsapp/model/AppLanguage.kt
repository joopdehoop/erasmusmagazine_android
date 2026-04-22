package nl.erasmusmagazine.newsapp.model

enum class AppLanguage(val code: String, val baseUrl: String) {
    DUTCH("nl", "https://www.erasmusmagazine.nl"),
    ENGLISH("en", "https://www.erasmusmagazine.nl/en");

    companion object {
        fun fromCode(raw: String?): AppLanguage = entries.firstOrNull { it.code == raw } ?: DUTCH
    }
}
