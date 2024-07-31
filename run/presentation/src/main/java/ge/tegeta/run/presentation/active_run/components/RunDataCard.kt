package ge.tegeta.run.presentation.active_run.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ge.tegeta.core.presentation.designsystem.TrackerAppTheme
import ge.tegeta.core.presentation.ui.formatted
import ge.tegeta.core.presentation.ui.toFormattedKm
import ge.tegeta.core.presentation.ui.toFormattedPace
import ge.tegeta.run.domain.RunData
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

@Composable
fun RunDataCard(
    modifier: Modifier = Modifier,
    elapsedTime: Duration,
    runData: RunData,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RunDataItem(title = "Duration", value = elapsedTime.formatted(), fontSize = 32.sp)
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            RunDataItem(
                title = "Km",
                modifier = Modifier.defaultMinSize(minWidth = 75.dp),
                value = (runData.distanceMeters / 1000.0).toFormattedKm()
            )
            RunDataItem(
                title = "Pace   ",
                modifier = Modifier.defaultMinSize(minWidth = 75.dp),
                value = elapsedTime.toFormattedPace(runData.distanceMeters / 1000.0)
            )


        }

    }


}

@Composable
private fun RunDataItem(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 16.sp
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text = value, fontSize = fontSize, color = MaterialTheme.colorScheme.onSurface)

    }

}

@Preview
@Composable
private fun RunDataItemPrev() {
    TrackerAppTheme {
        RunDataCard(
            elapsedTime = 10.minutes,
            runData = RunData(
                distanceMeters = 1000,
                pace = 5.minutes
            ),
        )
    }

}