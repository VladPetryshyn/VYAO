package com.vladpetryshyn.vyao

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.text.Spanned
import android.text.SpannedString
import android.util.Log
import android.util.TypedValue
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.core.text.HtmlCompat
import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.parser.MarkdownParser

@Composable
fun BackPressHandler(
    backPressedDispatcher: OnBackPressedDispatcher? =
        LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher,
    onBackPressed: () -> Unit
) {
    val currentOnBackPressed by rememberUpdatedState(newValue = onBackPressed)

    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                currentOnBackPressed()
            }
        }
    }

    DisposableEffect(key1 = backPressedDispatcher) {
        backPressedDispatcher?.addCallback(backCallback)

        onDispose {
            backCallback.remove()
        }
    }
}

fun spToPx(sp: Float, context: Context): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.resources.displayMetrics).toInt()
}
fun pxToDp(px: Int, context: Context): Int {
    return (px / context.resources.displayMetrics.density).toInt()
}

@Composable
fun ChangeNavigationColor(color: Color) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.navigationBarColor = color.toArgb()
        }
    }
}

private const val GET_VERSION_NAME_TAG = "GET_VERSION_NAME"
fun getVersionName(context: Context): String {
    return try {
        context.packageManager
            .getPackageInfo(context.packageName, 0).versionName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        Log.d(GET_VERSION_NAME_TAG, e.message.toString())
        "Error"
    }
}

fun convertMarkdownToHTML(markdownString: String): String {
    val flavour = CommonMarkFlavourDescriptor()
    val parsedTree = MarkdownParser(flavour).buildMarkdownTreeFromString(markdownString)
    return HtmlGenerator(markdownString, parsedTree, flavour).generateHtml()
}
fun convertMarkdownToSpannedText(markdownString: String): Spanned {
    val html = convertMarkdownToHTML(markdownString)
    return HtmlCompat.fromHtml(html, 0)
}

/**
 * Color strings should be formatted as hex color values (#000000)
 *
 **/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorPickerDialog(
    initialColor: String,
    colors: List<String>,
    onChoice: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    var color by remember(initialColor) { mutableStateOf(initialColor) }
    var hexTextColor by remember(initialColor) {
        mutableStateOf(initialColor.toColor(Color.White).contrastColor())
    }

    val colorRendered by animateColorAsState(
        targetValue = color.toColor(Color.White),
        label = "color-picker-animation",
        finishedListener = {
            hexTextColor = it.contrastColor()
        }
    )

    val onDismissRequest = {
        if (Patterns.color.matches(color)) {
            onChoice(color)
        }
        onDismiss()
    }

    AlertDialog(
        onDismissRequest = onDismissRequest
    ) {
        Surface(
            shape = AlertDialogDefaults.shape,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                BasicTextField(
                    value = color,
                    onValueChange = {
                        color = it
                    },
                    textStyle = MaterialTheme.typography.headlineSmall
                        .copy(
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                            color = hexTextColor
                        ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(colorRendered)
                        .wrapContentHeight(align = Alignment.CenterVertically)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Column(
                    modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 24.dp)
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 50.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        items(colors) {
                            Button(
                                onClick = { color = it },
                                shape = CircleShape,
                                modifier = Modifier.requiredSize(50.dp),
                                contentPadding = PaddingValues(1.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(it.toColorInt())
                                ),
                                border = if (it == color)
                                    BorderStroke(2.dp, MaterialTheme.colorScheme.onSurface)
                                else
                                    null,
                                content = {}
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    TextButton(onClick = onDismissRequest, modifier = Modifier.align(Alignment.End)) {
                        Text(text = "Confirm", style = MaterialTheme.typography.labelLarge)
                    }
                }
            }
        }
    }
}

fun String.toColorOrNull(): Color? = try {
    Color(toColorInt())
} catch (_: IllegalArgumentException) {
    null
}

fun String.toColor(fallback: Color) = toColorOrNull() ?: fallback

fun Color.contrastColor(): Color {
    // Calculate the perceptive luminance (aka luma) - human eye favors green color...
    val luma = (0.299 * red) + (0.587 * green) + (0.114 * blue)

    // Return black for bright colors, white for dark colors
    return if (luma > 0.6) {
        Color.Black
    } else {
        Color.White
    }
}

object Patterns {
    val color = "#[0-9a-fA-F]{6}".toRegex()
}