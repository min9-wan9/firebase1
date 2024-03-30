package com.example.bai23_firebase

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bai23_firebase.adapters.EmpAdater
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FetchingActivity : AppCompatActivity() {
    lateinit var ds : ArrayList<EmployeeModel>
    lateinit var dbRef :DatabaseReference



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        val txtLoadingData = findViewById<TextView>(R.id.txtLoadinData)
        val rvEmp=findViewById<RecyclerView>(R.id.rvEmp)



        rvEmp.layoutManager = LinearLayoutManager(this)
        rvEmp.setHasFixedSize(true)
        ds = arrayListOf<EmployeeModel>()

        getThongTinNV()
    }

    private fun getThongTinNV() {
        val txtLoadingData = findViewById<TextView>(R.id.txtLoadinData)
        val rvEmp=findViewById<RecyclerView>(R.id.rvEmp)

       rvEmp.visibility = View.GONE
        txtLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Employees")
        ds = arrayListOf<EmployeeModel>()
        // lăng nghe sư kiên
        dbRef.addValueEventListener( object:ValueEventListener{
            /**
             * This method will be called with a snapshot of the data at this location. It will also be called
             * each time that data changes.
             *
             * @param snapshot The current data at the location
             */
            override fun onDataChange(snapshot: DataSnapshot) {
                // xóa ds noww
                ds.clear()
                // kiểm tra snapshot có tồn tại
                if(snapshot.exists()){
                    for( snapEmp in snapshot.children){
                        val employee = snapEmp.getValue(EmployeeModel::class.java)
                        // găn các dữ liệu vào danh sách
                        ds.add(employee!!)
                    }
                }
                val empAapter = EmpAdater(ds)
                rvEmp.adapter = empAapter
                rvEmp.visibility = View.VISIBLE
                txtLoadingData.visibility = View.GONE
                // lắng nghe sự click trên item rv
                empAapter.setOnItemClickListener(object  :EmpAdater.onItemClickListener{
                    //ctrl i
                    override fun onItemClick(position: Int) {
                        val intent = Intent(this@FetchingActivity,EmployeeDetailsActivity::class.java)
                        // put extra
                        intent.putExtra("empId",ds[position].empId)
                        intent.putExtra("empName",ds[position].empName)
                        intent.putExtra("empAge",ds[position].empAge)
                        intent.putExtra("empSalary",ds[position].empSalary)
                        startActivity(intent)
                    }
                })

            }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
}