package org.example

//TIP 코드를 <b>실행</b>하려면 <shortcut actionId="Run"/>을(를) 누르거나
// 에디터 여백에 있는 <icon src="AllIcons.Actions.Execute"/> 아이콘을 클릭하세요.
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
}