package com.example.gym.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import kotlin.math.exp

@Composable
fun DropdownMenu(
    modifier: Modifier = Modifier,
    contentColor: Color,
    containerColor: Color,
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier.padding(16.dp)
    ) {
        IconButton(
            onClick = { expanded = !expanded },
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = contentColor,
                containerColor = containerColor
            ),
        ) {
            Icon(Icons.Default.MoreVert, contentDescription = "More options")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            shape = RoundedCornerShape(10.dp)
        ) {
            DropdownMenuItem(
                leadingIcon = {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                },
                text = { Text("Remover") },
                onClick = { /*TODO: Função para remover a planilha*/ }
            )
            DropdownMenuItem(
                leadingIcon = {
                    Icon(Icons.Default.Create, contentDescription = "Rename")
                },
                text = { Text("Renomear") },
                onClick = { /*TODO: Função para atualizar a planilha*/ }
            )
        }
    }

}