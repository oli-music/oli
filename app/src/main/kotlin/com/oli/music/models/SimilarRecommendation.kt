

package com.oli.music.models

import com.music.innertube.models.YTItem
import com.oli.music.db.entities.LocalItem

data class SimilarRecommendation(
    val title: LocalItem,
    val items: List<YTItem>,
)
