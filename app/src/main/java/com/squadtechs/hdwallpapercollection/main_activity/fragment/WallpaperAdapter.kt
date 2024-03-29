package com.squadtechs.hdwallpapercollection.main_activity.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley
import com.makeramen.roundedimageview.RoundedImageView
import com.squadtechs.hdwallpapercollection.R
import com.squadtechs.hdwallpapercollection.activity_view_wallpapers.ActivityViewWallpaper
import com.squareup.picasso.Picasso


class WallpaperAdapter(
    val context: Context,
    val activity: Activity,
    val list: ArrayList<WallpaperModel>,
    val isFromFav: Boolean,
    val isFromWeekly: Boolean
) :
    RecyclerView.Adapter<WallpaperAdapter.WallpaperHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperHolder =
        WallpaperHolder(LayoutInflater.from(context).inflate(R.layout.grid_cell, parent, false))

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int = position

    override fun onBindViewHolder(holder: WallpaperHolder, position: Int) {
        adjustScreen(holder, position)
        populateViews(holder, position)
        setListener(holder, position)
    }

    private fun setListener(holder: WallpaperHolder, position: Int) {
        holder.touchView.setOnClickListener {
            context.startActivity(
                Intent(context, ActivityViewWallpaper::class.java)
                    .putExtra("category_ref", list[position].category_ref)
                    .putExtra("position", position)
                    .putExtra("is_from_fav", isFromFav)
                    .putExtra("is_from_weekly", isFromWeekly)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
            if (isFromFav) {
                (context as Activity).finish()
            }
        }
    }


    private fun populateViews(holder: WallpaperHolder, position: Int) {
//        val requestQueue = Volley.newRequestQueue(context)
//        val imageRequest = ImageRequest(
//            list[position].wallpaper_image_url,
//            Response.Listener { response ->
//                holder.imgGrid.scaleType = ImageView.ScaleType.CENTER
//                holder.imgGrid.setImageBitmap(response)
//                holder.progress.visibility = View.GONE
//            },
//            1024,
//            860,
//            ImageView.ScaleType.CENTER,
//            null,
//            Response.ErrorListener { error ->
//                Toast.makeText(context, "Error loading Image", Toast.LENGTH_LONG).show()
//                holder.progress.visibility = View.GONE
//            }).setRetryPolicy(
//            DefaultRetryPolicy(
//                20000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
//            )
//        )
//        requestQueue.add(imageRequest)
        Picasso.get().load(list[position].wallpaper_image_url).resize(1024,860).into(holder.imgGrid)
        holder.txtCategory.visibility = View.GONE
    }

    private fun adjustScreen(holder: WallpaperHolder, position: Int) {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels
        holder.frame.layoutParams = FrameLayout.LayoutParams((width / 2), ((35 * height) / 100))
        if (position % 2 == 0) {
            holder.frame.setPadding(32, 16, 16, 16)
        } else {
            holder.frame.setPadding(16, 16, 32, 16)
        }
    }

    inner class WallpaperHolder(view: View) : RecyclerView.ViewHolder(view) {
        val progress: ProgressBar = view.findViewById(R.id.progress)
        val frame: FrameLayout = view.findViewById(R.id.grid_frame)
        val imgGrid: RoundedImageView = view.findViewById(R.id.img_grid)
        val txtCategory: TextView = view.findViewById(R.id.txt_grid_cell)
        val touchView: View = view.findViewById(R.id.grid_touch_view)
    }
}