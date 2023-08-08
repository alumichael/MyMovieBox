package com.homework.mymoviebox.util

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.homework.mymoviebox.data.states.ItemType
import com.homework.mymoviebox.domain.model.DomainModel
import com.homework.mymoviebox.presentation.detail.DetailActivity
import com.homework.mymoviebox.presentation.detail.DetailViewModel.Companion.EXTRA_ID
import com.homework.mymoviebox.presentation.detail.DetailViewModel.Companion.EXTRA_TYPE


fun Fragment.intentToDetailsActivity(item: DomainModel) =
    Intent(requireActivity(), DetailActivity::class.java).run {
        putExtra(EXTRA_ID, item.id)
        val type = ItemType.Movie
        putExtra(EXTRA_TYPE, type)
        startActivity(this)
    }


