package com.example.partyapppda.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CustomInput(
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    secureTextEntry: Boolean = false,
    modifier: Modifier = Modifier
) {
    val borderColor = Color(0xFFF213A4)
    val backgroundColor = Color(0xFF190A2E)
    val textColor = Color(0xFFF7A7FF)
    val placeholderColor = Color(0xFFD90F96)

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .height(66.dp)
            .fillMaxWidth()
            .background(backgroundColor, shape = RoundedCornerShape(25.dp))
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(25.dp)
            )
            .padding(horizontal = 20.dp),
        textStyle = TextStyle(
            color = textColor,
            textAlign = TextAlign.Center
        ),
        visualTransformation = if (secureTextEntry) PasswordVisualTransformation() else VisualTransformation.None,
        decorationBox = { innerTextField ->
            Box(
                contentAlignment = Alignment.Center
            ) {
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = placeholderColor,
                        textAlign = TextAlign.Center
                    )
                }
                innerTextField()
            }
        }
    )
}
