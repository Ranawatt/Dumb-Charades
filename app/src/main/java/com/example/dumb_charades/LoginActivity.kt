package com.example.dumb_charades

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionManager
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.video_background.*

class LoginActivity : AppCompatActivity() {

    private var currentStateId: Int = R.id.start
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.anna_moskal)
        videoView.setVideoURI(uri)
        videoView.start()
        videoView.setOnPreparedListener {
            it.isLooping = true
        }
        login_text.setOnClickListener {
            when (currentStateId) {
                R.id.start -> {
//                    Toast.makeText(this,"Perform Login",Toast.LENGTH_LONG).show()
                    if (login_email.editText!!.text.toString().equals("sugandhpatna95@gmail.com") && login_password.editText!!.text.toString().equals("sugandh"))
                        intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

                }
                else -> motionLayout.transitionToStart()
            }
        }

        sign_up_text.setOnClickListener {
            when (currentStateId) {
                R.id.end -> {
                    Toast.makeText(this,"Perform SignUp",Toast.LENGTH_LONG).show()
                    if (sign_up_password.editText!!.text.toString().equals(sign_up_confirm_password)){
                        Toast.makeText(this,"Successfully Signed up",Toast.LENGTH_LONG).show()
                        startActivity(Intent(this,MainActivity::class.java))
                    }
                }
                else -> motionLayout.transitionToEnd()
            }
        }

        motionLayout.setTransitionListener(object : TransitionAdapter(){
            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                super.onTransitionCompleted(motionLayout, currentId)
                currentStateId = currentId
            }
        })
    }

    override fun onRestart() {
        super.onRestart()
        videoView.start()
    }
}
