package com.example.sampleappmovielist.utils

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.example.sampleappmovielist.model.Datum


/**
 * Created by Nitin  on 17/03/20.
 */
class MovieListDiffCallBack(oldMovieData: List<Datum>?, newMovieData: List<Datum>?) :
    DiffUtil.Callback() {
    private var mOldMovieData: List<Datum>? = oldMovieData
    private var mNewMovieData: List<Datum>? = newMovieData

    override fun getOldListSize(): Int {
        return mOldMovieData!!.size
    }

    override fun getNewListSize(): Int {
        return mNewMovieData!!.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldMovieData!![oldItemPosition].id == mNewMovieData!![newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldMovie: Datum = mOldMovieData!![oldItemPosition]
        val newMovie: Datum = mNewMovieData!![newItemPosition]
        return oldMovie.id!!.equals(newMovie.id)
    }

    @Nullable
    override fun getChangePayload(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Any? { // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}