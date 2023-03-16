package com.tsato.util

fun isEmailValid(str: String) = str.matches("[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+(?:[A-Z]{2}|com|org|net|gov|mil|biz|info|mobi|name|aero|jobs|museum)\\b".toRegex())

/*
At least one upper case English letter
At least one lower case English letter
At least one digit
At least one special character
Minimum 8 in length
Maximum 32 in length
 */
fun isPasswordValid(str: String) = str.matches("^(?=.*?[A-Z])(?=(.*[a-z]){1,})(?=(.*[\\d]){1,})(?=(.*[\\W]){1,})(?!.*\\s).{8,32}\$".toRegex())

fun isTitleValid(str: String) = str.matches("^.{1,32}\$".toRegex())

fun isDescriptionValid(str: String) = str.matches("^.{1,9999}\$".toRegex())

fun isPriceValid(str: String) = str.matches("^[0-9]{1,8}\$".toRegex())