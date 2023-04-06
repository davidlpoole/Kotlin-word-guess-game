fun main() {
    // Set up:
    val alphabet = "ABCDEFGH" // the letters available to select from random for the secret word
    val wordLength = 4 // how many characters in the secret word

    // Run the game:
    playGame(generateSecret(wordLength, alphabet), wordLength, 3, alphabet)
}

fun playGame(
    secret: String,
    wordLength: Int,
    maxAttemptsCount: Int,
    alphabet: String
) {
    println(getGameRules(wordLength, maxAttemptsCount, alphabet))
    var complete: Boolean = false
    var tries = 0
    do {
//        println(secret)
        var guess: String = safeUserInput(wordLength, alphabet)
//        println(guess)
        if (isCorrectInput(guess, wordLength, alphabet)) {
            printRoundResults(secret, guess)
            tries += 1
            complete = isComplete(secret, guess)
        }
    } while (!complete && tries <= maxAttemptsCount)

    if (isWin(complete, tries, maxAttemptsCount)) {
        println("Congratulations! You guessed it!")
    }
    else if (isLost(complete, tries, maxAttemptsCount)) {
        println("Sorry, you lost! :( My word is $secret")
    }


}

fun isCorrectInput(userInput: String, wordLength: Int, alphabet: String): Boolean {

    var errors = 0
    for (symbol in userInput) {
        if (!alphabet.contains(symbol)) {
            errors += 1
        }
    }

    return if (userInput.length != wordLength) {
        println("The length of your guess should be $wordLength characters! Try again!")
        false
    } else if (errors > 0) {
        println("All symbols in your guess should be the <alphabet> alphabet characters! Try again!")
        false
    } else
        true
}

fun safeUserInput(wordLength: Int, alphabet: String): String {
    println("Please input your guess. It should be of length $wordLength, and each symbol should be from the alphabet: $alphabet.")
    return safeReadLine()
}

fun isWin(complete: Boolean, attempts: Int, maxAttemptsCount: Int): Boolean {
    return complete && attempts <= maxAttemptsCount
}

fun isLost(complete: Boolean, attempts: Int, maxAttemptsCount: Int): Boolean {
    return !complete && attempts > maxAttemptsCount
}

fun isComplete(secret: String, guess: String) = (secret == guess)

fun getGameRules(
    wordLength: Int,
    maxAttemptsCount: Int,
    alphabet: String
): String {
    return "Welcome to the game! $newLineSymbol" +
            newLineSymbol +
            "Two people play this game: one chooses a word (a sequence of letters), the other guesses it. In this version, the computer chooses the word: a sequence of $wordLength letters (for example, 'ACEB'). The user has several attempts to guess it (the max number is $maxAttemptsCount). For each attempt, the number of complete matches (letter and position) and partial matches (letter only) is reported. The possible symbols in the word: $alphabet.$newLineSymbol" +
            newLineSymbol +
            "For example, with 'ACEB' as the hidden word, the 'BCDF' guess will give 1 full match (C) and 1 partial match (B)."
}

fun generateSecret(wordLength: Int, alphabet: String): String {
    return List(wordLength) { alphabet.random() }.joinToString("")
}

const val ANSI_RESET = "\u001B[0m"
const val ANSI_BLACK = "\u001B[30m"
const val ANSI_RED = "\u001B[31m"
const val ANSI_GREEN = "\u001B[32m"
const val ANSI_YELLOW = "\u001B[33m"
const val ANSI_BLUE = "\u001B[34m"
const val ANSI_PURPLE = "\u001B[35m"
const val ANSI_CYAN = "\u001B[36m"
const val ANSI_WHITE = "\u001B[37m"

fun printRoundResults(
    secret: String,
    guess: String
) {
    var wordlResult = ANSI_RESET
    for (i in guess.indices) {
        if (secret[i] == guess[i]) {
            wordlResult += ANSI_GREEN
        } else if (secret.contains(guess[i])) {
            wordlResult += ANSI_YELLOW
        } else {
            wordlResult += ANSI_RED
        }
        wordlResult += guess[i]
    }
    wordlResult += ANSI_RESET
    println(wordlResult)
}