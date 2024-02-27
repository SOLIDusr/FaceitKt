data class FaceitUser(
    val nickname: String, val avatarUrl: String?,
    val country: String, val platforms: Any?,
    val membership: String, val isVerified: Any?,
    val age: String, val wins: Int, val winstreak: String,
    val matches: Int, val averageHSRatio: Int, val averageKDRatio: Float,
    val lvl: Int, val region: String, val elo: Int) {
}
