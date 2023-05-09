package com.progress.management.base.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.progress.management.R
import com.progress.management.entity.response.ProgressResponse
import com.progress.management.extension.formatDateString
import com.progress.management.extension.formatString
import com.progress.management.extension.gone
import com.progress.management.extension.inflate
import com.progress.management.extension.invisible
import com.progress.management.extension.visible
import kotlinx.android.synthetic.main.item_progress.view.cb_more
import kotlinx.android.synthetic.main.item_progress.view.rcv_progress
import kotlinx.android.synthetic.main.item_progress.view.tv_date_end
import kotlinx.android.synthetic.main.item_progress.view.tv_date_start
import kotlinx.android.synthetic.main.item_progress.view.tv_id
import kotlinx.android.synthetic.main.item_progress.view.tv_note
import kotlinx.android.synthetic.main.item_progress.view.tv_product
import kotlinx.android.synthetic.main.item_progress.view.tv_progress
import kotlinx.android.synthetic.main.item_progress.view.tv_time_line
import kotlinx.android.synthetic.main.progress_fragment.rcv_progress
import javax.inject.Inject

class ProgressAdapter @Inject constructor(context: Context) :
    EndlessLoadingRecyclerViewAdapter(context, false) {
    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder? {
        return ContactConsultantViewHolder(parent.inflate(R.layout.item_progress))
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        val item = getItem(position, ProgressResponse::class.java)
        item?.let {
            holder.itemView.apply {
                cb_more.isChecked = it.isCheck
                tv_product.text = it.NAME.formatString()
                tv_id.text = it.ID_MAY.formatString()
                tv_time_line.text = it.TNGAY.formatString()
                tv_date_start.text = it.START_DATE.formatDateString()
                tv_date_end.text = it.END_DATE.formatDateString()
                tv_progress.text = it.TIENDO.formatString()
                tv_note.text = it.GHICHU.formatString()
                if (it.listDetailProgressRespons.size > 0){
                    rcv_progress.visible()
                    val adapter = ProgressAdapter(context)
                    val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    rcv_progress.layoutManager = layoutManager
                    rcv_progress.adapter = adapter
                    adapter.refresh(it.listDetailProgressRespons)
                }else{
                    rcv_progress.gone()
                    cb_more.invisible()
                }
                cb_more.setOnCheckedChangeListener { _, isChecked ->
                    it.isCheck = isChecked
                    if (isChecked) {
                        rcv_progress.visible()
                    } else {
                        rcv_progress.gone()
                    }
                }
            }
        }
    }

    inner class ContactConsultantViewHolder(view: View) : NormalViewHolder(view)
}