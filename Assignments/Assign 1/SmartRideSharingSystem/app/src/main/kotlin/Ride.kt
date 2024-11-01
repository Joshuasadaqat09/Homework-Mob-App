data class Ride(
    val rider: Rider,
    val assignedDriver: Driver,
    val distanceToDestination: Double,
    var estimatedTime: Double
) {
    // Calculates distance using the Haversine formula
    fun calculateDistance(): Double {
        return Utils.haversine(
            rider.currentLocation.first, rider.currentLocation.second,
            rider.destinationLocation.first, rider.destinationLocation.second
        )
    }

    // Calculates estimated time for the ride
    fun calculateEstimatedTime(trafficFactor: Double) {
        val distance = calculateDistance()
        val baseTime = distance / 50.0 // Assuming average speed of 50 km/h
        estimatedTime = baseTime * trafficFactor
    }
}
