package com.coderland.taxcal

class TaxCalculator(val taxSlab: TaxSlab, var userNetIncome: Double) {

    fun calculateCessOnTax(tax: Double): Double {

        return if (tax > taxSlab.cess!!.max) {
            taxSlab.cess.percentage / 100.0 * tax
        }else{
            0.0
        }
    }

    fun calculateTaxOnIncome(): Double {

        var totalTax = 0.0

        for (tax in taxSlab.taxBracket!!) {
            totalTax += calculateTax(tax.percentage,tax.max)
        }

        return totalTax
    }

    private fun calculateTaxableIncome(netIncome: Double, max: Double): Double {

        return if (netIncome > max && max != 0.0) {
            this.userNetIncome -= max
            return max
        } else {
            this.userNetIncome -= netIncome
            netIncome

        }
    }

    private fun calculateTax(percentage: Double, max: Double): Double {
       return (percentage / 100.0) * calculateTaxableIncome(this.userNetIncome, max)
    }


}