package controllers
import models.User


class UserStore {
    private val users = ArrayList<User>()
//    private val users = mutableListOf<User>()

    private var lastID = 200
    private fun incID() = ++lastID

    fun create(user : User){
        user.id = incID()
        users.add(user)
    }

    fun allUsers(): List<User> {
        return users
    }
    fun addNew(user : User){
        users.add(user)
    }

    fun findUser(id: Int): User?{
        return users.find { p -> p.id == id }
    }

    fun delUser(user: User?) : Boolean {
        return users.remove(user)
    }

    fun updateUser(user : User) : Boolean {
        val currentUser = findUser(user.id)
        if ( currentUser != null){
            currentUser.name = user.name
            currentUser.email = user.email
            currentUser.gender = user.gender
            currentUser.weight = user.weight
            currentUser.height = user.height
            return true
        }
        return false
    }

    fun filterUser(withGender : Char) : List<User>?{
        return users.filter { it.gender == withGender.uppercaseChar() }
    }

    fun genderBreak() :String {
        val maleCount = users.filter { it.gender == 'M' }.count()
        val femaleCount = users.filter { it.gender == 'F' }.count()
        val OtherCount = users.filter { it.gender == 'O'}.count()
        return "{M=${maleCount}, F=${femaleCount}, O=${OtherCount}}"
        }



    fun weightBreakDown() : weightMerger{
        val min = users.minBy { it.weight }.weight
        val max = users.maxBy { it.weight }.weight
        val avg = users.sumOf { it.weight }/users.count()
        return weightMerger(avg,min,max)
    }
    fun heightBreakDown() : heightMerger{
        val min = users.minBy { it.height }.height
        val max = users.maxBy { it.height }.height
        val avg = users.sumOf { it.height.toDouble() }/users.count()
        return heightMerger(avg.toFloat(),min,max)
    }
}
data class weightMerger(val avg : Double = 0.0,
                        val min : Double = 0.0,
                        val max : Double = 0.0)

data class heightMerger(val avg : Float = 0.0F,
                         val min : Float = 0.0F,
                         val max : Float = 0.0F)
