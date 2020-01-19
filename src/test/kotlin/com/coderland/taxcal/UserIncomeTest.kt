package com.coderland.taxcal

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.kotlintest.specs.StringSpec
import io.kotlintest.shouldBe
import java.io.File

class UserIncomeTest : StringSpec() {


    init {

        val yearWiseIncomeList: MutableList<UserIncome> = arrayListOf()
        val inputString = "2019,25,1825000,150000"


        var jsonString = """[{
  "year":"2019",
  "taxBracket":[{"percentage":"0","min":0,"max": "100000"},{"percentage":"10","min":"100001","max": "500000"},{"percentage":"20","min":"5000001","max":"1200000"},{"percentage":"30","min":"12000001"}],
  "govBonds":"150000",
  "cess": {
      "percentage":"2",
      "max": "500000"
    },
  "ageBenefit":{
      "age":"60",
      "reduced":"50000"
    }
}]"""

        val listType = object : TypeToken<List<TaxSlab>>() {}.type
        val taxSlabList = Gson().fromJson<List<TaxSlab>>(jsonString, listType)

        val investmentDetails = inputString.split(",")
        yearWiseIncomeList.add(
            UserIncome(
                investmentDetails[0].toInt(),
                investmentDetails[1].toInt(),
                investmentDetails[2].toDouble(),
                investmentDetails[3].toDouble()
            )
        )

        "calculateUserNetIncome" {


            yearWiseIncomeList.forEach { user ->


                val taxSlab = taxSlabList.find { taxSlab -> taxSlab.year == user.year }

                taxSlab?.let {
                    user.calculateUserNetIncome(taxSlab.ageBenefit!!, taxSlab.govBonds!!) shouldBe (1675000.0)
                }
            }
        }
    }

}