import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.http.HttpClient
import javax.print.AttributeException
import javax.print.attribute.Attribute


class FaceitUser(private val userName: String, game: FaceitUser.Game) {
//    Privates
    private val apiHeader = mapOf(
        "Authorization" to "Bearer e7c77750-2387-44c5-9c2d-ba4f9596d083",
        "content-type" to "Application/json"
    )
    private val client = OkHttpClient()
//  Public
    var userId: String? = ""
    val gameId = game.name

    val userInfo: Map<String, Any>
        get() {
            try {
                if (userId == null) {
                    throw Exception("Player does not exist")
                }
                val url = "https://open.faceit.com/data/v4/players/$userId"
                val request = Request.Builder()
                    .url(url)
                    .headers(apiHeader.toHeaders())
                    .build()
                val response = client.newCall(request).execute()
                val playerDataJson = response.body?.string()?.toJson()
                return playerDataJson!!
            } catch (e: Exception) {
                throw e
            }
        }
    val userStats: Map<String, Any>
        get() {
            try {
            val url = "https://open.faceit.com/data/v4/players/$userId/stats/${gameId}"
            val request = Request.Builder()
                .url(url)
                .headers(apiHeader.toHeaders())
                .build()
            val response = client.newCall(request).execute()
            val playerStatsJson = response.body?.string()?.toJson()
            return playerStatsJson!!
        } catch (e: Exception) { throw e }
        }

//    Init

    init {
        userId = getUserId(userName)

//        userInfo = getUserInfo()
//        userStats = getUserStats()
    }

//    vals to manipulate
    val nickname = userInfo.get("nickname")
    val avatarUrl = if (userInfo.get("avatar") != ""){
        userInfo.get("avatar")
    } else {
        null
    }
    val country = userInfo["country"]
    val platforms = userInfo["platforms"]
    val membership = userInfo["memberships"]
    val isVerified = userInfo["verified"]
    val age = userInfo["activated_at"]
    val gameStats: Any? = if (this.gameId == "csgo"){(userInfo["games"] as Map<*, *>?)!!["cs2"]} else {throw InternalError()}
    val wins = userInfo["lifetime"]?.get("Wins")
    val winstreak = userInfo["lifetime"]?.get("Recent Results")
    val matches = userInfo["lifetime"]?.get("Matches")
    val avgHs = userInfo["lifetime"]?.get("Average Headshots %")
    val avg = gameStats!![""]
    val lvl = gameStats!!["skill_level"]
    val region = gameStats!!["region"]
    val elo = gameStats!!["faceit_elo"]

// This fun throws errors, probably cuz "Skill Level is not it"
    operator fun Any.get(string: String): Any? {
        return if ((this as Map<*, *>)["skill_level"] != null){
            this[string]} else {"Hidden? ${this["skill_level"]}"}
    }

    private fun getUserId(userName: String): String?{
        try {
            val url = "https://open.faceit.com/data/v4/players?nickname=$userName&game=CSGO"
            val request = Request.Builder()
                .url(url)
                .headers(apiHeader.toHeaders())
                .build()
            val response = client.newCall(request).execute()
            val playerIdData = response.body?.string()?.toJson()
            val playerIdQueue = playerIdData?.get("player_id")?.toString()
            return playerIdQueue
        } catch (e: Exception) {
            println("Error Occured $userName doesn't exist")
            return null
        }
    }

    enum class Game(name: String){
        csgo("csgo"),
        lol("lol"),
        dota2("dota2"),
        tf2("tf2")
    }

    fun Map<String, String>.toHeaders(): okhttp3.Headers {
        val builder = okhttp3.Headers.Builder()
        for ((key, value) in this) {
            builder.add(key, value)
        }
        return builder.build()
    }

    fun String.toJson(): Map<String, Any> {
        val gson = com.google.gson.Gson()
        val type = object : com.google.gson.reflect.TypeToken<Map<String, Any>>() {}.type
        return gson.fromJson(this, type)
    }

}
