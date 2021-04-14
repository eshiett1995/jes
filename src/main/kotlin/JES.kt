import kotlin.properties.Delegates

class JES(var data : String, var key : String) {

    fun encrypt() : String{
       var subbedHexArray = mutableListOf<String>()
       for (char in data){
           val charArray = Integer.toHexString(char.toInt()).toCharArray()
           val newHex = hexSub(charArray[0].toString(), charArray[1].toString())
           subbedHexArray.add(newHex)
           println("${Integer.toHexString(char.toInt())} - $newHex")

       }
       return ""
    }

    fun decrypt() : String{
        return ""
    }

    fun hexSub(row : String, column : String) : String{
        var c = data;
        val indexOfColumn = Constants.hexMatrix[0].indexOf(column)
        var indexOfRow by Delegates.notNull<Int>()
        for ((index,array) in Constants.hexMatrix.withIndex()){
             if(array[0] == row){
                 indexOfRow = index
                 break
             }else{
                 continue
             }
        }
        return Constants.hexMatrix[indexOfRow][indexOfColumn]
    }
}