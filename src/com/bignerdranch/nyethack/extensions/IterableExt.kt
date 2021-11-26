package extensions

fun <T> Iterable<T>.randooom():T = this.shuffled().first()
