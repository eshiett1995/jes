import kotlin.properties.Delegates

class KeySchedule<T> (var key : String){
    private var scheduler : HashMap<Int, ArrayList<ArrayList<String>>> = HashMap()

    fun convert(){
        val hexList = swapForHexValue()
        val firstSchedule = arrangeToColumnAndPushToScheduler(hexList)
        scheduler[1] = firstSchedule
        makeNextSchedule()
    }

    private fun makeNextSchedule(){
        //scheduler.keys.
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

    private fun arrangeToColumnAndPushToScheduler(hexList: List<String>) : ArrayList<ArrayList<String>>{
        val finalList = ArrayList<ArrayList<String>>()
        val rowOneList = ArrayList<String>()
        val rowTwoList = ArrayList<String>()
        val rowThreeList = ArrayList<String>()
        val rowFourList = ArrayList<String>()
        for((index,hex) in hexList.withIndex()){

            if(index == 0 || index == 4 || index == 8 || index == 12){
                rowOneList.add(hex)
            }
            if(index == 1 || index == 5 || index == 9 || index == 13){
                rowTwoList.add(hex)
            }
            if(index == 2 || index == 6 || index == 10 || index == 14){
                rowThreeList.add(hex)
            }
            if(index == 3 || index == 7 || index == 11 || index == 15){
                rowFourList.add(hex)
            }
        }

        finalList.add(rowOneList)
        finalList.add(rowTwoList)
        finalList.add(rowThreeList)
        finalList.add(rowFourList)
        return finalList
    }
}