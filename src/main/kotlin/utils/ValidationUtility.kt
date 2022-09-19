package utils

fun genderVal(ComingGender : Char) : Boolean {
    return listOf('M','F','O').contains(ComingGender)
}
fun emailVal(comingEmail : CharArray) : Boolean {
    return (comingEmail.contains('@'))
}

fun weightVal(comingWeight : Double) : Boolean {
    return comingWeight in 20.0..150.0

}
fun heightVal(comingHeight : Float) : Boolean {
    return comingHeight in 1.0..2.0
}


