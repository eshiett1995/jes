class ArrayShifter<T> {
    fun shiftArray(array: Array<T>) : Array<T>{
        val temp = array[0]
        for ((index,value) in array.withIndex()){
            if(index != array.size.minus(1)){
                array[index] = array[index + 1]
            }else{
                array[index] = temp
            }
        }
        print(array.contentToString())
        return array
    }

    fun shiftArray(array: ArrayList<T>) : ArrayList<T>{
        val temp = arrayListOf<String>()
        for ((index,value) in array.withIndex()){
            if(index != array.size.minus(1)){
                array[index] = array[index + 1]
            }else{
                array[index] = temp
            }
        }
        return array
    }
}