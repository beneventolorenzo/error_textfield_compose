package com.lbenevento.examples.errortextfields

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Error
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lbenevento.examples.errortextfields.ui.theme.ErrorTextFieldsTheme

@Composable
fun TextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    errorIcon: @Composable (() -> Unit)? = null,
    helperMessage: @Composable (() -> Unit)? = null,
    errorMessage: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape =
        MaterialTheme.shapes.small.copy(bottomEnd = ZeroCornerSize, bottomStart = ZeroCornerSize),
    colors: TextFieldColors = TextFieldDefaults.textFieldColors()
) {
    Column(
        modifier = modifier
    ) {
        androidx.compose.material.TextField(
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = textStyle,
            label = label,
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            trailingIcon = if (isError) errorIcon else trailingIcon,
            isError = isError,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            maxLines = maxLines,
            interactionSource = interactionSource,
            shape = shape,
            colors = colors
        )
        Box(
            modifier = Modifier
                .requiredHeight(16.dp)
                .padding(start = 16.dp, end = 12.dp)
        ) {
            CompositionLocalProvider(
                LocalTextStyle provides LocalTextStyle.current.copy(
                    fontSize = 12.sp,
                    color = if (isError) MaterialTheme.colors.error else LocalTextStyle.current.color
                )
            ) {
                if (isError) {
                    if (errorMessage != null) {
                        errorMessage()
                    }
                } else {
                    if (helperMessage != null) {
                        CompositionLocalProvider(
                            LocalContentAlpha provides ContentAlpha.medium
                        ) {
                            helperMessage()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    errorIcon: @Composable (() -> Unit)? = null,
    helperMessage: @Composable (() -> Unit)? = null,
    errorMessage: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape =
        MaterialTheme.shapes.small.copy(bottomEnd = ZeroCornerSize, bottomStart = ZeroCornerSize),
    colors: TextFieldColors = TextFieldDefaults.textFieldColors()
) {

    var textFieldValueState by remember { mutableStateOf(TextFieldValue(text = value)) }
    val textFieldValue = textFieldValueState.copy(text = value)

    TextField(
        value = textFieldValue,
        onValueChange = {
            textFieldValueState = it
            if (value != it.text) {
                onValueChange(it.text)
            }
        },
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        singleLine = singleLine,
        textStyle = textStyle,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        errorIcon = errorIcon,
        helperMessage = helperMessage,
        errorMessage = errorMessage,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        maxLines = maxLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors
    )
}

@Preview(name = "Day ꞏ Helper", showBackground = true)
@Composable
fun TextFieldDayHelperPreview() {
    ErrorTextFieldsTheme {
        Surface(modifier = Modifier.padding(15.dp)) {
            TextField(
                value = TextFieldValue(""),
                onValueChange = { },
                trailingIcon = {
                    Icon(
                        painter = rememberVectorPainter(image = Icons.Rounded.AccountCircle),
                        contentDescription = null
                    )
                },
                errorIcon = {
                    Icon(
                        painter = rememberVectorPainter(image = Icons.Rounded.Error),
                        contentDescription = null
                    )
                },
                helperMessage = {
                    Text(text = "This is an helper!")
                }
            )
        }
    }
}

@Preview(name = "Day ꞏ Error", showBackground = true)
@Composable
fun TextFieldNightHelperPreview() {
    ErrorTextFieldsTheme {
        Surface(modifier = Modifier.padding(15.dp)) {
            TextField(
                value = TextFieldValue(),
                onValueChange = { },
                errorMessage = {
                    Text(text = "This is an error!")
                },
                errorIcon = {
                    Icon(
                        painter = rememberVectorPainter(image = Icons.Rounded.Error),
                        contentDescription = null
                    )
                },
                isError = true
            )
        }
    }
}