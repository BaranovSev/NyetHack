package com.bignerdranch.nyethack
import extensions.randooom as randomizer
import java.io.File
import java.lang.NumberFormatException


class Player(_name: String,
             override var healthPoints: Int = 100,
             val isBlessed: Boolean,
//             val isCursed:Boolean,       //----------------------------добавляем проклятие
             private val isImmortal: Boolean): Fightable{
    var name = _name
        get() = "${field.capitalize()} of $hometown"
    private set(value) {
        field = value.trim()
    }
    val hometown by lazy {selectHometown()}
    var currentPosition = Coordinate(0,0)
    init {
        require(healthPoints>0, { "healthPoints must be greater than zero." })
        require(name.isNotBlank(),{"Player must have a name."})
    }
    constructor(name: String) : this(name,
        isBlessed = true,
//        isCursed = false, //------------------------------------------добавляем проклятие
        isImmortal = false) {if (name.toLowerCase()=="kar") healthPoints=40}


//    var healthPoints = 89                 |
//    val isBlessed = true                  | Вспомогательный конструктор
//    private val isImmortal = false        |

    fun auraColor(): String {
        val auraVisible = isBlessed && healthPoints > 50 || isImmortal
        val auraColor = if (auraVisible) "GREEN" else "NONE"
        return auraColor
    }

    fun formatHealthStatus() =
        when (healthPoints) {
            in 101..200 -> "fells him self much better then you"
            100 -> "is in excellent condition!"
            in 90..99 -> "has a few scratches."
            in 75..89 -> if (isBlessed) {
                "has some minor wounds but is healing quite quickly!"
            } else {
                "has some minor wounds."
            }
            in 15..74 -> "looks pretty hurt."
            in 1..14 -> "is in awful condition!"
            in -20..0 -> "is unholly walking dead now\n> your attack is increased by 3 times <"
            else -> "almost dead"
        }

    fun castFireball(numFireballs: Int = 2) =
        println("A glass of Fireball springs into existence. (x$numFireballs)")

    private fun selectHometown() = File("data/towns.txt")
        .readText()
        .split(System.lineSeparator())
        .randomizer()

    override val diceCount: Int = 3
    override val diceSides: Int = 6

    override fun attack(opponent: Fightable): Int {
        val damageDealt = if (isBlessed){
            damageRoll*2
//        }else if (isCursed){damageRoll*3 //---------------------------добавляем проклятие урон х 3

        }else{
            damageRoll
        }
        opponent.healthPoints -= damageDealt
        return damageDealt
    }


    fun ringBell() {
        println("enter how much<")
        var numRing: Int
        try { numRing = (readLine() ?: "0").toInt() // может быть введен текст, который нельзя привести к числу
            if(numRing == 0) println("Player don't knock")
            else println("Player knocking the Ball $numRing times: ")

            for (n in 1..numRing) print ("GWANG ! ")
        } catch (e: NumberFormatException) {
            println("the >number< was expected")}
    }

}