import controllers.UserStore
import models.User
import mu.KotlinLogging
import utils.*
import utils.Conversion

//var user = User()
val userdb = UserStore()
private val NSlog = KotlinLogging.logger {}

fun main(){
    userdb.addNew(User(1, "Homer Simpson", "homer@simpson.com", 178.0, 2.0f, 'M'))
    userdb.addNew(User(2, "Marge Simpson", "marge@simpson.com", 140.0, 1.6f, 'F'))
    userdb.addNew(User(3, "Lisa Simpson", "lisa@simpson.com", 100.0, 1.2f, 'F'))
    userdb.addNew(User(4, "Bart Simpson", "bart@simpson.com", 80.0, 1.0f, 'M'))
    NSlog.info{"Hello, App is up and running"}
    runApp()
}
fun adduser(){
    val user = User()
    userdb.create(user)

    print(" Please, Enter your details i.e. Name,Email \n")
    print(" Name ...\n")
    user.name = readLine().toString()

    do {
        print("Email...\n")
        val mail = readLine().toString()
        if (emailVal(mail.toCharArray())){
            user.email = mail
        }
    } while (!emailVal(mail.toCharArray()))

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
//    for ((i,user) in userdb.allUsers().withIndex()) {
//        println("User ${i}")
//        print("The User Details are: \nName: ${user.name}\nMail: ${user.email}\nID: ${user.id} \nWeight: ${user.weight} \nHeight: ${user.height}\nGender: ${user.gender}\n")
//    }
    userdb.allUsers()
        .sortedBy { it.name }
        .forEach{println(it)}
}
fun getUserbyID(){
    print("Enter User ID: \n")
    val user = userdb.findUser(readLine()?.toIntOrNull() ?: -1)
    if ( user == null) {
        NSlog.info{"No user found with the mentioned id"}
    }else {
        print("The User Details are: \nName: ${user.name}\nMail: ${user.email}\nID: ${user.id} \nWeight: ${user.weight} \nHeight: ${user.height}\nGender: ${user.gender}\n")
    }
}

fun deleteUserbyID(){
    print("Enter User ID: \n")
    if (userdb.delUser(userdb.findUser(readLine()?.toIntOrNull() ?: -1))){
        NSlog.info{"User deleted"}
    }else{
        NSlog.info{"No user found with the mentioned id"}
    }
}

fun updateUserbyID(){
    dispUser()
    print("Enter User ID: \n")
    val id = readLine()?.toIntOrNull() ?: -1
    val user = userdb.findUser(id)
    if ( user == null) {
        NSlog.info{"No user found with the mentioned id"}
    }else {
        val newUser = User()
        newUser.id = id

        print(" Please, Enter your details i.e. Name,Email \n")
        print(" Name ...\n")
        newUser.name = readLine().toString()

        do {
            print("Email...\n")
            val mail = readLine().toString()
            if (emailVal(mail.toCharArray())){
                newUser.email = mail
            }
        } while (!emailVal(mail.toCharArray()))

        do {
            print("Weight(kgs) ...\n")
            val weight = readLine()?.toDoubleOrNull() ?: 0.0
            if (weightVal(weight)){
                newUser.weight = weight
            }
        }while (!weightVal(weight))

        do {
            print("Height(meters)...\n")
            val height = readLine()?.toFloatOrNull() ?: 0.0F
            if (heightVal(height)){
                newUser.height = height
            }
        }while (!heightVal(height))


        do {
            print("Gender(M|F|O)...\n")
            val inpChar = readLine()!!.single().uppercase()
            if (genderVal(inpChar[0])) {
                newUser.gender = inpChar[0]
            }
        }while (!genderVal(inpChar[0]))

        userdb.updateUser(newUser)
    }
}

fun searchByGender(){
    println("Enter gender(M/F/O):")
    val filtered = userdb.filterUser(readLine().toString().first())
    if (!filtered!!.isEmpty()){
        filtered.forEach{println(it)}
    }else{
        println("Gender not found")
    }
}

fun generateReport(){
    val weight = userdb.weightBreakDown()
    val height = userdb.heightBreakDown()
    print("""
        -----------------------
            USER REPORT
        -----------------------
        
        Number of User: ${userdb.allUsers().count()}
        Gender Breakdown: ${userdb.genderBreak()}
        Min Weight: ${weight.min} kg
        Max Weight: ${weight.max} kg
        Avg Weight: ${weight.avg} kg
        Min Height: ${height.min} meters
        Max Height: ${height.max} meters
        Avg Height: ${height.avg} meters        

    """.trimIndent())
}

fun generateReportJava(){

    val weight = userdb.weightBreakDown()
    val height = userdb.heightBreakDown()
    print("""
        -----------------------
            USER REPORT
        -----------------------
        
        Number of User: ${userdb.allUsers().count()}
        Gender Breakdown: ${userdb.genderBreak()}
        Min Weight: ${utils.Conversion.convertKGtoPounds(weight.min,1.0)} pounds
        Max Weight: ${utils.Conversion.convertKGtoPounds(weight.max,1.0)} pounds
        Avg Weight: ${utils.Conversion.convertKGtoPounds(weight.avg,1.0)} pounds
        Min Height: ${utils.Conversion.convertMetresToInches(height.min.toDouble(),1.0)} inches
        Max Height: ${utils.Conversion.convertMetresToInches(height.max.toDouble(),1.0)} inches
        Avg Height: ${utils.Conversion.convertMetresToInches(height.avg.toDouble(),1.0)} inches        

    """.trimIndent())

}

fun imperialUser(){
    println("The user details (imperial) are")
    userdb.allUsers().forEach{ println("User: ${it.name}(${it.email}), ${utils.Conversion.convertKGtoPounds(it.weight,1.0)} pounds, ${utils.Conversion.convertMetresToInches(it.height.toDouble(),1.0)} inches") }
}



fun menu(): Int{
    print("""
        |Main Menu:
        |  1. Add User
        |  2. List Users
        |  3. Search by ID
        |  4. Delete by ID
        |  5. Update by ID
        |  6. Search by Gender(M/F/O)
        |  7. Generate User Report
        |  8. Users (imperial)
        |  0. Exit
        |Please enter your option: """.trimMargin())
    return readLine()?.toIntOrNull() ?: -1
}



fun runApp(){
    var input: Int
    do {
        input = menu()
        when(input) {
            1 -> adduser()
            2 -> dispUser()
            3 -> getUserbyID()
            4 -> deleteUserbyID()
            5 -> updateUserbyID()
            6 -> searchByGender()
            7 -> generateReport()
            8 -> imperialUser()
            in(9..15) -> NSlog.info{"new feature coming"}
            0 -> println("Bye...")
            else -> print("Try again")
        }
    } while (input != 0)
}