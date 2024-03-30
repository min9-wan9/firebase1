package com.example.bai23_firebase.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bai23_firebase.EmployeeModel
import com.example.bai23_firebase.R

class EmpAdater(val ds : ArrayList<EmployeeModel>) : RecyclerView.Adapter<EmpAdater.ViewHolder>() {
    //code adapter lắng nghe sự kiện
    private lateinit var mListener : onItemClickListener
    // tạo interface trước sau đó mới tạo private lateinter var mListent
    interface onItemClickListener{
        fun onItemClick(position : Int)
    }
    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }
    // tạo inner class viewHodle
    inner  class ViewHolder( itemView : View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }
    // ctr +i

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.emp_list_item,parent, false)
        return ViewHolder(view,mListener)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            val txtEmpNam = findViewById<TextView>(R.id.txtEmpName)
            txtEmpNam.text = ds[position].empName
        }
    }


    override fun getItemCount(): Int {
        return ds.size
    }
}

