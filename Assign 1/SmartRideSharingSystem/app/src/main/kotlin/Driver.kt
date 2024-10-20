data class Driver(
    val name: String,
    var currentLocation: Pair<Double, Double>,
    var isAvailable: Boolean = true,
    var rating: Double = 5.0
) {
    fun setAvailability(available: Boolean) {
        isAvailable = available
    }

    fun updateRating(newRating: Double) {
        rating = (rating + newRating) / 2.0
    }
}
