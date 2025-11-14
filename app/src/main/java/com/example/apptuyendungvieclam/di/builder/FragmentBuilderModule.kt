package com.example.apptuyendungvieclam.di.builder

import com.example.apptuyendungvieclam.ui.candidate.answer.AnswerFragment
import com.example.apptuyendungvieclam.ui.candidate.list_job.ListJobSearchFragment
import com.example.apptuyendungvieclam.ui.employer.company.create_company.CreateCompanyFragment
import com.example.apptuyendungvieclam.ui.employer.job.job_information.JobInformationFragment
import com.example.apptuyendungvieclam.ui.employer.create_job.create_description.AddJobFragment
import com.example.apptuyendungvieclam.ui.employer.create_job.create_question.CreateQuestionFragment
import com.example.apptuyendungvieclam.ui.employer.create_job.create_request.CreateRequestFragment
import com.example.apptuyendungvieclam.ui.employer.create_job.create_right.CreateRightFragment
import com.example.apptuyendungvieclam.ui.employer.create_job.create_status.CreateStatusFragment
import com.example.apptuyendungvieclam.ui.employer.job.my_job.MyJobFragment
import com.example.apptuyendungvieclam.ui.employer.company.update_company.UpdateCompanyFragment
import com.example.apptuyendungvieclam.ui.login.signin.LoginFragment
import com.example.apptuyendungvieclam.ui.login.signup.RegisterFragment
import com.example.apptuyendungvieclam.ui.xample.user.UserFragment
import com.example.apptuyendungvieclam.ui.noticification.NoticificationFragment
import com.example.apptuyendungvieclam.ui.account.ProfileFragment
import com.example.apptuyendungvieclam.ui.account.information.InformationFragment
import com.example.apptuyendungvieclam.ui.account.update_skill.UpdateSkillFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeUserFragment(): UserFragment

    @ContributesAndroidInjector
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun contributeRegisterFragment(): RegisterFragment

    @ContributesAndroidInjector
    abstract fun contributeProfileFragment() : ProfileFragment

    @ContributesAndroidInjector
    abstract fun contributeMyJobFragment() : MyJobFragment

    @ContributesAndroidInjector
    abstract fun contributeNoticificationFragment() : NoticificationFragment

    @ContributesAndroidInjector
    abstract fun contributeAddJobFragment() : AddJobFragment

    @ContributesAndroidInjector
    abstract fun contributeUpdateSkillFragment() : UpdateSkillFragment

    @ContributesAndroidInjector
    abstract fun contributeJobInformationFragment() : JobInformationFragment

    @ContributesAndroidInjector
    abstract fun contributeUpdateCompanyFragment() : UpdateCompanyFragment

    @ContributesAndroidInjector
    abstract fun contributeCreateRequestFragment() : CreateRequestFragment

    @ContributesAndroidInjector
    abstract fun contributeCreateQuestionFragment() : CreateQuestionFragment

    @ContributesAndroidInjector
    abstract fun contributeCreateRightFragment() : CreateRightFragment

    @ContributesAndroidInjector
    abstract fun contributeCreateStatusFragment() : CreateStatusFragment

    @ContributesAndroidInjector
    abstract fun contributeListJobSearchFragment() : ListJobSearchFragment

    @ContributesAndroidInjector
    abstract fun contributeAnswerFragment() : AnswerFragment

    @ContributesAndroidInjector
    abstract fun contributeCreateCompanyFragment() : CreateCompanyFragment

    @ContributesAndroidInjector
    abstract fun contributeInformationFragment() : InformationFragment
}