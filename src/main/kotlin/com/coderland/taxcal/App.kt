package com.coderland.taxcal

import java.io.File

fun main(args: Array<String>) {

    if (!File(args[0]).isFile && !File(args[1]).isFile) {
        println("Please provide proper input files")
        return
    }

    ProcessInput(args).calculateTaxOnInputValues()
}