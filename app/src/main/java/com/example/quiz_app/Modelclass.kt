package com.example.quiz_app

class Modelclass {
    var question: String? = null
    var one: String? = null
    var two: String? = null
    var three: String? = null
    var ans: String? = null

    constructor() {}

    constructor(question: String?, one: String?, two: String?, three: String?, ans: String?) {
        this.question = question
        this.one = one
        this.two = two
        this.three = three
        this.ans = ans
    }
}