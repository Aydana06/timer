package com.example.daysofwellness

import DaysOfWellnessTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.daysofwellness.data.DataSource
import com.example.daysofwellness.model.Motivation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DaysOfWellnessTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MotivationApp(
                        modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MotivationTopAppBar(modifier: Modifier = Modifier){
    CenterAlignedTopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically){
                Image(
                    modifier = Modifier
                        .size(64.dp)
                        .padding(8.dp),
                    painter = painterResource(R.drawable.ic_woof_logo),
                    contentDescription = null
                )

                Text(
                    text = stringResource(R.string.title),
                    style =MaterialTheme.typography.displayLarge
                )
            }
        },
        modifier = modifier
    )
}
@Composable
fun MotivationApp(modifier: Modifier = Modifier){
    Scaffold(
        topBar = {
            MotivationTopAppBar()
        }
    ){ paddingValues ->
        LazyColumn(modifier = modifier.padding(paddingValues)){
            items(DataSource.motivations) { motivation ->
                MotivationCard(motivation)
            }
        }
    }
}

@Composable
fun MotivationCard(motivation: Motivation, modifier: Modifier = Modifier){
    var expanded by remember{ mutableStateOf(false)}
    val color by animateColorAsState(
        targetValue = if (expanded) MaterialTheme.colorScheme.tertiaryContainer
        else MaterialTheme.colorScheme.primaryContainer,
    )

    Card(modifier = modifier
        .fillMaxSize()
        .padding(start = 16.dp, end = 16.dp, top = 8.dp)
        .clickable {
            expanded = !expanded
        }
        .animateContentSize(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioNoBouncy,
                stiffness = Spring.StiffnessMedium
            )
        )
    ){
        Column(modifier = modifier
            .background(color)
            .fillMaxSize()
            .padding(16.dp)
            ){
            Text(
                text = stringResource(motivation.dayRes),
                style = MaterialTheme.typography.displayLarge
            )
            Row(modifier = modifier){
                Text(
                    text = stringResource(R.string.author),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = modifier.padding(2.dp))
                Text(
                    text = stringResource(motivation.author),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Row(modifier = modifier){
                Box(
                    modifier = modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    Image(
                        painter = painterResource(motivation.imageRes),
                        contentDescription = null,
                        modifier = modifier
                            .aspectRatio(1f)
                            .padding(8.dp)
                            .clip(MaterialTheme.shapes.small),
                        contentScale = ContentScale.FillWidth,
                    )
                }
                if(expanded){
                    Text(
                        text = stringResource(motivation.motivationRes),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MotivationPreview() {
    DaysOfWellnessTheme (darkTheme = true){
        MotivationApp()
    }
}