package ipca.example.shoppinglist.models

class Item(
    var docId: String?,
    var name: String?,
    var qtd: Double?,
    var price: Double?,
    var checked: Boolean = false
) {
    constructor() : this(null, null, null, null, false)
}
