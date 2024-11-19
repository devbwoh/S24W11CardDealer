package kr.ac.kumoh.ce.s20240000.s24w11carddealer

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kr.ac.kumoh.ce.s20240000.s24w11carddealer.ui.theme.S24W11CardDealerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            S24W11CardDealerTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val viewModel: CardViewModel = viewModel()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            Modifier.padding(innerPadding),
        ) {
            //CardImages()
            CardSection(viewModel)
            ShuffleButton {
                viewModel.shuffle()
            }
        }
    }
}

@Composable
@SuppressLint("DiscouragedApi")
fun ColumnScope.CardSection(viewModel: CardViewModel = viewModel()) {
    val cards by viewModel.cards.observeAsState(emptyList())
    val context = LocalContext.current

    val cardResources = IntArray(cards.size)

    cards.forEachIndexed { index, cardName ->
        cardResources[index] = context.resources.getIdentifier(
            cardName,
            "drawable",
            context.packageName
        )
    }

//    cardResources[0] = R.drawable.c_10_of_spades
//    cardResources[1] = R.drawable.c_jack_of_spades2
//    cardResources[2] = R.drawable.c_queen_of_spades2
//    cardResources[3] = R.drawable.c_king_of_spades2
//    cardResources[4] = R.drawable.c_ace_of_spades

    CardImages(cardResources)
}

@Composable
fun ColumnScope.CardImages(res: IntArray) {
    if (LocalConfiguration.current.orientation
        == Configuration.ORIENTATION_LANDSCAPE) {
        // weight(1f)은 onCreate()에 있는 Column에 적용됨
        // 버튼을 맨 밑에 위치시키기 위함
        Row(
            modifier = Modifier
                .weight(1f)
                .background(Color(0, 100, 0))
        ) {
            res.forEachIndexed { index, res ->
                Image(
                    painter = painterResource(res),
                    contentDescription = "card ${index + 1}",
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(4.dp)
                        .weight(1f)
                )
            }
        }
    }
    else {
        Column(
            modifier = Modifier
                .weight(1f)
                .background(Color(0, 100, 0))
        ) {
            CardRow(res, 0)
            CardRow(res, 1)
            Row(Modifier.weight(1f)) {
                Image(
                    painter = painterResource(res[4]),
                    contentDescription = "5th card",
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(4.dp)
                        .weight(1f)
                )
            }
        }
    }
}

@Composable
fun ColumnScope.CardRow(res: IntArray, row: Int) {
    // Row의 weight는 세로 화면에서 균등 분배
    Row(Modifier.weight(1f)) {
        Image(
            painter = painterResource(res[row * 2]),
            contentDescription = "card ${row * 2 + 1}",
            modifier = Modifier
                .fillMaxHeight()
                .padding(4.dp)
                .weight(1f)
        )
        Image(
            painter = painterResource(res[row * 2 + 1]),
            contentDescription = "card ${row * 2 + 1 + 1}",
            modifier = Modifier
                .fillMaxHeight()
                .padding(4.dp)
                .weight(1f)
        )
    }
}

@Composable
fun ShuffleButton(onDeal: () -> Unit) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = { onDeal() },
    ) {
        Text(stringResource(R.string.good_luck))
    }
}