import okhttp3.OkHttpClient
import okhttp3.Request
import com.google.gson.Gson
import jdk.jfr.Experimental


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


    enum class ChampionshipType(val type: String) {
        ALL("all"),
        UPCOMING("upcoming"),
        ONGOING("ongoing"),
        PAST("past")
    }

    inner class Championships{

        fun getChampionships(game: String, type: ChampionshipType = ChampionshipType.ALL, limit: String = "10"): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/championships?game=$game&type=${type.type}&offset=0&limit=$limit")
        }

        fun getChampionship(championshipId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/championships/$championshipId")
        }

        fun getChampionshipMatches(championshipId: String, type: ChampionshipType = ChampionshipType.ALL): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/championships/$championshipId/matches?type=${type.type}")
        }

        fun getChampionshipResults(championshipId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/championships/$championshipId/results")
        }

        fun getChampionshipSubscriptions(championshipId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/championships/$championshipId/subscriptions")
        }
    }

    @Deprecated("GamesRegion enum is unverified, as faceit did not provide correct region values")
    enum class GamesRegion(val region: String) {
        ALL("all"),
        EU("eu"),
        NA("na"),
        AS("as"),
        KR("kr"),
        RU("ru"),
        JP("jp"),
        BR("br"),
        AU("au"),
        OCE("oce"),
        GLOBAL("global")
    }

    inner class Games {
        fun getGames(): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/games")
        }

        fun getGameDetails(gameId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/games/$gameId")
        }
    
        @Deprecated("GamesRegion enum is unverified, as faceit did not provide correct region values")
        fun getGameMatchmakings(gameId: String, region: GamesRegion = GamesRegion.ALL): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/games/$gameId/matchmakings?region=${region.region}")
        }

        fun getGameParent(gameId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/games/$gameId")
        }

        fun getGameQueues(gameId: String, entityType: String = "hub", entityId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/games/$gameId/queues?entity_type=$entityType&entity_id=$entityId")
        }

        fun getGameQueueDetails(gameId: String, queueId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/games/$gameId/queues/$queueId")
        }

        fun getGameQueueBans(gameId: String, queueId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/games/$gameId/queues/$queueId/bans")
        }

        @Deprecated("GamesRegion enum is unverified, as faceit did not provide correct region values")
        fun getGameRegionQueues(gameId: String, region: GamesRegion = GamesRegion.ALL): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/games/$gameId/regions/${region.region}/queues")
        }
    }

    enum class MatchesType(val type: String) {
        ALL("all"),
        UPCOMING("upcoming"),
        ONGOING("ongoing"),
        PAST("past")
    }
    inner class Hubs {

        fun getHubDetails(hubId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/hubs/$hubId")
        }

        fun getHubMatches(hubId: String, type: MatchesType = MatchesType.ALL): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/hubs/$hubId/matches?type=${type.type}")
        }

        fun getHubMembers(hubId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/hubs/$hubId/members")
        }

        fun getHubRoles(hubId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/hubs/$hubId/roles")
        }

        fun getHubRules(hubId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/hubs/$hubId/rules")
        }
    }

    inner class Leaderboards {
        fun getChampionshipsLeaderboards(championshipId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/leaderboards/championships/$championshipId")
        }

        fun getChampionshipsGroupRanking(championshipId: String, groupId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/leaderboards/championships/$championshipId/groups/$groupId")
        }

        fun getHubLeaderboards(hubId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/leaderboards/hubs/$hubId")
        }

        fun getHubAlltimeRanking(hubId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/leaderboards/hubs/$hubId/general")
        }

        fun getHubSeasonRanking(hubId: String, season: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/leaderboards/hubs/$hubId/seasons/$season")
        }

        fun getLeaderboard(leaderboardId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/leaderboards/$leaderboardId")
        }

        fun getLeaderboardPlayers(leaderboardId: String, playerId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/leaderboards/$leaderboardId/players/$playerId")
        }
    }

    inner class Leagues {
        fun getLeagueDetails(leagueId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/leagues/$leagueId")
        }
        
        fun getLeagueSeasonDetails(leagueId: String, seasonId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/leagues/$leagueId/seasons/$seasonId")
        }

        fun getLeagueSeasonPlayerDetails(leagueId: String, seasonId: String, playerId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/leagues/$leagueId/seasons/$seasonId/players/$playerId")
        }
    }

    inner class Matches {

        fun getMatchDetails(matchId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/matches/$matchId")
        }

        fun getMatchStats(matchId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/matches/$matchId/stats")
        }
    }

    inner class MatchMakings {

        fun getMatchMakingDetails(matchMakingId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/matchmakings/$matchMakingId")
        }   
    }

    enum class TournamentTypes(val type: String) {
        UPCOMING("upcoming"),
        PAST("past")
    }

    inner class Organizers {
        fun getOrganizers(): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/organizers")
        }

        fun getOrganizerDetails(organizerId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/organizers/$organizerId")
        }

        fun getOrganizersChampionships(organizerId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/organizers/$organizerId/championships")
        }

        fun getOrganizersGames(organizerId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/organizers/$organizerId/games")
        }

        fun getOrganizersHubs(organizerId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/organizers/$organizerId/hubs")
        }

        fun getOrganizersTournaments(organizerId: String, type: TournamentTypes = TournamentTypes.UPCOMING): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/organizers/$organizerId/tournaments?type=${type.type}")
        }
    }

    inner class Players {
        fun getPlayers(nickname: String = "", gameString: String = "", gamePlayerId: String = ""): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/players?nickname=$nickname&game=$gameString&game_player_id=$gamePlayerId")
        }

        fun getPlayerDetails(playerId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/players/$playerId")
        }

        fun getPlayerBans(playerId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/players/$playerId/bans")
        }

        fun getPlayerGameStats(playerId: String, gameId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/players/$playerId/games/$gameId/stats")
        }

        fun getPlayerHistory(playerId: String, gameString: String, from: String = "", to: String = ""): Map<String, Any> {
            var url = "https://open.faceit.com/data/v4/players/$playerId/history?game=$gameString"
            if (from.isNotEmpty()) url += "&from=$from"
            if (to.isNotEmpty()) url += "&to=$to"
            return getRequest(url)
        }

        fun getPlayerHubs(playerId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/players/$playerId/hubs")
        }

        fun getPlayerStats(playerId: String, gameId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/players/$playerId/stats/$gameId")
        }

        fun getPlayerTeams(playerId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/players/$playerId/teams")
        }

        fun getPlayerTournaments(playerId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/players/$playerId/tournaments")
        }
    }

    @Deprecated("GamesRegion enum is unverified, as faceit did not provide correct region values")
    enum class RankingsRegions(val region: String) {
        ALL("all"),
        EU("eu"),
        NA("na"),
        AS("as"),
        KR("kr"),
        RU("ru"),
        JP("jp"),
        BR("br"),
        AU("au"),
        OCE("oce"),
        GLOBAL("global")
    }

    inner class Rankings {
        @Deprecated("Region is not verified, as faceit did not provide correct region values")
        fun getGlobalRanking(gameId: String, region: RankingsRegions = RankingsRegions.ALL): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/rankings/games/$gameId/regions/${region.region}")
        }
        @Deprecated("Region is not verified, as faceit did not provide correct region values")
        fun getUserRanking(playerId: String, gameId: String, region: RankingsRegions = RankingsRegions.ALL): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/rankings/games/$gameId/regions/${region.region}/players/$playerId")
        }
    }

    inner class Search {
        @Deprecated("Region is not verified, as faceit did not provide correct region values")
        fun searchChampionships(championshipName: String, gameString: String, region: GamesRegion, type: ChampionshipType): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/search/championships?name=$championshipName&game=$gameString&region=${region.region}&type=${type.type}")
        }
        @Deprecated("Region is not verified, as faceit did not provide correct region values")
        fun searchClans(clanName: String, gameString: String, region: GamesRegion): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/search/clans?name=$clanName&game=$gameString&region=${region.region}")
        }
        @Deprecated("Region is not verified, as faceit did not provide correct region values")
        fun searchHubs(hubName: String, gameString: String, region: GamesRegion): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/search/hubs?name=$hubName&game=$gameString&region=${region.region}")
        }

        fun searchOrganizers(organizerName: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/search/organizers?name=$organizerName")
        }

        fun searchPlayers(playerName: String, gameString: String, country: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/search/players?nickname=$playerName&game=$gameString&country=$country")
        }

        fun searchTeams(teamName: String, gameString: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/search/teams?name=$teamName&game=$gameString")
        }
        @Deprecated("Region is not verified, as faceit did not provide correct region values")
        fun searchTournaments(tournamentName: String, gameString: String, region: GamesRegion): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/search/tournaments?name=$tournamentName&game=$gameString&region=${region.region}")
        }
    }

    inner class Teams {

        fun getTeam(teamId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/teams/$teamId")
        }

        fun getTeamStats(teamId: String, gameId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/teams/$teamId/stats/$gameId")
        }

        fun getTeamTournaments(teamId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/teams/$teamId/tournaments")
        }

    }

    inner class Tournaments {
        @Deprecated("Region is not verified, as faceit did not provide correct region values")
        fun getTournamentDetails(tournamentId: String, region: GamesRegion? = null): Map<String, Any> {
            if (region != null) {
                return getRequest("https://open.faceit.com/data/v4/tournaments/$tournamentId?region=${region.region}")
            }
            return getRequest("https://open.faceit.com/data/v4/tournaments/$tournamentId")
        }

        fun getTournamentBrackets(tournamentId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/tournaments/$tournamentId/brackets")
        }

        fun getTournamentMatches(tournamentId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/tournaments/$tournamentId/matches")
        }

        fun getTournamentTeams(tournamentId: String): Map<String, Any> {
            return getRequest("https://open.faceit.com/data/v4/tournaments/$tournamentId/teams")
        }
    }
}
