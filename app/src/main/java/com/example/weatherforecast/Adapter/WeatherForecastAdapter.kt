package com.example.weatherforecast.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherforecast.Model.Data
import com.example.weatherforecast.R

class WeatherForecastAdapter(var context: Context, var weatherList: List<Data>) : RecyclerView.Adapter<WeatherForecastAdapter.PostViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.post_layout, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item = weatherList.get(position)
        holder.txtTitle.text = (item.highTemp.toString())
        Glide
                .with(context)
                .load("https://www.weatherbit.io/static/img/icons/" + item.weather.icon + ".png")
                .centerCrop()
                //.placeholder(R.drawable.loading_spinner)
                .into(holder.img)
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }


    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtTitle: TextView
        var img: ImageView

        init {
            txtTitle = itemView.findViewById(R.id.txtTitle)
            img = itemView.findViewById(R.id.img)
        }
    }

}