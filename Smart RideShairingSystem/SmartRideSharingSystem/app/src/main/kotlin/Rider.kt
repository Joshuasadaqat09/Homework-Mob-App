data class Rider(
    val name: String,
    var currentLocation: Pair<Double, Double>,
    var destinationLocation: Pair<Double, Double>,
    var requestStatus: String = "In Queue" // Default status
) {
    fun requestRide(system: RideSharingSystem) {
        system.assignRide(this)
    }
}
