package com.example.memesharing

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var currentimageurl: String?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Loadmeme()
    }

    private fun Loadmeme(){
        val textView = findViewById<TextView>(R.id.text)
        PrograssBar.visibility = View.VISIBLE
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"

        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener { response ->
                    currentimageurl = response.getString("url")

                    Glide.with(this).load(currentimageurl).listener(object:RequestListener<Drawable>{

                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                           PrograssBar.visibility=View.GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            PrograssBar.visibility=View.GONE
                            return false
                        }
                    }).into(Image)
                },
                Response.ErrorListener { error ->
                }
        )
        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }

    fun Sharememe(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type="text/plane"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey,check this meme $currentimageurl" )
        val chooser= Intent.createChooser(intent,"share this meme...")
        startActivity(intent)


    }
    fun Nextmeme(view: View) {

        Loadmeme()

    }
}