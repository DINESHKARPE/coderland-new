package com.coderland.taxcal

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.kotlintest.specs.StringSpec
import io.kotlintest.shouldBe

class TaxCalculatorTest : StringSpec() {

    init {


        var jsonString = """[{
  "year":"2019",
  "taxBracket":[{"percentage":"0","min":0,"max": "100000"},{"percentage":"10","min":"100001","max": "500000"},{"percentage":"20","min":"6000001","max":"1200000"},{"percentage":"30","min":"12000001"}],
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


        val taxSlab = taxSlabList.find { taxSlab -> taxSlab.year == 2019 }



        "calculateCessOnTax" {

            val taxCal: TaxCalculator? = taxSlab?.let { TaxCalculator(it, 750000.0) }
            var tax = taxCal?.calculateTaxOnIncome();
            tax?.let { taxCal?.calculateCessOnTax(it) } shouldBe (0.0)
        }

        "calculateTaxOnIncome" {
            val taxCal: TaxCalculator? = taxSlab?.let { TaxCalculator(it, 1200000.0) }
            taxCal?.calculateTaxOnIncome() shouldBe (170000.0)
        }
    }

}