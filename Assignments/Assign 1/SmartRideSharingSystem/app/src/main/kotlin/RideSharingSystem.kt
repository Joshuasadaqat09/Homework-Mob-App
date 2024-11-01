class RideSharingSystem {
    val drivers = mutableListOf<Driver>()
    val riders = mutableListOf<Rider>()

    fun addDriver(driver: Driver) {
        drivers.add(driver)
    }

    fun addRider(rider: Rider) {
        riders.add(rider)
    }

    fun displayDrivers() {
        println("-------------------------------------------------")
        println("| Driver Name | Availability | Rating | Location |")
        println("-------------------------------------------------")
        drivers.forEach {
            val availability = if (it.isAvailable) "Available" else "Unavailable"
            println("| ${it.name.padEnd(11)} | ${availability.padEnd(12)} | ${it.rating}   | ${it.currentLocation} |")
        }
        println("-------------------------------------------------")
    }


    fun displayRiders() {
        println("--------------------------------------------------------")
        println("| Rider Name | Request Status | Current Location | Destination |")
        println("--------------------------------------------------------")
        riders.forEach {
            println("| ${it.name.padEnd(10)} | ${it.requestStatus.padEnd(14)} | ${it.currentLocation} | ${it.destinationLocation} |")
        }
        println("--------------------------------------------------------")
    }

    // Assign a ride to the rider
    fun assignRide(rider: Rider): Ride? {
        val availableDrivers = drivers.filter { it.isAvailable }
        if (availableDrivers.isEmpty()) {
            println("No available drivers for ${rider.name}.")
            return null
        }

        val closestDriver = availableDrivers.minWithOrNull { driver1, driver2 ->
            val distance1 = Utils.haversine(driver1.currentLocation.first, driver1.currentLocation.second,
                rider.currentLocation.first, rider.currentLocation.second)
            val distance2 = Utils.haversine(driver2.currentLocation.first, driver2.currentLocation.second,
                rider.currentLocation.first, rider.currentLocation.second)
            val distanceComparison = distance1.compareTo(distance2)

            if (distanceComparison == 0) {
                driver2.rating.compareTo(driver1.rating)
            } else {
                distanceComparison
            }
        }

        closestDriver?.let { driver ->
            val rideDistance = Utils.haversine(rider.currentLocation.first, rider.currentLocation.second,
                rider.destinationLocation.first, rider.destinationLocation.second)
            val trafficFactor = Utils.simulateTraffic()

            val ride = Ride(rider, driver, rideDistance, 0.0)
            ride.calculateEstimatedTime(trafficFactor)

            driver.setAvailability(false)
            rider.requestStatus = "Accepted" // Update ride request status
            println("Ride Assigned: ${rider.name} -> ${driver.name}")
            println("Distance to Destination: ${ride.distanceToDestination} km")
            println("Estimated Time: ${ride.estimatedTime} hours")
            return ride
        }
        return null
    }

    fun rateDriver(driver: Driver, rating: Double) {
        driver.updateRating(rating)
        println("${driver.name}'s new rating is ${driver.rating}")
    }
}
