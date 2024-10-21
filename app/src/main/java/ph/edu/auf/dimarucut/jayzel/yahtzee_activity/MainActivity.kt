package ph.edu.auf.dimarucut.jayzel.yahtzee_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ph.edu.auf.dimarucut.jayzel.yahtzee_activity.ui.theme.Blue
import ph.edu.auf.dimarucut.jayzel.yahtzee_activity.ui.theme.LightBlue
import ph.edu.auf.dimarucut.jayzel.yahtzee_activity.ui.theme.PokerInOctober
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
                .fillMaxSize()
                .background(LightBlue),

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = result,
                fontFamily = PokerInOctober,
                fontSize = 32.sp,
                color = Blue)

            Spacer(modifier = Modifier.height(24.dp))

            Column {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(25.dp))
                        .background(Blue)
                        .padding(8.dp)
                ) {
                    Column {
                        Row {
                            dice.take(3).forEach { die ->
                                Image(
                                    painter = painterResource(id = getDiceImage(die)),
                                    contentDescription = "Dice $die",
                                    modifier = Modifier.size(100.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row {
                            dice.drop(3).forEach { die ->
                                Image(
                                    painter = painterResource(id = getDiceImage(die)),
                                    contentDescription = "Dice $die",
                                    modifier = Modifier.size(100.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                dice = List(6) { Random.nextInt(1, 7) }
                result = checkCombinations(dice)
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = White,
                    contentColor = Blue
                )
            ) {
                Text("Roll Dice",
                    fontFamily = PokerInOctober,
                    fontSize = 24.sp)
            }
        }
    }
}

fun getDiceImage(die: Int): Int {
    return when (die) {
        1 -> R.drawable.dice1
        2 -> R.drawable.dice2
        3 -> R.drawable.dice3
        4 -> R.drawable.dice4
        5 -> R.drawable.dice5
        6 -> R.drawable.dice6
        else -> R.drawable.dice1
    }
}

fun checkCombinations(dice: List<Int>): String {
    val counts = dice.groupingBy { it }.eachCount()
    return when {
        counts.containsValue(6) -> "Six of a Kind"
        counts.containsValue(5) -> "Five of a Kind"
        counts.containsValue(4) -> "Four of a Kind"
        counts.containsValue(3) && counts.containsValue(2) -> "Full House"
        counts.containsValue(3) -> "Three of a Kind"
        counts.filterValues { it == 2 }.size == 2 -> "Two Pairs"
        counts.containsValue(2) -> "One Pair"
        dice.sorted() == listOf(1, 2, 3, 4, 5, 6) -> "Straight"
        else -> "No matches"
    }
}

@Preview(showBackground = true)
@Composable
fun YahtzeeGamePreview() {
    Yahtzee_ActivityTheme {
        YahtzeeGame()
    }
}