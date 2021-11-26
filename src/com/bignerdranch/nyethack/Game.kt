package com.bignerdranch.nyethack

import java.lang.Exception
import kotlin.system.exitProcess

fun main() {


// Аура
//    val auraColor = player.auraColor() // переменная которая нахрен не всралась и нигде не используется,
    // но какого-то хрена в учебнике ее оставили
    // а теперь какого то хрена оно у них куда-то бесследно пропало
    Game.play()
// Аура
//    println(player.auraColor())


}


object Game {
    private val player = Player("Madrigal")
    private var currentRoom: Room = TownSquare()
    private var worldMap = listOf(
        listOf(currentRoom, Room("Tavern"), Room("Back Room")),
        listOf(Room("Long Corridor"), Room("Generic Room"))
    )

    init {
        println("Welcome, adventurer!")
        player.castFireball()
    }

    fun play() {
        while (true) {
            println(currentRoom.description())
            println(currentRoom.load())
            // состояние игрока
            printPlayerStatus(player)
            print("> Enter your command: ")
            println(GameInput(readLine()).processComand())


        }
    }


    private fun printPlayerStatus(player: Player) {
        println(
            "(Aura: ${player.auraColor()}) " +
                    "(Blessed: ${if (player.isBlessed) "YES" else "NO"})"
        )
        println("${player.name} ${player.formatHealthStatus()}")
    }

    private class GameInput(arg: String?) {
        private val input = arg ?: ""
        val command = input.split(" ")[0]
        val argument = input.split(" ").getOrElse(1, { "" })

        fun processComand() = when (command.toLowerCase()) {
            "fight" -> fight()
            "move" -> move(argument)
            "bell" -> if (currentRoom is TownSquare) player.ringBell() else println("there is no bell in his location")
            "hill" -> player.healthPoints+=25
//            "hp" -> println(player.healthPoints)
            else -> commandNotFound()
        }

        private fun commandNotFound() = "I'm not quite sure what you're trying to do!"
    }


    private fun move(directionInput: String) =
        try {
            val direction = Direction.valueOf(directionInput.toUpperCase())
            val newPosition = direction.updateCoordinate(player.currentPosition)
            if (!newPosition.isInBounds) {
                throw IllegalStateException("$direction is out of bounds.")
            }
            val newRoom = worldMap[newPosition.y][newPosition.x]
            player.currentPosition = newPosition
            currentRoom = newRoom
            "OK, you move $direction to the ${newRoom.name}.\n${newRoom.load()}"
        } catch (e: Exception) {
            "Invalid direction: $directionInput." // выдает ошибку направления движения
        }
    private fun fight() = currentRoom.monster?.let{
        while (player.healthPoints>0 && it.healthPoints>0){
            slay(it)
            Thread.sleep(1000)
        }
            "Combat complete."
    } ?: "There's nothing here to fight."

    private fun slay (monster: Monster){
        println("${monster.name} did ${monster.attack(player)} damage!")
        println("${player.name} did ${player.attack(monster)} damage!")

        if (player.healthPoints <=0){
            println(">>>> You have been defeated! Thanks for playing.")
            exitProcess(0)
        }
        if (monster.healthPoints<=0){
            println(">>>> ${monster.name} has been defeated! <<<<")
            currentRoom.monster = null
        }
    }

}