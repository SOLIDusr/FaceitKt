import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * FaceitKt is the first API wrapper for Faceit written entirely in Kotlin.
 *
 * It currently supports finding player data for CS2 and will soon support additional games.
 *
 * copyright (c) 2024-present.
 *
 * licence GNU GPL, see LICENSE for more details.
 *
 * @author SOLIDusr
 */

class FaceitKtOld() {

    /**
     * class FaceitUser. Used for main interactions with player data.
     * @property userName Name of the user on Faceit. WARNING: Case sensitive!
     * @property gameId class Game. What game will be requested.
     */
    class FaceitUser(private val userName: String, game: Game) {

        //    Privates
        private val apiHeader = mapOf(
            "Authorization" to "Bearer e7c77750-2387-44c5-9c2d-ba4f9596d083",
            "content-type" to "Application/json"
        )

        private val client = OkHttpClient()

        //  Public
        var userId: String? = ""
        val gameId = game.name

        private val userInfo: Map<String, Any>
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
        private val userStats: Map<String, Any>
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
                } catch (e: Exception) {
                    throw e
                }
            }

        //    Init

        init {
            userId = getUserId(userName)

            //        userInfo = getUserInfo()
            //        userStats = getUserStats()
        }

        //    vals to manipulate
        val nickname = userInfo.get("nickname")
        val avatarUrl = if (userInfo.get("avatar") != "") {
            userInfo.get("avatar")
        } else {
            null
        }
        val country = userInfo["country"]
        val platforms = userInfo["platforms"]
        val membership = userInfo["memberships"]
        val isVerified = userInfo["verified"]
        val age = userInfo["activated_at"]
        private val gameStats: Any? = if (this.gameId == "csgo") {
            (userInfo["games"] as Map<*, *>?)!!["cs2"]
        } else {
            throw InternalError()
        }
        val wins = userStats["lifetime"]?.get("Wins")
        val winstreak = userStats["lifetime"]?.get("Recent Results")
        val matches = userStats["lifetime"]?.get("Matches")
        val averageHSRatio = userStats["lifetime"]?.get("Average Headshots %")
        val averageKDRatio = userStats["lifetime"]?.get("Average K/D Ratio")
        val lvl = gameStats!!["skill_level"]
        val region = gameStats!!["region"]
        val elo = gameStats!!["faceit_elo"]

        // This fun throws errors, probably cuz "Skill Level is not it"
        private operator fun Any.get(string: String): Any? {
            return if ((this as Map<String, *>)[string] != null) {
                this[string]
            } else {
                "Hidden? ${this[string]}"
            }
        }

        private fun getUserId(userName: String): String? {
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


        private fun Map<String, String>.toHeaders(): okhttp3.Headers {
            val builder = okhttp3.Headers.Builder()
            for ((key, value) in this) {
                builder.add(key, value)
            }
            return builder.build()
        }

        private fun String.toJson(): Map<String, Any> {
            val gson = com.google.gson.Gson()
            val type = object : com.google.gson.reflect.TypeToken<Map<String, Any>>() {}.type
            return gson.fromJson(this, type)
        }
    }
    enum class Game(name: String) {
        csgo("csgo"),
        lol("lol"),
        dota2("dota2"),
        tf2("tf2")
    }
}
