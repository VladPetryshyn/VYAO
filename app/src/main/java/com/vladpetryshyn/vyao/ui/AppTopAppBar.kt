package com.vladpetryshyn.vyao.ui

import android.content.Intent
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.vladpetryshyn.vyao.R
import com.vladpetryshyn.vyao.getVersionName

@Composable
@ExperimentalMaterial3Api
fun VyaoAppTopAppBar(
    title: String,
    viewModel: AppTopBarViewModel = hiltViewModel(),
    goToSettings: () -> Unit
) {
    val userAvatar = viewModel.userAvatar.collectAsState().value
    val context = LocalContext.current


    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { it ->
        val flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        it.data?.data?.let {
            // context.contentResolver.releasePersistableUriPermission(it, flags)
            viewModel.updateUserAvatar(it)
            context.contentResolver.takePersistableUriPermission(it, flags)
        }
    }

    Row(
        modifier = Modifier.padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .clickable {
                    val intent = Intent(
                        Intent.ACTION_OPEN_DOCUMENT,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                        .apply {
                            addCategory(Intent.CATEGORY_OPENABLE)
                            type = "image/*"
                            addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
                        }
                    launcher.launch(intent)
                },
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(userAvatar)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(R.string.Avatar),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.account_circle_24px),
            error = painterResource(id = R.drawable.account_circle_24px)
        )
        Text(
            text = title,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
        )
        IconButton(onClick = goToSettings) {
            Icon(imageVector = Icons.Default.Settings, contentDescription = "")
        }
    }
}
