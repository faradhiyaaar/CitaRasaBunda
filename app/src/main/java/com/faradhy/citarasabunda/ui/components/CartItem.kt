package com.faradhy.citarasabunda.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.faradhy.citarasabunda.ui.theme.Shapes
import com.faradhy.citarasabunda.R
import com.faradhy.citarasabunda.ui.theme.CitaRasaBundaTheme

@Composable
fun CartItem(
    FoodId: Long,
    image: Int,
    title: String,
    totalPrice: Int,
    count: Int,
    onProductCountChanged:(id:Long,count: Int)->Unit,
    modifier: Modifier = Modifier
){
    Row (
        modifier=modifier.fillMaxWidth()
    ){
        Image(
            painter = painterResource(image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(Shapes.small)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(1.0f)
        ) {
            Text(
                text = title,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Text(
                text = stringResource(
                    R.string.required_price,
                    totalPrice
                ),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall,
            )
        }
        ProductCounter(
            orderId = FoodId,
            orderCount = count,
            onProductIncreased = { onProductCountChanged(FoodId, count + 1) },
            onProductDecreased = { onProductCountChanged(FoodId, count - 1) },
            modifier = Modifier.padding(8.dp)
        )

    }

}

@Composable
@Preview(showBackground = true)
fun CartItemPreview() {
    CitaRasaBundaTheme {
        CartItem(
            4, R.drawable.bubur_manado, "Bubur Manado", 6000, 0,
            onProductCountChanged = { rewardId, count -> },
        )
    }
}