package ph.edu.auf.dimarucut.jayzel.yahtzee_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ph.edu.auf.dimarucut.jayzel.yahtzee_activity.ui.theme.Yahtzee_ActivityTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Yahtzee_ActivityTheme {
                YahtzeeGame()
            }
        }
    }
}

@Composable
fun YahtzeeGame() {
    var dice by remember { mutableStateOf(List(6) { Random.nextInt(1, 7) }) }
    var result by remember { mutableStateOf("") }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                dice.forEach { die ->
                    Image(
                        painter = painterResource(id = getDiceImage(die)),
                        contentDescription = "Dice $die",
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                dice = List(6) { Random.nextInt(1, 7) }
                result = checkCombinations(dice)
            }) {
                Text("Roll Dice")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Result: $result")
        }
    }
}

fun getDiceImage(die: Int): Int {
    return when (die) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        6 -> R.drawable.dice_6
        else -> R.drawable.dice_1
    }
}

fun checkCombinations(dice: List<Int>): String {
    val counts = dice.groupingBy { it }.eachCount()
    return when {
        counts.containsValue(6) -> "Yahtzee"
        counts.containsValue(5) -> "Five of a Kind"
        counts.containsValue(4) -> "Four of a Kind"
        counts.containsValue(3) && counts.containsValue(2) -> "Full House"
        counts.containsValue(3) -> "Three of a Kind"
        counts.filterValues { it == 2 }.size == 2 -> "Two Pair"
        counts.containsValue(2) -> "One Pair"
        dice.sorted() == listOf(1, 2, 3, 4, 5, 6) -> "Straight"
        else -> "Chance"
    }
}

@Preview(showBackground = true)
@Composable
fun YahtzeeGamePreview() {
    Yahtzee_ActivityTheme {
        YahtzeeGame()
    }
}