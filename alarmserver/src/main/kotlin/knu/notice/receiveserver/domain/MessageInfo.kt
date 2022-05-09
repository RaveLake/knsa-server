package knu.notice.receiveserver.domain

class MessageInfo() {
    var title: String = ""
    var body: String = ""
    var id: String = ""

    constructor(title: String, body: String, id: String) : this() {
        this.title = title
        this.body = body
        this.id = id
    }
}