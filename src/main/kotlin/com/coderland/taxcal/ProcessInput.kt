package com.coderland.taxcal

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.File

class ProcessInput(args: Array<String>) {


    private var taxSlabList: MutableList<TaxSlab> = arrayListOf()

    private var yearWiseIncomeList: MutableList<UserIncome> = arrayListOf()


    fun calculateTaxOnInputValues() {


        yearWiseIncomeList.forEach { user ->


            val taxSlab = taxSlabList.find { taxSlab -> taxSlab.year == user.year }



            taxSlab?.let {

                val userNetIncome = user.calculateUserNetIncome(taxSlab.ageBenefit!!, taxSlab.govBonds!!)

                val taxCal = TaxCalculator(taxSlab, userNetIncome)

                val taxOnIncome = taxCal.calculateTaxOnIncome()


                val  cess = taxCal.calculateCessOnTax(taxOnIncome)


                val total = taxOnIncome + cess

                println("$taxOnIncome, $cess, $total ")


            } ?: kotlin.run {
                println("Tax Slab Not Found  " + user.year)
            }
        }

    }

    init {

        val bufferedReader: BufferedReader =
            File(args[1]).bufferedReader()

        val inputString = bufferedReader.use { it.readText() }
        val listType = object : TypeToken<MutableList<TaxSlab>>() {}.type
        taxSlabList = Gson().fromJson<MutableList<TaxSlab>>(inputString, listType)


        File(args[0]).forEachLine {
            val investmentDetails = it.split(",")
            yearWiseIncomeList.add(
                UserIncome(
                    investmentDetails[0].toInt(),
                    investmentDetails[1].toInt(),
                    investmentDetails[2].toDouble(),
                    investmentDetails[3].toDouble()
                )
            )
        }


    }
}

