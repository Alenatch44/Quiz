package com.example.quiz_app

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.google.firebase.database.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var playButton: Button
    private lateinit var databaseReference: DatabaseReference

    companion object {
        var list: ArrayList<Modelclass> = ArrayList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playButton = findViewById(R.id.main_button)
        list = ArrayList()

        databaseReference = FirebaseDatabase.getInstance().getReference("Question")
        val query: Query = databaseReference.orderByChild("order")

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val modelclass: Modelclass? = dataSnapshot.getValue(Modelclass::class.java)
                    if (modelclass != null) {
                        list.add(modelclass)
                    }
                }

                playButton.setOnClickListener { v: View? ->
                    val intent = Intent(this@MainActivity, Questions_Activity::class.java)
                    startActivity(intent)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}