package com.github.heesung6701.quokkaui.catalog

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.heesung6701.quokkaui.catalog.anchor.window.DialogAnchorActivity
import com.github.heesung6701.quokkaui.catalog.picker.ComponentPickerActivity
import com.github.heesung6701.quokkaui.catalog.picker.ComponentPickerComposableTestActivity
import com.github.heesung6701.quokkaui.catalog.picker.ComponentPickerCustomTestActivity
import com.github.heesung6701.quokkaui.catalog.touchdelegate.TouchDelegateActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val catalogItems = listOf(
            CatalogItemData(
                getString(R.string.title_activity_touch_delegate),
                TouchDelegateActivity::class.java
            ),
            CatalogItemData(
                getString(R.string.title_activity_dialog_anchor),
                DialogAnchorActivity::class.java
            ),
            CatalogItemData(
                getString(R.string.title_activity_picker_component),
                ComponentPickerActivity::class.java
            ),
            CatalogItemData(
                getString(R.string.title_activity_picker_component_composable),
                ComponentPickerComposableTestActivity::class.java
            ),
            CatalogItemData(
                getString(R.string.title_activity_picker_component_custom),
                ComponentPickerCustomTestActivity::class.java
            ),
        )
        setContent {
            MaterialTheme {
                Scaffold { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) {
                        CatalogItemList(
                            titles = catalogItems.map { it.title },
                            onItemClick = {
                                startActivity(Intent(this@MainActivity, catalogItems[it].clazz))
                            })
                    }
                }
            }
        }
    }

    data class CatalogItemData(val title: String, val clazz: Class<out Activity>)
}

@Composable
fun CatalogItemList(titles: List<String>, onItemClick: (Int) -> Unit) {
    LazyColumn {
        items(titles.size, key = {
            titles[it]
        }) {
            CatalogItem(onClick = {
                onItemClick(it)
            }, title = titles[it])
        }
    }
}

@Preview
@Composable
fun CatalogItemListPreview() {
    MaterialTheme {
        CatalogItemList(
            titles = listOf("test1", "test2"),
            onItemClick = {
            })
    }
}

@Composable
fun CatalogItem(onClick: () -> Unit, title: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp),
        color = MaterialTheme.colorScheme.background,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier
                    .weight(1F),
                text = title,
                textAlign = TextAlign.Center
            )
            Button(onClick = onClick) {
                Text("go")
            }
        }

    }
}

@Preview
@Composable
fun ListItemPreview() {
    MaterialTheme {
        CatalogItem(
            title = "text",
            onClick = {
            })
    }
}