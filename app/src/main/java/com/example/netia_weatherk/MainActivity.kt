package com.example.netia_weatherk

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.netia_weatherk.databinding.ActivityMainBinding
import com.example.netia_weatherk.databinding.RowBinding
import com.example.netia_weatherk.models.MainViewState
import com.example.netia_weatherk.models.RowState
import com.example.netia_weatherk.viewmodel.MainMotor

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val motor = ViewModelProvider(this)[MainMotor::class.java]
        val adapter = WeatherAdapter()

        binding.forecasts.layoutManager = LinearLayoutManager(this)
        binding.forecasts.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.forecasts.adapter = adapter

        motor.results.observe(this) { state ->
            when (state) {
                MainViewState.Loading -> binding.progress.visibility = View.VISIBLE
                is MainViewState.Content -> {
                    binding.progress.visibility = View.GONE
                    adapter.submitList(state.forecasts)
                }
                is MainViewState.Error -> {
                    binding.progress.visibility = View.GONE
                    Toast.makeText(
                        this@MainActivity, state.throwable.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()
                    Log.e("Weather", "Exception loading data", state.throwable)
                }
            }
        }

        motor.load("OKX", 32, 34)    }



    inner class WeatherAdapter :
        ListAdapter<RowState, RowHolder>(RowStateDiffer) {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): RowHolder {
            return RowHolder(
                RowBinding.inflate(layoutInflater, parent, false)
            )
        }

        override fun onBindViewHolder(holder: RowHolder, position: Int) {
            holder.bind(getItem(position))
        }
    }

    class RowHolder(private val binding: RowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(state: RowState) {
            binding.state = state
            binding.executePendingBindings()
        }
    }

    object RowStateDiffer : DiffUtil.ItemCallback<RowState>() {
        override fun areItemsTheSame(
            oldItem: RowState,
            newItem: RowState
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: RowState,
            newItem: RowState
        ): Boolean {
            return oldItem == newItem
        }
    }
}
