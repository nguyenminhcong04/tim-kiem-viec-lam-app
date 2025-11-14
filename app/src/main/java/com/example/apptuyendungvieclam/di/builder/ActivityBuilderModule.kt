package com.example.apptuyendungvieclam.di.builder

import com.example.apptuyendungvieclam.ui.candidate.CandidateMainActivity
import com.example.apptuyendungvieclam.ui.employer.EmployerMainActivity
import com.example.apptuyendungvieclam.ui.login.FirstActivity
import com.example.apptuyendungvieclam.ui.payer.PayerActivity
import com.example.apptuyendungvieclam.ui.xample.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeFirstActivity(): FirstActivity

    @ContributesAndroidInjector
    abstract fun contributeEmployerMaintActivity(): EmployerMainActivity

    @ContributesAndroidInjector
    abstract fun contributeCandidateMainActivity(): CandidateMainActivity

    @ContributesAndroidInjector
    abstract fun contributePayerActivity(): PayerActivity
}