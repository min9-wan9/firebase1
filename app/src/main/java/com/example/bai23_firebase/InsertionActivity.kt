package com.example.bai23_firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.lang.System.err

class   InsertionActivity : AppCompatActivity() {

    lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        val btnSave = findViewById<Button>(R.id.btnSave)

        //tạo bảng Employees trên firebase
        dbRef = FirebaseDatabase.getInstance().getReference("Employees")
        btnSave.setOnClickListener {
            saveEmployeeData()
        }

    }

    private fun saveEmployeeData() {
        //getting value
        val empName = findViewById<EditText>(R.id.edtEmpName)
        val empAge = findViewById<EditText>(R.id.edtEmpAge)
        val empSalary = findViewById<EditText>(R.id.edtEmpSalary)
        // kiểm tra xong các dòng có trống
        if(empName.text.toString().isEmpty()){
            empName.error = "Please enter name"
            return
        }
        if(empAge.text.toString().isEmpty()){
            empAge.error ="Please enter age"
            return
        }
        if(empSalary.text.toString().isEmpty()){
            empSalary.error = "Please enter salary"
            return
        }

        // đẩy dữ liệu
        // tạo ra key: khóa chính ấy
        val empId = dbRef.push().key!!
        val employee = EmployeeModel(empId,empName.text.toString(),empAge.text.toString(),empSalary.text.toString())

        // thêm dữ liệu vào bảng Employees ở trên
        dbRef.child(empId).setValue(employee)
            .addOnCompleteListener {
                Toast.makeText(this, "Insert thành công", Toast.LENGTH_SHORT).show()
                // xóa trăng ô nhập
                empName.text.clear()
                empAge.text.clear()
                empSalary.text.clear()
            }. addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()
            }

    }
}