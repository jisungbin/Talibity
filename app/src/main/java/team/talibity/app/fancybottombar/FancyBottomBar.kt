/*
* FancyBottomBar created by Ji Sungbin
* FancyBottomBar license is under the MIT license.
* Please see: https://github.com/jisungbin/FancyBottomBar/blob/master/LICENSE
*/

package team.talibity.app.fancybottombar

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import team.talibity.app.ui.theme.PrimaryDark

@Composable
fun FancyBottomBar(
    modifier: Modifier = Modifier,
    fancyColors: FancyColors = FancyColors(),
    fancyOptions: FancyOptions = FancyOptions(),
    items: List<FancyItem>,
    selectedState: Int,
    onItemChanged: (FancyItem) -> Unit,
) {
    var fancyItemState by remember { mutableStateOf(items.first().id) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(fancyOptions.barHeight)
            .background(color = fancyColors.background),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            val fancyItemColor by animateColorAsState(if (fancyItemState == item.id) Color.White else Color.LightGray)

            Box(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f, matchHeightConstraintsFirst = true)
                    .clip(CircleShape)
                    .run {
                        if (fancyItemState == item.id) {
                            background(
                                color = animateColorAsState(
                                    targetValue = when (selectedState == fancyItemState) {
                                        true -> PrimaryDark
                                        else -> Color.White
                                    }
                                ).value
                            )
                        } else this
                    }
                    .clickable {
                        fancyItemState = item.id
                        onItemChanged(item)
                    }
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (item.icon != null) {
                        Icon(
                            painter = painterResource(item.icon),
                            contentDescription = null,
                            tint = fancyItemColor
                        )
                    }
                    if (item.title.isNotBlank()) {
                        Text(
                            text = item.title,
                            color = fancyItemColor,
                            modifier = Modifier.padding(top = fancyOptions.titleTopPadding),
                            fontFamily = fancyOptions.fontFamily
                        )
                    }
                }
            }
        }
    }
}
