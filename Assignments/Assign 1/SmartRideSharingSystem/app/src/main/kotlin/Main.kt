import java.util.Scanner

fun main() {
    val system = RideSharingSystem()
    val scanner = Scanner(System.`in`)

    while (true) {
        println("\nMenu:")
        println("1. Add Driver")
        println("2. Add Rider")
        println("3. Request Ride")
        println("4. Display Drivers")
        println("5. Display Riders")
        println("6. Rate Driver")
        println("7. Exit")
        print("Choose an option: ")

        when (scanner.nextInt()) {
            1 -> {
                // Add Driver
                println("Enter driver's name: ")
                val name = scanner.next()
                println("Enter driver's location (latitude and longitude): ")
                val lat = scanner.nextDouble()
                val lon = scanner.nextDouble()
                system.addDriver(Driver(name, Pair(lat, lon)))
                println("Driver $name added.")
            }
            2 -> {
                // Add Rider
                println("Enter rider's name: ")
                val name = scanner.next()
                println("Enter rider's current location (latitude and longitude): ")
                val lat = scanner.nextDouble()
                val lon = scanner.nextDouble()
                println("Enter rider's destination location (latitude and longitude): ")
                val destLat = scanner.nextDouble()
                val destLon = scanner.nextDouble()
                system.addRider(Rider(name, Pair(lat, lon), Pair(destLat, destLon)))
                println("Rider $name added.")
            }
            3 -> {
                // Request Ride
                println("Enter rider's name to request a ride: ")
                val riderName = scanner.next()
                val rider = system.riders.find { it.name == riderName }

                if (rider != null) {
                    rider.requestRide(system)
                } else {
                    println("Rider not found.")
                }
            }
            4 -> {
                // Display Drivers
                system.displayDrivers()
            }
            5 -> {
                // Display Riders
                system.displayRiders()
            }
            6 -> {
                // Rate Driver
                println("Enter driver's name to rate: ")
                val driverName = scanner.next()
                val driver = system.drivers.find { it.name == driverName }

                if (driver != null) {
                    println("Enter a rating (1-5): ")
                    val rating = scanner.nextDouble()
                    system.rateDriver(driver, rating)
                } else {
                    println("Driver not found.")
                }
            }
            7 -> {
                println("Exiting the system.")
                break
            }
            else -> println("Invalid option. Please try again.")
        }
    }
}

