package com.vladpetryshyn.vyao.ui.updateTask

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FormatAlignCenter
import androidx.compose.material.icons.filled.FormatAlignJustify
import androidx.compose.material.icons.filled.FormatAlignLeft
import androidx.compose.material.icons.filled.FormatAlignRight
import androidx.compose.material.icons.filled.FormatBold
import androidx.compose.material.icons.filled.FormatColorText
import androidx.compose.material.icons.filled.FormatItalic
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.FormatUnderlined
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorColors
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults
import com.vladpetryshyn.vyao.ChangeNavigationColor
import com.vladpetryshyn.vyao.R


@Composable
@ExperimentalMaterial3Api
fun UpdateTaskScreen(
    updateTaskViewModel: UpdateTaskViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    goToAddEvent: (taskId: String) -> Unit,
) {
    val formDescription = updateTaskViewModel.formDescription.collectAsState().value
    val formTitle = updateTaskViewModel.formTitle.collectAsState().value
    val taskId = updateTaskViewModel.taskId
    val richTextEditorState = rememberRichTextState()

    LaunchedEffect(updateTaskViewModel.formDescription) {
        if (updateTaskViewModel.formDescription.value != null)
            richTextEditorState.setMarkdown(updateTaskViewModel.formDescription.value)
    }

    val scrollStateVertical = rememberScrollState()
    val primaryColor = MaterialTheme.colorScheme.primary
    Scaffold(
        topBar = {
            UpdateTaskTopAppBar(
                saveTask = {
                    if (formTitle.isNotEmpty()) {
                        updateTaskViewModel.createTask(richTextEditorState.toMarkdown())
                    }
                    navigateBack()
                },
                navigateBack = navigateBack
            )
        },
        bottomBar = {
                BottomAppBar(
                    actions = {
                        var boldSelected by rememberSaveable { mutableStateOf(false) }
                        var italicSelected by rememberSaveable { mutableStateOf(false) }
                        var underlineSelected by rememberSaveable { mutableStateOf(false) }
                        var orderedList by rememberSaveable { mutableStateOf(false) }
                        var unorderedList by rememberSaveable { mutableStateOf(false) }


                        if (taskId != null) {
                            IconButton(onClick = { goToAddEvent(taskId) }) {
                                Icon(Icons.Default.DateRange, contentDescription = "")
                            }
                            ChangeNavigationColor(MaterialTheme.colorScheme.surfaceVariant)
                        }
                        IconButton(onClick = {
                            richTextEditorState.toggleUnorderedList()
                            unorderedList = !unorderedList
                        }) {
                            Icon(
                                Icons.Default.FormatListBulleted,
                                contentDescription = "",
                                tint = if (unorderedList) primaryColor else LocalContentColor.current
                            )
                        }
                        IconButton(
                            onClick = {
                                richTextEditorState.toggleOrderedList()
                                orderedList = !orderedList
                            }) {
                            Icon(
                                Icons.Default.FormatListNumbered,
                                contentDescription = "",
                                tint = if (orderedList) primaryColor else LocalContentColor.current
                            )
                        }
                        IconButton(onClick = {
                            richTextEditorState.toggleSpanStyle(SpanStyle(textDecoration = TextDecoration.Underline))
                            underlineSelected = !underlineSelected
                        }) {
                            Icon(
                                Icons.Default.FormatUnderlined,
                                contentDescription = "",
                                tint = if (underlineSelected) primaryColor else LocalContentColor.current
                            )
                        }
                        IconButton(onClick = {
                            richTextEditorState.toggleSpanStyle(SpanStyle(fontWeight = FontWeight.Bold))
                            boldSelected = !boldSelected
                        }) {
                            Icon(
                                Icons.Default.FormatBold,
                                contentDescription = "",
                                tint = if (boldSelected) primaryColor else LocalContentColor.current
                            )
                        }
                        IconButton(onClick = {
                            richTextEditorState.toggleSpanStyle(SpanStyle(fontStyle = FontStyle.Italic))
                            italicSelected = !italicSelected
                        }) {
                            Icon(
                                Icons.Default.FormatItalic,
                                contentDescription = "",
                                tint = if (italicSelected) primaryColor else LocalContentColor.current
                            )
                        }
                    }
                )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(scrollStateVertical),
            verticalArrangement = Arrangement.Top
        ) {
                    CustomTextField(
                        value = formTitle,
                        placeholderText = stringResource(id = R.string.update_task_text_title),
                        fontSize = 25,
                        onValueChange = updateTaskViewModel::updateTitle,
                        singleLine = false
                    )
                    RichTextEditor(
                        state = richTextEditorState,
                        singleLine = false,
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = RichTextEditorDefaults.richTextEditorColors(
                            disabledTextColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            containerColor = Color.Transparent
                        ),
                        placeholder = { Text(text = stringResource(id = R.string.update_task_text_description))},
                    )
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun UpdateTaskTopAppBar(
    saveTask: () -> Unit,
    navigateBack: () -> Unit
) {
    var isSaveButtonEnabled by remember {
        mutableStateOf(true)
    }

    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = navigateBack) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = null
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    isSaveButtonEnabled = false
                    saveTask()
                },
                enabled = isSaveButtonEnabled
            ) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = null,
                )
            }
        }
    )
}

@Composable
@ExperimentalMaterial3Api
fun CustomTextField(
    value: String,
    placeholderText: String,
    singleLine: Boolean = true,
    fontSize: Int = 20,
    modifier: Modifier = Modifier,
    onValueChange: (str: String) -> Unit,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = singleLine,
        modifier = modifier
            .fillMaxWidth(),
        textStyle = TextStyle(
            fontSize = fontSize.sp,
            color = MaterialTheme.colorScheme.onBackground
        ),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        decorationBox = {
            innerTextField ->
            Box(
                modifier = Modifier
                    .padding(13.dp)
                    .fillMaxWidth()
            ) {
                if (value.isEmpty())
                    Text(
                        placeholderText,
                        fontSize = fontSize.sp,
                        color = Color.LightGray
                    )
                innerTextField()
            }
        }
    )
}
//
//@Composable
//@ExperimentalMaterial3Api
//fun CustomRichTextEditor(
//    placeholderText: String,
//    singleLine: Boolean = true,
//    fontSize: Int = 20,
//    modifier: Modifier = Modifier,
//    state: RichTextState,
//    value: String,
//    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
//) {
//        BasicRichTextEditor(
//            state = state,
//            modifier = modifier
//                .defaultMinSize(
//                    minWidth = TextFieldDefaults.MinWidth,
//                    minHeight = TextFieldDefaults.MinHeight
//                ),
//            singleLine = singleLine,
//        decorationBox = {
//                innerTextField ->
//            val measurePolicy = remember(singleLine, animationProgress, paddingValues) {
//                TextFieldMeasurePolicy(singleLine, animationProgress, paddingValues)
//            }
//                Layout(content = { /*TODO*/ }, measurePolicy = )
//        }
//    )
//}

@Preview
@ExperimentalMaterial3Api
@Composable
fun CustomTextFieldPreview() {
    CustomTextField(value = "value", placeholderText ="placeholder", onValueChange = {} )
}
