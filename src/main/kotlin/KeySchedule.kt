import kotlin.properties.Delegates

class KeySchedule<T> (var key : String){
    var scheduler : HashMap<String, ArrayList<T>> = HashMap()

    fun convert(){
        var hexList = swapForHexValue()
        arrangeAndPushToScheduler(hexList)
    }
    private fun swapForHexValue() : MutableList<String>{
        val subbedHexArray = mutableListOf<String>()
        for (char in key){
            val charArray = Integer.toHexString(char.toInt()).toCharArray()
            val newHex = hexSub(charArray[0].toString(), charArray[1].toString())
            subbedHexArray.add(newHex)
        }
        return subbedHexArray
    }

    private fun hexSub(row : String, column : String) : String{
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

    private fun arrangeAndPushToScheduler(hexList: List<String>){
        var tempList = mutableListOf<String>()
        for((index,hex) in hexList.withIndex()){
            tempList.add(hex)
            if(index ==)
        }
    }
}