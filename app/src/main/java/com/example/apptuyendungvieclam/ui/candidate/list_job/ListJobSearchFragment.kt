package com.example.apptuyendungvieclam.ui.candidate.list_job

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apptuyendungvieclam.R
import com.example.apptuyendungvieclam.data.model.User
import com.example.apptuyendungvieclam.data.model.job.Job
import com.example.apptuyendungvieclam.databinding.FragmentListJobSearchBinding
import com.example.apptuyendungvieclam.ui.base.fragment.BaseMvvmFragment
import com.example.apptuyendungvieclam.ui.employer.job.job_information.JobInformationFragment
import com.example.apptuyendungvieclam.ui.employer.job.my_job.JobAdapter
import com.example.apptuyendungvieclam.ui.payer.PayerActivity

class ListJobSearchFragment(var user: User?) : BaseMvvmFragment<ListJobCallBack,ListJobSearchViewModel>(),ListJobCallBack,JobAdapter.IJob {

    override fun initComponents() {
        getBindingData().listJobSearchViewModel = mModel
        getBindingData().searchView.clearFocus()
        // Thao tác READ (Đọc dữ liệu việc làm)
        mModel.getJobSearchDataFromDB(requireContext(),"")
        // Logic gọi READ danh sách Job từ DB
        initRecyclerViewoJob()
        onSearch()
    }

    // HÀM SEARCH
    private fun onSearch(){
        getBindingData().searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String): Boolean {
                // Thao tác READ (Đọc dữ liệu việc làm theo tiêu chí tìm kiếm)
                mModel.getJobSearchDataFromDB(requireContext(),newText)
                getBindingData().rcvListJob.adapter!!.notifyDataSetChanged();
                return false
            }
        })
    }

    override fun getLayoutMain(): Int {
        return R.layout.fragment_list_job_search
    }

    override fun setEvents() {

    }

    override fun getBindingData() = mBinding as FragmentListJobSearchBinding

    override fun getViewModel(): Class<ListJobSearchViewModel> {
        return ListJobSearchViewModel::class.java
    }

    override fun error(id: String, error: Throwable) {
        showMessage(error.message!!)
    }
    companion object{
        val TAG = ListJobSearchFragment::class.java.name
    }

    private fun initRecyclerViewoJob(){
        val jobAdapter = JobAdapter(this, requireContext(),user!!)
        getBindingData().rcvListJob.layoutManager = LinearLayoutManager(context)
        getBindingData().rcvListJob.adapter = jobAdapter
    }

    override fun count(): Int {
        return mModel.getListJobSearch().size
    }

    override fun getJob(position: Int): Job {
        return mModel.getListJobSearch()[position]
    }

    override fun onClickJob(position: Int) {
        if(mModel.checkActive(requireContext(),user!!.idAccount)) { // Kiểm tra active
            val job = mModel.getListJobSearch()[position]
            val jobInformationFragment = JobInformationFragment(user!!)
            // Truyền dữ liệu Job qua Bundle
            val bundle = Bundle()
            bundle.putSerializable("job",job)
            jobInformationFragment.arguments = bundle
            // Cơ chế: FRAGMENT TRANSACTION (Chuyển trang)
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentMain2, jobInformationFragment)
            fragmentTransaction.addToBackStack(JobInformationFragment.TAG)
            fragmentTransaction.commit()
        }else{
            goToPay() // Chuyển đến Thanh toán
        }
    }

    fun goToPay(){
        val view = View.inflate(context, R.layout.dialog_view, null)
        val builder = AlertDialog.Builder(context)
        builder.setView(view)
        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(R.color.transparent)
        dialog.setCancelable(false)
        val btnCancel  = view.findViewById<Button>(R.id.btnCancel)
        val btnGoPAY  = view.findViewById<Button>(R.id.btnGoPay)
        btnGoPAY.setOnClickListener {
            // Cơ chế: INTENT
            val intent = Intent(context, PayerActivity::class.java)
            // Chi tiết: Truyền ID người dùng cần thanh toán
            intent.putExtra("userId",user!!.idAccount)
            startActivity(intent)
            dialog.dismiss()
        }
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    override fun onClickPower(position: Int) {

    }

    override fun onClickDelete(position: Int) {

    }

}