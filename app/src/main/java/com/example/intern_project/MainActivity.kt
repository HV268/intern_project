package com.example.intern_project

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CustomAdapter
    private val itemList: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        adapter = CustomAdapter(itemList, object : CustomAdapter.ItemClickListener {
            override fun onEditClick(position: Int) {
                showEditDialog(position)
            }

            override fun onDeleteClick(position: Int) {
                showDeleteDialog(position)
            }
        })
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val addButton = findViewById<Button>(R.id.addButton)
        addButton.setOnClickListener {
            showAddDialog()
        }
    }

    private fun showAddDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null)
        val editText = dialogView.findViewById<EditText>(R.id.editText)
        val saveButton = dialogView.findViewById<Button>(R.id.saveButton)

        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Add Item")

        val dialog = dialogBuilder.show()

        saveButton.setOnClickListener {
            val text = editText.text.toString()
            itemList.add(text)
            adapter.notifyDataSetChanged()
            dialog.dismiss()
        }
    }

    private fun showEditDialog(position: Int) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null)
        val editText = dialogView.findViewById<EditText>(R.id.editText)
        val saveButton = dialogView.findViewById<Button>(R.id.saveButton)

        editText.setText(itemList[position])

        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Edit Item")

        val dialog = dialogBuilder.show()

        saveButton.setOnClickListener {
            val text = editText.text.toString()
            itemList[position] = text
            adapter.notifyDataSetChanged()
            dialog.dismiss()
        }
    }

    private fun showDeleteDialog(position: Int) {
        val dialogBuilder = AlertDialog.Builder(this)
            .setTitle("Delete Item")
            .setMessage("Are you sure you want to delete this item?")
            .setPositiveButton("Yes") { _, _ ->
                itemList.removeAt(position)
                adapter.notifyDataSetChanged()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }

        val dialog = dialogBuilder.create()
        dialog.show()
    }
}
