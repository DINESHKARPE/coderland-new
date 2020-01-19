package com.coderland.taxcal

import com.coderland.taxcal.datamodel.Agebenifit

class UserIncome internal constructor( val year: Int,  val age: Int, val  annumIncome: Double,  val investment: Double){

    fun calculateUserNetIncome(ageBenefit: Agebenifit, govBonds: Double): Double  {

        ageBenefit?.let {
            if (age >= ageBenefit.age) {
                return (annumIncome - ageBenefit.reduced) - compareInvestmentWithGovBonds(govBonds)
            }
        }
        return annumIncome - compareInvestmentWithGovBonds(govBonds)
    }

    private fun compareInvestmentWithGovBonds(govBonds: Double): Double {

        return if(investment > govBonds){
            govBonds
        }else{
            investment
        }
    }
}