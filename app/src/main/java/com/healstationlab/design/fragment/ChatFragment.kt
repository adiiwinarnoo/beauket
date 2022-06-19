package com.healstationlab.design.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.healstationlab.design.R
import com.healstationlab.design.adapter.ChatAdapter
import com.healstationlab.design.dto.chat
import com.healstationlab.design.model.Chat
import com.healstationlab.design.resource.Constant
import com.healstationlab.design.resource.RetrofitMansae
import com.healstationlab.design.ui.AddChatActivity
import com.healstationlab.design.ui.DetailChatActivity
import com.healstationlab.design.ui.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatFragment : Fragment() {

    var mainActivity: MainActivity? = null
    var chatList : ArrayList<Chat> = arrayListOf()
    lateinit var chatRecyclerView : RecyclerView
    private lateinit var editTextTextPersonName7 : EditText
    private lateinit var view126 : View
    private lateinit var spinner6 : Spinner
    private lateinit var textView368 : TextView
    var category = "FREE"
//    var eidt_check = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)
        chatRecyclerView = view.findViewById(R.id.chatRecyclerView)
        editTextTextPersonName7 = view.findViewById(R.id.editTextTextPersonName7) as EditText
        view126 = view.findViewById(R.id.view126)
        spinner6 = view.findViewById(R.id.spinner6)
        textView368 = view.findViewById(R.id.textView368)

        val spinnerItems = arrayListOf("피부 고민별","모공", "블랙헤드", "색소", "주름", "트러블", "민감", "다크서클")
        val spinnerAdapter = ArrayAdapter(context!!, R.layout.spinner_item, spinnerItems)

        textView368.setOnClickListener {
            textView368.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#5A2D8B"))
            textView368.setTextColor(Color.WHITE)
            getChat(category = "FREE")
        }

        spinner6.apply {
            adapter = spinnerAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                    when(position){
                        1 -> {
                         //   parent?.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#059899"))
                            category = "PORE"
                            getChat(category = category)
                            //background()
                        }
                        2 -> {
                            category = "BLACK_HEAD"
                            getChat(category = category)
                            //background()
                        }
                        3 -> {
                            category = "COLORED"
                            getChat(category = category)
                            //background()
                        }
                        4 -> {
                            category = "WRINKLE"
                            getChat(category = category)
                            //background()
                        }
                        5 -> {
                            category = "TROUBLE"
                            getChat(category = category)
                            //background()
                        }
                        6 -> {
                            category = "SENSITIVITY"
                            getChat(category = category)
                            //background()
                        }
                        7 -> {
                            category = "DARK_CIRCLE"
                            getChat(category = category)
                            //background()
                        }
                    }
                }
            }
        }

        val floating = view.findViewById<ImageButton>(R.id.floating_button)

        floating.setOnClickListener {
            val intent = Intent(context, AddChatActivity::class.java)
            startActivityForResult(intent, 1100)
            activity!!.overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
        }

        /** 수다방 검색 **/
        view126.setOnClickListener {
            getChat(category = category)
        }

        getChat(category)
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = activity as MainActivity
    }

    override fun onResume() {
        super.onResume()
        if(Constant.edit_check){
            editTextTextPersonName7.setText("")
        } else {
            Constant.edit_check = true
        }
    }

    override fun onDetach() {
        super.onDetach()
        editTextTextPersonName7.setText("")
        mainActivity = null
        chatList.clear()
    }



    fun getChat(category : String){
        RetrofitMansae.server.getChat(pageNumber = 0, pageSize = 100, keyword = editTextTextPersonName7.text.toString(), category = category)
            .enqueue(object : Callback<chat>{
                override fun onFailure(call: Call<chat>, t: Throwable) {

                }

                override fun onResponse(call: Call<chat>, response: Response<chat>) {
                    chatList.clear()
                    when(response.body()!!.responseCode){
                        "SUCCESS" -> {
                            for(i in response.body()!!.data){
                                if(i.contents != null){
                                    chatList.add(Chat(i.id, i.user?.id, i.user?.nickname.toString(), i.contents.toString(), i.createdDate, i.user?.recommendProductCode, i.user?.skinProblems, i.user?.imageUrl,
                                        i.imageUrls, i.user?.birthDate))
                                }
                            }

                            val chatAdapter = ChatAdapter(chatList)

                            chatRecyclerView.apply {
                                adapter = chatAdapter
                                layoutManager = LinearLayoutManager(context)
                            }

                            chatAdapter.setItemClickListner(object : ChatAdapter.ItemClickListener{
                                override fun onClick(view: View, skin : String, position: Int) {
                                    val intent = Intent(context, DetailChatActivity::class.java)
                                    intent.putExtra("id", chatList[position].id)
                                    Log.d("id-00", chatList[position].id.toString())
                                    intent.putExtra("user_id",chatList[position].userId)
                                    intent.putExtra("skin", skin)
                                    intent.putExtra("create", chatList[position].createdDate)
                                    intent.putExtra("img", chatList[position].imageUrl)
                                    intent.putExtra("category", category)
                                    startActivity(intent)
                                    activity!!.overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
                                }
                            })
                        }
                    }
                }
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                1100 -> {
                    getChat(category)
                }
                else -> {
                }
            }
        }
    }

    fun background(){
        textView368.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
        textView368.setTextColor(Color.parseColor("#777777"))
    }
}