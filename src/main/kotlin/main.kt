
fun main(){
    val faceitClient = FaceitClient()
    val games = faceitClient.Games()
    val ch = games.getGameMatchmakings("csgo")
//     println(ch)
//     FaceitClient.GamesRegion.values().forEach { println(it.region) }
//    val items = (ch["items"] as? List<*>)?.filterIsInstance<Map<String, Any>>()
//    if (items != null) {
//        for (item in items) {
//            println(item["id"])
//        }
//    }
}
