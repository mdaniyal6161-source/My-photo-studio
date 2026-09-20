package com.picfix.app

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.Gravity
import android.view.View
import android.widget.*

class MainActivity : Activity() {
    private val navy = Color.rgb(7,20,38); private val blue = Color.rgb(22,119,255); private val purple = Color.rgb(123,63,242)
    private lateinit var root: LinearLayout
    private fun tv(text:String, size:Float=16f, bold:Boolean=false): TextView = TextView(this).apply { this.text=text; textSize=size; setTextColor(Color.WHITE); setPadding(16,12,16,12); if(bold) setTypeface(typeface,1) }
    private fun btn(text:String, color:Int=blue, action:()->Unit): Button = Button(this).apply { this.text=text; setTextColor(Color.WHITE); setBackgroundColor(color); setOnClickListener{action()}; isAllCaps=false }
    override fun onCreate(b:Bundle?) { super.onCreate(b); showHome() }
    private fun base(title:String): LinearLayout { root=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL; setBackgroundColor(navy); setPadding(12,18,12,12)}; root.addView(tv(title,26f,true)); return root }
    private fun finishPage(){ setContentView(ScrollView(this).apply{addView(root)}) }
    private fun showHome(){ val r=base("PicFix 👑"); r.addView(tv("AI Photo Studio + Local Services",15f)); r.addView(tv("Better Photos  •  Trusted Services",14f)); r.addView(btn("📸  AI Photo Studio",purple){showStudio()}); r.addView(btn("🛠  Local Services",Color.rgb(16,185,129)){showServices()}); r.addView(tv("Popular",20f,true)); listOf("❄️ AC Repair","⚡ Electrician","🚰 Plumber","🚗 Car Wash","🧹 Home Cleaning","💇 Beauty & Salon").forEach{r.addView(tv(it,16f))}; r.addView(btn("My Bookings"){showBookings()}); finishPage() }
    private fun showStudio(){ val r=base("AI Photo Studio"); r.addView(tv("Choose a category",19f,true)); listOf("Men","Women","Couple","💍 Wedding").forEach { x -> r.addView(btn(x,if(x=="💍 Wedding") purple else blue){ if(x.contains("Wedding")) showWedding() else showEditor(x) }) }; r.addView(tv("Popular templates",19f,true)); listOf("Business Look","Formal Suit","Car Scene","Office","Airplane","City Vibes").forEach{r.addView(tv("✨  $it"))}; r.addView(btn("← Home"){showHome()}); finishPage() }
    private fun showWedding(){ val r=base("Wedding Templates 💍"); r.addView(tv("Bride + Groom templates",19f,true)); listOf("Traditional Wedding","Modern Wedding","Royal Wedding","Outdoor Wedding","Nikah / Ceremony").forEach{r.addView(btn("💒  $it",purple){pickPhoto(it)})}; r.addView(tv("Tip: upload a clear face photo for the best result.",14f)); r.addView(btn("← AI Photo Studio"){showStudio()}); finishPage() }
    private fun showEditor(kind:String){ val r=base("$kind Photo Studio"); r.addView(btn("📷 Choose Photo",blue){pickPhoto(kind)}); r.addView(tv("After selecting a photo, this prototype shows the edit flow. Real AI generation can be connected to an image API/backend.",14f)); r.addView(btn("← AI Photo Studio"){showStudio()}); finishPage() }
    private fun pickPhoto(kind:String){ val i=Intent(Intent.ACTION_PICK).apply{type="image/*"}; startActivityForResult(i,1001) }
    override fun onActivityResult(req:Int,res:Int,data:Intent?){super.onActivityResult(req,res,data); if(req==1001 && res==RESULT_OK){ showPreview(data?.data) }}
    private fun showPreview(uri:Uri?){ val r=base("Preview ✨"); if(uri!=null){val img=ImageView(this).apply{setImageURI(uri); adjustViewBounds=true; setPadding(8,8,8,8)}; r.addView(img,LinearLayout.LayoutParams(-1,600))}; r.addView(tv("AI Edit Preview",20f,true)); r.addView(tv("Wedding / outfit / background options are ready for API integration.",14f)); r.addView(btn("Save to My Photos"){Toast.makeText(this,"Saved in prototype",Toast.LENGTH_SHORT).show()}); r.addView(btn("← AI Studio"){showStudio()}); finishPage() }
    private fun showServices(){ val r=base("Local Services 🛠"); r.addView(tv("Riyadh / Saudi Arabia",14f)); r.addView(tv("Search services",18f,true)); listOf("AC Repair & Maintenance","Electrician","Plumber","Car Wash & Detailing","Home Cleaning","Beauty & Salon","Handyman","Moving & Shifting").forEach{r.addView(btn(it,Color.rgb(16,185,129)){showProvider(it)})}; r.addView(btn("← Home"){showHome()}); finishPage() }
    private fun showProvider(service:String){ val r=base(service); r.addView(tv("Verified Service Provider",19f,true)); r.addView(tv("⭐ 4.8   •   124 reviews   •   2.5 km",15f)); r.addView(tv("Professional service • Fast response • Transparent pricing",14f)); r.addView(btn("Book Now",blue){showBooking(service)}); r.addView(btn("Call / Chat",Color.DKGRAY){Toast.makeText(this,"Contact flow ready for integration",Toast.LENGTH_SHORT).show()}); r.addView(btn("← Services"){showServices()}); finishPage() }
    private fun showBooking(service:String){ val r=base("Book Service"); r.addView(tv(service,20f,true)); r.addView(tv("Select date & time",17f,true)); val times=listOf("09:00 AM","11:00 AM","01:00 PM","03:00 PM","05:00 PM"); times.forEach{r.addView(btn(it,blue){})}; r.addView(tv("Estimated total: SAR 80",18f,true)); r.addView(btn("Confirm Booking",blue){Toast.makeText(this,"Booking confirmed",Toast.LENGTH_LONG).show(); showBookings()}); r.addView(btn("← Back"){showServices()}); finishPage() }
    private fun showBookings(){ val r=base("My Bookings"); r.addView(tv("Upcoming",20f,true)); r.addView(tv("No bookings yet",16f)); r.addView(btn("← Home"){showHome()}); finishPage() }
}
