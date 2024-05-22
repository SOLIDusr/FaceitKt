fun main(){
    val faceitClient = FaceitClient()
    val championships = faceitClient.Championships()
    val ch = championships.getChampionships("csgo", "all", "4")
    println(ch)
    
    val items = (ch["items"] as? List<*>)?.filterIsInstance<Map<String, Any>>()
    if (items != null) {
        for (item in items) {
            println(item["id"])
        }
    }
}
