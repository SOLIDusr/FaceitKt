import okhttp3.OkHttpClient
import okhttp3.Request

class FaceitUser(private val playerName: String) {
    private var playerId: String? = null
    private val gameId = "csgo"
    private val apiHeader = mapOf(
        "Authorization" to "Bearer e7c77750-2387-44c5-9c2d-ba4f9596d083",
        "content-type" to "Application/json"
    )
    private val client = OkHttpClient()

    private val playerIdGet: String?
        get() {
            try {
                val url = "https://open.faceit.com/data/v4/players?nickname=$playerName&game=CSGO"
                val request = Request.Builder()
                    .url(url)
                    .headers(apiHeader.toHeaders())
                    .build()
                val response = client.newCall(request).execute()
                val playerIdData = response.body?.string()?.toJson()
                val playerIdQueue = playerIdData?.get("player_id")?.toString()
                return playerIdQueue
            } catch (e: Exception) {
                println("Error Occured $playerName doesn't exist")
                return null
            }
        }

    fun setPlayerId() {
        playerId = playerIdGet
    }

    fun playerInformation(): Map<String, Any>? {
        try {
            if (playerIdGet == null) {
                throw Exception("Player does not exist")
            }
            val url = "https://open.faceit.com/data/v4/players/$playerIdGet"
            val request = Request.Builder()
                .url(url)
                .headers(apiHeader.toHeaders())
                .build()
            val response = client.newCall(request).execute()
            val playerDataJson = response.body?.string()?.toJson()
            return playerDataJson
        } catch (e: Exception) {
            return null
        }
    }

    fun playerStats(): Map<String, Any>? {
        try {
            val url = "https://open.faceit.com/data/v4/players/$playerIdGet/stats/$gameId"
            val request = Request.Builder()
                .url(url)
                .headers(apiHeader.toHeaders())
                .build()
            val response = client.newCall(request).execute()
            val playerStatsJson = response.body?.string()?.toJson()
            return playerStatsJson
        } catch (e: Exception) {
            return null
        }
    }

    fun playerStatsMap(mapChosen: String): Map<String, Any>? {
        var playerMap = mapChosen
        if ('_' !in playerMap) {
            playerMap = "de_$playerMap"
        }

        try {
            val pdata = playerStats()
            val mapData = pdata?.get("segments") as List<Map<String, Any>>
            for (map in mapData) {
                if (map["label"] == playerMap) {
                    return map
                }
            }
        } catch (e: Exception) {
            return null
        }
        return null
    }
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
