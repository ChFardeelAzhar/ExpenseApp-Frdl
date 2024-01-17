package com.example.expenseapproom.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.expenseapproom.expenseData.dataModel.ExpenseDataModel
import com.example.expenseapproom.util.TypeOfData
import java.text.SimpleDateFormat
import java.util.Date


@Composable
fun ListItem(
    item: ExpenseDataModel,
    onUpdateClick: (id: Long) -> Unit,
    onDeleteClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(shape = RoundedCornerShape(7.dp))
            .clickable {
                onUpdateClick.invoke(item.id!!)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(8f),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.typeOfData,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(8f)
                )

                Image(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = null,
                    modifier = Modifier
                        .weight(2f)
                        .clickable {
                            onDeleteClick()
                        },

                    colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.error)
                )
            }

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = item.description,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Normal
            )

            val dateTimeFormat = SimpleDateFormat("EEE, DD-MM-YYYY, HH:MM")
            val dateTime = dateTimeFormat.format(Date(item.date))

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = dateTime.toString(),
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Medium
            )
        }

        val isExpense = item.typeOfData == TypeOfData.TypeOfData.Expense.value
        Text(
            text = if (isExpense) "${-1 * item.amount} Rs" else "${item.amount} Rs",
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f),
            fontWeight = FontWeight.Bold,
            color = if (isExpense) Color.Red else Color.Green

        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    ListItem(item = ExpenseDataModel(
        id = null,
        typeOfData = TypeOfData.TypeOfData.Expense.value,
        date = System.currentTimeMillis(),
        amount = 100.00,
        description = "I am Frdl Azhar and working as an android developer "
    ), onUpdateClick = { }) {

    }
}