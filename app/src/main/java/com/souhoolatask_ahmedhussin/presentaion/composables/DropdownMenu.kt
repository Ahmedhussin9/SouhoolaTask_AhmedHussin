package com.souhoolatask_ahmedhussin.presentaion.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortByDropdownMenu(
    currentSort: String,
    onSortSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val sortOptions = listOf("publishedAt", "relevancy", "popularity")
    val sortLabels = mapOf(
        "publishedAt" to "Latest",
        "relevancy" to "Relevant",
        "popularity" to "Popular"
    )

    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .menuAnchor()
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(10.dp)
                ).padding(10.dp),
        ) {
            Text(
                text = sortLabels[currentSort] ?: currentSort,
                color = Color.Black
            )
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "Sort",
                tint = Color.Black,
            )

        }
        ExposedDropdownMenu(
            modifier = Modifier.width(200.dp),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            sortOptions.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = sortLabels[option] ?: option,
                            color = Color.Black
                        )
                    },
                    onClick = {
                        onSortSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun SortByDropdownMenuPreview() {
    SortByDropdownMenu(currentSort = "relevancy", onSortSelected = {})
}

