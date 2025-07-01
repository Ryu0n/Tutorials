package org.example

//TIP 코드를 <b>실행</b>하려면 <shortcut actionId="Run"/>을(를) 누르거나
// 에디터 여백에 있는 <icon src="AllIcons.Actions.Execute"/> 아이콘을 클릭하세요.
fun maxOf(a: Int, b: Int): Int {
    if (a > b) {
        return a
    } else {
        return b
    }
}
// fun maxOf(a: Int, b: Int) = if (a > b) a else b


fun describe(obj: Any): String {
    val result = when (obj) {
        is Int -> "Int"
        is Long -> "Long"
        !is String -> "Not a String"
        else -> "Unknown type"
    }
    return result
}

fun describe_direct(obj: Any): String = when (obj) {
    is Int -> "Int"
    is Long -> "Long"
    !is String -> "Not a String"
    else -> "Unknown type"
}

//fun sampleFunction(val a: Int) {
//    //TIP <shortcut actionId="ShowIntentionActions"/>을(를) 눌러 이 함수에 대한 제안을 확인하세요.
//    // 이 함수는 a 매개변수를 사용하지 않습니다.
//    println("This is a sample function with parameter a: $a")
//}

fun main() {
    var name = "Kotlin"
    name = "World"
    val locked_variable = "This variable cannot be changed"
    //TIP 캐럿을 강조 표시된 텍스트에 놓고 <shortcut actionId="ShowIntentionActions"/>을(를) 누르면
    // IntelliJ IDEA이(가) 수정을 제안하는 것을 확인할 수 있습니다.
    println("Hello, " + name + "!")
    println(locked_variable)

    for (i in 1..5) {
        //TIP <shortcut actionId="Debug"/>을(를) 눌러 코드 디버그를 시작하세요. 1개의 <icon src="AllIcons.Debugger.Db_set_breakpoint"/> 중단점을 설정해 드렸습니다
        // 언제든 <shortcut actionId="ToggleLineBreakpoint"/>을(를) 눌러 중단점을 더 추가할 수 있습니다.
        println("i = $i")
    }

    printSum(a = 1, b = 2)

    val r = Rectangle(height = 1.0, length = 2.0)
    println("Perimeter of rectangle is ${r.perimeter}")

    // String templates
    var a = 1
    var s1 = "a is $a"
    println(s1)

    a = 2
    val s2 = "${s1.replace("is", "was")} but now is $a"
    println(s2)

    // Function call
    val m1 = maxOf(a=1, b=2)
    val m2 = maxOf(a=2, b=1)
    println("m1 : ${m1}, m2 : ${m2}")

    // For loops
    val immutableItems = listOf("apple", "banana", "kiwi")
    for (item in immutableItems) {
        println(item)
    }

    for (index in immutableItems.indices) {
        println("Index : $index, Item : ${immutableItems[index]}")
    }

    // While loops
    var currentIndex = 0
    while (currentIndex < immutableItems.size) {
        println(immutableItems[currentIndex])
        currentIndex++
    }

    // When expression
    val x = 2
    when (x) {
        1 -> println("x is 1")
        2 -> println("x is 2")
        else -> println("x is neither 1 nor 2")
    }

    val y = "Kotlin"
    when (y) {
        "Kotlin" -> println("This fine-grained language is $y")
        "Java" -> println("This old language is $y")
    }

    println(describe("Hello"))
    println(describe_direct("World"))

    for (i in 1..10 step 3) {
        println("Step: $i")
    }
    for (i in 10 downTo 0 step 2) {
        println("Down: $i")
    }

    val x1 = 10
    val y1 = 9
    if (x1 in 1..y1+1) {
        println("fits in range")
    }

    val list = listOf("a", "b", "c")
    if (-1 !in 0..list.lastIndex) {
        println("-1 is out of range")
    }

    val s = Square(side= 5.0)
    s.setMyName(name= "My Square")
    println("Square name: ${s.name}, area: ${s.area()}")

    awd@for (i in 0..10) {
        for (j in 0..10) {
            if (i == 5) {
                continue@awd
            }
            println("$i $j")
        }
    }

    var demo = InitDemo("Kotlin")

    val owner = Person()
    val pet1 = Pet(owner = owner)
    println("Owner has ${owner.pets.size} pets")

    val delegation1 = DelegationWithInit(name = "Kotlin")
    val delegation2 = DelegationWithInit(name = "Kotlin", age = 5)
    val delegation3 = DelegationWithInit(name = "Kotlin", age = 5, desc = "A programming language")

    val animal1 = Animal("Dog")
    animal1.bark(sound = null)
    animal1.bark(sound = "Woof Woof")
    animal1.bark(sound = null)

    val animal2 = Dog("Dog")
    animal2.bark(sound = null)
    animal2.bark(sound = "Bark Bark")
    animal2.bark(sound = null)

    val child = Child()
    child.foo()
    child.bar()
    println(child.propertyWithImplementation)
}