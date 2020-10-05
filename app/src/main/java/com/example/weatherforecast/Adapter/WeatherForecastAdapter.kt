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
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class WeatherForecastAdapter(var context: Context, var weatherList: List<Data>) : RecyclerView.Adapter<WeatherForecastAdapter.PostViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_layout, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item = weatherList.get(position)
        holder.txtPreciptVAlue.text = item.highTemp.toString()

        val format = SimpleDateFormat("yyyy-MM-dd")
        try {
            val date: Date = format.parse(item.datetime)
            holder.txtDataValue.text = DateFormat.getDateInstance(DateFormat.LONG).format(date).toString()
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        holder.txtMinTempValue.text = item.minTemp.toString()
        holder.txtMaxTempValue.text = item.maxTemp.toString()

        val df = DecimalFormat("0.0")
        holder.txtPreciptVAlue.text = df.format(item.precip * 10).toString().plus("%")
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
        var txtPreciptVAlue: TextView
        var txtDataValue: TextView
        var txtMaxTempValue: TextView
        var txtMinTempValue: TextView
        var img: ImageView

        init {
            txtPreciptVAlue = itemView.findViewById(R.id.txtPreciptValue)
            img = itemView.findViewById(R.id.img)
            txtDataValue = itemView.findViewById(R.id.txtDataValue)
            txtMinTempValue = itemView.findViewById(R.id.txtTempMinValue)
            txtMaxTempValue = itemView.findViewById(R.id.txtTempMaxValue)
        }
    }

}