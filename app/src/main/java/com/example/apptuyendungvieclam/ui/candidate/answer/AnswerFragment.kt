package com.example.apptuyendungvieclam.ui.candidate.answer

import android.app.AlertDialog
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apptuyendungvieclam.R
import com.example.apptuyendungvieclam.data.model.User
import com.example.apptuyendungvieclam.data.model.job.Job
import com.example.apptuyendungvieclam.data.model.job.question.Question
import com.example.apptuyendungvieclam.databinding.FragmentAnswerBinding
import com.example.apptuyendungvieclam.ui.base.fragment.BaseMvvmFragment
import com.example.apptuyendungvieclam.ui.base.viewmodel.BaseViewModel
import com.example.apptuyendungvieclam.ui.candidate.list_job.ListJobSearchFragment

class AnswerFragment(private val job: Job,private val user: User) : BaseMvvmFragment<AnswerCallBack,AnswerViewModel>(),AnswerCallBack,AnswerTheQuestionAdapter.IAnswerTheQuestion{

    override fun initComponents() {
        getBindingData().answerViewModel = mModel
        mModel.uiEventLiveData.observe(this){
            when(it){
                BaseViewModel.FINISH_ACTIVITY -> finishActivity()
                AnswerViewModel.CLICK_COMFIRM -> onClickComfirm() // Sự kiện Ứng tuyển/Xác nhận
                AnswerViewModel.APPLY_SUCCESS -> onApplySuccess() // Sự kiện Ứng tuyển thành công
                AnswerViewModel.APPLY_ERROR -> onApplyError()     // Sự kiện Ứng tuyển lỗi
            }
        }
        initRecyclerView()
    }

    private fun onApplyError() {
        Toast.makeText(context,"Bạn đã ứng tuyển công việc này",Toast.LENGTH_SHORT).show()
    }

    private fun onApplySuccess() {
        // 1. Chuẩn bị và hiển thị Dialog
        val view = View.inflate(context, R.layout.dialog_view_apply_success, null)
        val builder = AlertDialog.Builder(context)
        builder.setView(view)
        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(R.color.transparent)
        dialog.setCancelable(false)

        // 2. Xử lý Sự kiện Click nút "Xác nhận" trong Dialog
        val btnComfirm  = view.findViewById<Button>(R.id.btnComfirm)
        btnComfirm.setOnClickListener {
            // Hành động: Thay thế Fragment hiện tại bằng ListJobSearchFragment
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentMain2,ListJobSearchFragment(user))
            fragmentTransaction.commit()
            dialog.dismiss() // Đóng Dialog
        }
    }

//    private fun onClickComfirm() {
//          val view = View.inflate(context,R.layout.dialog_view,null)
//          val builder = AlertDialog.Builder(context)
//          builder.setView(view)
//          val dialog = builder.create()
//          dialog.show()
//          dialog.window?.setBackgroundDrawableResource(R.color.transparent)
//          dialog.setCancelable(false)
//          var btnBuy = view.findViewById<Button>(R.id.btnBuy)
//          var btnTrial30Days = view.findViewById<Button>(R.id.btnTrial30Days)
//          btnBuy.setOnClickListener {
//              Toast.makeText(context,"Chưa hoàn thiện",Toast.LENGTH_SHORT).show()
//          }
//          btnTrial30Days.setOnClickListener {
//              Toast.makeText(context,"Thành công",Toast.LENGTH_SHORT).show()
//              dialog.dismiss()
//          }
//    }

    private fun onClickComfirm() {
        // Thao tác INSERT (Ghi dữ liệu ứng tuyển và câu trả lời)
       mModel.setApply(user,job, requireContext())
        // Logic gọi INSERT vào bảng Apply và Answer
    }

    override fun getLayoutMain(): Int {
        return R.layout.fragment_answer
    }

    override fun setEvents() {

    }

    override fun getBindingData() = mBinding as FragmentAnswerBinding

    override fun getViewModel(): Class<AnswerViewModel> {
        return AnswerViewModel::class.java
    }

    override fun error(id: String, error: Throwable) {
        showMessage(error.message!!)
    }

    companion object{
        val TAG = AnswerFragment::class.java.name
    }

    override fun count(): Int {
        return job.listQuestion!!.size
    }

    override fun getQuestion(position: Int): Question {
        return job.listQuestion!![position]
    }

    private fun initRecyclerView(){
        val answerTheQuestionAdapter = AnswerTheQuestionAdapter(this)
        getBindingData().rcvAnswerToQuestion.layoutManager = LinearLayoutManager(context)
        getBindingData().rcvAnswerToQuestion.adapter = answerTheQuestionAdapter
    }
}