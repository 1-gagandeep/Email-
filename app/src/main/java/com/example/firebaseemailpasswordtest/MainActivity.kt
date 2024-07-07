package com.example.firebaseemailpasswordtest

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Gallery
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID
import java.util.concurrent.TimeUnit

//class MainActivity : AppCompatActivity() {
//    private lateinit var edtEmail : EditText
//    private lateinit var edtPassword : EditText
//    private lateinit var btnLogin : Button
//    private lateinit var btnSignup : Button
//    private lateinit var mAuth : FirebaseAuth
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)
//
//        edtEmail = findViewById(R.id.edt_email)
//        edtPassword = findViewById(R.id.edt_password)
//        btnLogin = findViewById(R.id.btn_login)
//        btnSignup = findViewById(R.id.btn_sign_up)
//
//        mAuth = Firebase.auth
//
//        btnLogin.setOnClickListener {
//            val email = edtEmail.text.toString()
//            val password = edtPassword.text.toString()
//            login(email,password)
//        }
//
//        btnSignup.setOnClickListener {
//            val email = edtEmail.text.toString()
//            val password = edtPassword.text.toString()
//            signup(email,password)
//        }
//    }
//
//    private fun login(email: String, password: String) {
//        mAuth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    startActivity(
//                        Intent(this, HomeActivity::class.java)
//                    )
//                    Toast.makeText(this, "Logged In Successfully", Toast.LENGTH_SHORT).show()
//
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Toast.makeText(this, "Some Error Occured", Toast.LENGTH_SHORT).show()
//                }
//            }
//    }
//
//    private fun signup(email: String, password: String) {
//        mAuth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//
//                    Toast.makeText(this, "Signed In Successfully", Toast.LENGTH_SHORT).show()
//
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Toast.makeText(this, "Some Error Occured", Toast.LENGTH_SHORT).show()
//                }
//            }
//    }

//class MainActivity : AppCompatActivity() {
//
//    private lateinit var btnAddData : Button
//    private lateinit var btnReadData : Button
//    private lateinit var databaseRef : DatabaseReference
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)
//
//        databaseRef = Firebase.database.getReference("users")
//        btnAddData = findViewById(R.id.btn_add_data)
//        btnReadData = findViewById(R.id.btn_get_data)
//
//        val user_1 = User(
//            name = "Gagandeep",
//            age = 21,
//            email = "gagan2345@gmail.com"
//        )
//
//        val user_2 = User(
//            name = "Prince",
//            age = 22,
//            email = "prince2865@gmail.com"
//        )
//
//        btnAddData.setOnClickListener {
////            addDataToFirebaseDatabase(user)
//
//            databaseRef.child("user_1").setValue(user_1)
//            databaseRef.child("user_2").setValue(user_2)
//        }
//
//        btnReadData.setOnClickListener {
//            getAllDataFromFirebase()
//        }
//    }
//    private fun getAllDataFromFirebase() {
//
//        databaseRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                for (dataSnapshot in snapshot.children) {
//                    val currentUser = dataSnapshot.getValue(User::class.java)
//                    Log.i("currentUser", currentUser.toString())
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })
//    }
//
//
////    private fun addDataToFirebaseDatabase(user:User) {
////
////    }
//
//}
//
//data class User(
//    val name : String = "",
//    val age : Int = 0,
//    val email : String = ""
//)

class MainActivity : AppCompatActivity() {

    private lateinit var btnOpenGallery: Button
    private lateinit var btnUploadImage : Button
    private lateinit var imgPreview : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        btnOpenGallery = findViewById(R.id.btn_open_gallery)
        btnUploadImage = findViewById(R.id.btn_upload_img)
        imgPreview = findViewById(R.id.img_preview)

        btnOpenGallery.setOnClickListener {

            val gallaryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(gallaryIntent, 101)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == RESULT_OK) {
            imgPreview.setImageURI(data?.data)
        }

        btnUploadImage.setOnClickListener {
            uploadImage(data?.data)
        }
    }

    private fun uploadImage(imagaUri: Uri?) {
        val fileName = UUID.randomUUID().toString() + ".jpg"
        val storageReference = FirebaseStorage.getInstance().reference.child("images/$fileName")
        storageReference.putFile(imagaUri!!).addOnSuccessListener {
            Toast.makeText(this, "image upload successfully", Toast.LENGTH_SHORT).show()
        }
    }
}

data class User(
    val name : String = "",
    val age : Int = 0,
    val email : String = ""
)