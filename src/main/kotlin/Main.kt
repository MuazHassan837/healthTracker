import models.User
import utils.emailVal
import utils.genderVal
import utils.heightVal
import utils.weightVal

var user = User()

fun main(){
    print("Legendary Hello World :p \n")
    runApp()
}
fun adduser(){
    print(" Please, Enter your details i.e. Name,Email,ID \n")
    print(" Name ...\n")
    user.name = readLine().toString()

    do {
        print("Email...\n")
        val mail = readLine().toString()
        if (emailVal(mail.toCharArray())){
            user.email = mail
        }
    } while (!emailVal(mail.toCharArray()))

    print("ID...\n")
    user.id = readLine()?.toIntOrNull() ?: -1


    do {
        print("Weight(kgs) ...\n")
        val weight = readLine()?.toDoubleOrNull() ?: 0.0
        if (weightVal(weight)){
            user.weight = weight
        }
    }while (!weightVal(weight))

    do {
        print("Height(meters)...\n")
        val height = readLine()?.toFloatOrNull() ?: 0.0F
        if (heightVal(height)){
            user.height = height
        }
    }while (!heightVal(height))


    do {
        print("Gender(M|F|O)...\n")
        val inpChar = readLine()!!.single().uppercase()
        if (genderVal(inpChar[0])) {
            user.gender = inpChar[0]
        }
    }while (!genderVal(inpChar[0]))

}
fun dispUser(){
    print("\nThe User Details are: \n${user.name} \n${user.email}\n${user.id} \n${user.weight} \n${user.height}\n${user.gender}")
}

fun menu(): Int{
    println("\n Menu")
    println("\n1) add new user")
    println("\n2) list current user")
    println("\n0) Exit")
    print("\nPlease enter your choice: ")

    return readLine()?.toIntOrNull() ?: -1
}



fun runApp(){
    var input: Int
    do {
        input = menu()
        when(input) {
            1 -> adduser()
            2 -> dispUser()
            in(3..6) -> println("feature coming")
            0 -> println("Bye...")
            else -> print("Try again")
        }
    } while (input != 0)
}