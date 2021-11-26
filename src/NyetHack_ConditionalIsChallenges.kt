fun main() {


    val name = "Madrigal"
    var healthPoints = 89
    val isBlessed = true
    val isImmortal = false
// Аура
    val auraColor = auraColor(isBlessed, healthPoints, isImmortal)


    printPlayerStatus(healthPoints, isBlessed, auraColor, name)
    castFireball()
}

private fun printPlayerStatus(
    healthPoints: Int,
    isBlessed: Boolean,
    auraColor: String,
    name: String
) {
    val healthStatus = formatHealthStatus(healthPoints, isBlessed)
    println(
        "(Aura: $auraColor) " +
                "(Blessed: ${if (isBlessed) "YES" else "NO"})"
    )
    println("$name $healthStatus")
}

private fun auraColor(isBlessed: Boolean, healthPoints: Int, isImmortal: Boolean) =
     if (isBlessed && healthPoints > 50 || isImmortal) "GREEN" else "NONE"


private fun formatHealthStatus(healthPoints: Int, isBlessed: Boolean) =
    when (healthPoints) {
        100 -> "is in excellent condition!"
        in 90..99 -> "has a few scratches."
        in 75..89 -> if (isBlessed) {
            "has some minor wounds but is healing quite quickly!"
        } else {
            "has some minor wounds."
        }
        in 15..74 -> "looks pretty hurt."
        else -> "is in awful condition!"
    }

private fun castFireball(numFireballs:Int = 2) {

    var intoxicatingLevel = numFireballs * 3
    if (intoxicatingLevel > 50) {
        intoxicatingLevel = 50
    }
    val intoxicatedState = when (intoxicatingLevel) {
        in 1..10 -> "Tipsy"
        in 11..20 -> "Sloshed"
        in 21..30 -> "Soused"
        in 31..40 -> "Stewed"
        else -> "..t0aSt3d"
    }
    println("A glass of Fireball springs into existence. (x$numFireballs)" +
            "Hero was poisoned, and now hi is $intoxicatedState ")
}


