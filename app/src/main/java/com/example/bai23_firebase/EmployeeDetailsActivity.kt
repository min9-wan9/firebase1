package com.example.bai23_firebase

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase

class EmployeeDetailsActivity : AppCompatActivity() {

    private fun setValuesToView() {
        val txtId = findViewById<TextView>(R.id.txtId)
        val txtName = findViewById<TextView>(R.id.txtName)
        val txtAge = findViewById<TextView>(R.id.txtAge)
        val txtSalary = findViewById<TextView>(R.id.txtSalary)
        val btnUpdate = findViewById<Button>(R.id.btnUpdate)
        val btnDelete = findViewById<Button>(R.id.btnDelete)
        txtId.text = intent.getStringExtra("empId")
        txtAge.text = intent.getStringExtra("empAge")
        txtName.text = intent.getStringExtra("empName")
        txtSalary.text = intent.getStringExtra("empSalary")


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_employee_details)

        val txtId = findViewById<TextView>(R.id.txtId)
        val txtName = findViewById<TextView>(R.id.txtName)
        val txtAge = findViewById<TextView>(R.id.txtAge)
        val txtSalary = findViewById<TextView>(R.id.txtSalary)
        val btnUpdate = findViewById<Button>(R.id.btnUpdate)
        val btnDelete = findViewById<Button>(R.id.btnDelete)
        setValuesToView()

        // code nút delete
        btnDelete.setOnClickListener {
            deleteRecode(
                intent.getStringExtra("empId").toString()
            )
        }
        // nut update
        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("empId").toString(),
                intent.getStringExtra("empName".toString())

            )
        }
    }

    private fun openUpdateDialog(empId: String, empName: String?) {

        val txtId = findViewById<TextView>(R.id.txtId)
        val txtName = findViewById<TextView>(R.id.txtName)
        val txtAge = findViewById<TextView>(R.id.txtAge)
        val txtSalary = findViewById<TextView>(R.id.txtSalary)

        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = layoutInflater.inflate(R.layout.update_dialog,null)
        mDialog.setView(mDialogView)

        //
        val edtName = mDialogView.findViewById<EditText>(R.id.edtName)
        val edtAge = mDialogView.findViewById<EditText>(R.id.edtAge)
        val edtSalary = mDialogView.findViewById<EditText>(R.id.edtSalary)
        val btnUpdataData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        edtName.setText(intent.getStringExtra("empName").toString())
        edtAge.setText(intent.getStringExtra("empAge").toString())
        edtSalary.setText(intent.getStringExtra("empSalary").toString())
        mDialog.setTitle("Updating  $empName Record ")

        val AlerDialog = mDialog.create()
        AlerDialog.show()

        // code khi click updatedata
        btnUpdataData.setOnClickListener {
            updateEmpData (
                empId,
                edtName.text.toString(),
                edtAge.text.toString(),
                edtSalary.text.toString()
            )
            // thông báo ra update thành công
            Toast.makeText(applicationContext, "Employee update thành công", Toast.LENGTH_SHORT).show()
            // cập nhật lại thông thông trên
            txtName.text = edtName.text.toString()
            txtAge.text = edtAge.text.toString()
            txtSalary.text =edtSalary.text.toString()

            AlerDialog.dismiss()
        }


    }

    private fun updateEmpData(empId: String,
                              empName: String,
                              empAge: String,
                              empSalary: String)
    {
        val dbRef = FirebaseDatabase.getInstance().getReference("Employees").child(empId)
        val inforEmp = EmployeeModel(empId, empName, empAge, empSalary)
        dbRef.setValue(inforEmp)

    }

    private fun deleteRecode(id: String) {
        // lây địa chỉ của dòng dòng cần xóa trong table Employees
        var dbref = FirebaseDatabase.getInstance().getReference("Employees").child(id)
        val mTask = dbref.removeValue()
        mTask.addOnCompleteListener {
            Toast.makeText(this, "Delete employee thành công", Toast.LENGTH_SHORT).show()
            val intent = Intent(this,FetchingActivity::class.java)
//            finish()
            startActivity(intent)
        }.addOnFailureListener { err->
            Toast.makeText(this, "Error : ${err.message} ", Toast.LENGTH_SHORT).show()

        }




    }


}