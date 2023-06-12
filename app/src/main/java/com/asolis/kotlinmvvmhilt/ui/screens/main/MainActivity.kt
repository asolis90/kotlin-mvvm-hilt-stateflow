package com.asolis.kotlinmvvmhilt.ui.screens.main

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asolis.kotlinmvvmhilt.data.models.Article
import com.asolis.kotlinmvvmhilt.ui.state.UIState
import com.asolis.kotlinmvvmhilt.ui.theme.KotlinmvvmhiltstateflowTheme
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinmvvmhiltstateflowTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel = hiltViewModel<MainActivityViewModel>()
                    val list: UIState<List<Article>> by viewModel.uiState.collectAsState()
                    ArticleList(items = list.data)
                }
            }
        }
    }
}

@Composable
fun ArticleList(
    items: List<Article>?,
    modifier: Modifier = Modifier
) {
    items?.let {
        LazyColumn(modifier = modifier) {
            items(it) { data ->
                ArticleItem(modifier = modifier, article = data) {
                }
            }
        }
    }
}

@Composable
private fun ArticleItem(
    modifier: Modifier = Modifier,
    article: Article,
    onClick: (String) -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(8.dp)
            .clickable(onClick = {
                onClick(article.url)
            })
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.Top)
                .padding(end = 16.dp)
        ) {
            ArticleImage(
                url = article.imageUrl
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = article.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = article.description,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 15.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun ArticleImage(
    modifier: Modifier = Modifier,
    url: String
) {
    val bitmapState = remember { mutableStateOf<Bitmap?>(null) }
    Glide.with(LocalContext.current)
        .asBitmap()
        .load(url)
        .apply(
            RequestOptions()
                .transform(CenterCrop())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
        )
        .into(
            object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    bitmapState.value = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            }
        )

    bitmapState.value?.let {
        Image(
            bitmap = it.asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleListPreview() {
    KotlinmvvmhiltstateflowTheme {
        ArticleList(items = listOf())
    }
}