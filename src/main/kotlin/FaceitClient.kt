import okhttp3.OkHttpClient
import okhttp3.Request
import com.google.gson.Gson


class FaceitClient(apiKey: String = "e7c77750-2387-44c5-9c2d-ba4f9596d083") {

    private val apiHeader = mapOf(
        "Authorization" to "Bearer $apiKey",
        "content-type" to "Application/json"
    )

    private val client = OkHttpClient()

    private fun Map<String, String>.toHeaders(): okhttp3.Headers {
        val builder = okhttp3.Headers.Builder()
        for ((key, value) in this) {
            builder.add(key, value)
        }
        return builder.build()
    }

    private fun String.toJson(): Map<String, Any> {
        val gson = Gson()
        val type = object : com.google.gson.reflect.TypeToken<Map<String, Any>>() {}.type
        return gson.fromJson(this, type)
    }

    operator fun Any?.get(index: Any): Any? {
        return when (this) {
            is Map<*, *> -> this[index]
            is List<*> -> this[index as Int]
            else -> null
        }
    }
    
    fun getRequest(url: String): Map<String, Any> {
        try {
            val request = Request.Builder()
                .url(url)
                .headers(apiHeader.toHeaders())
                .build()
            val response = client.newCall(request).execute()
            val playerDataJson = response.body?.string()?.toJson()
            return playerDataJson!!
        }
        catch (e: Exception){
            throw e
        }
    }

    inner class Championships{

        fun getChampionships(game: String, type: String = "all", limit: String = "10"): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/championships?game=$game&type=$type&offset=0&limit=$limit")
        }

        fun getChampionship(championshipId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/championships/$championshipId")
        }

        fun getChampionshipMatches(championshipId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/championships/$championshipId/matches")
        }

        fun getChampionshipResults(championshipId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/championships/$championshipId/results")
        }

        fun getChampionshipSubscriptions(championshipId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/championships/$championshipId/subscriptions")
        }
    }

    inner class Games {
        fun getGames(): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/games")
        }

        fun getGameDetails(gameId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/games/$gameId")
        }
    }

    inner class Hubs {
        fun getHubs(): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/hubs")
        }

        fun getHubDetails(hubId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/hubs/$hubId")
        }
    }

    inner class Leaderboards {
        fun getLeaderboards(): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/leaderboards")
        }

        fun getLeaderboardDetails(leaderboardId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/leaderboards/$leaderboardId")
        }
    }

    inner class Leagues {
        fun getLeagues(): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/leagues")
        }

        fun getLeagueDetails(leagueId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/leagues/$leagueId")
        }
    }

    inner class Matches {
        fun getMatches(): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/matches")
        }

        fun getMatchDetails(matchId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/matches/$matchId")
        }
    }

    inner class MatchMakings {
        fun getMatchMakings(): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/matchmakings")
        }

        fun getMatchMakingDetails(matchMakingId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/matchmakings/$matchMakingId")
        }
    }

    inner class Organizers {
        fun getOrganizers(): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/organizers")
        }

        fun getOrganizerDetails(organizerId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/organizers/$organizerId")
        }
    }

    inner class Players {
        fun getPlayers(): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/players")
        }

        fun getPlayerDetails(playerId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/players/$playerId")
        }
    }

    inner class Rankings {
        fun getRankings(): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/rankings/games")
        }

        fun getRankingDetails(rankingId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/rankings/games/$rankingId")
        }
    }

    inner class Search {
        fun search(query: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/search?query=$query")
        }
    }

    inner class Teams {
        fun getTeams(): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/teams")
        }

        fun getTeamDetails(teamId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/teams/$teamId")
        }
    }

    inner class Tournaments {
        fun getTournaments(): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/tournaments")
        }

        fun getTournamentDetails(tournamentId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/tournaments/$tournamentId")
        }
    }
}
