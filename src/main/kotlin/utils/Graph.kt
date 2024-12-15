package utils

data class Graph<T>(
    val vertices: MutableSet<T> = mutableSetOf(),
    val edges: MutableMap<T, MutableSet<T>> = mutableMapOf(),
    val weights: MutableMap<Pair<T, T>, Int> = mutableMapOf(),
) {
    fun addEdge(first: T, second: T, wage: Int = 1, directed: Boolean = true) {
        if (vertices.add(first)) {
            edges[first] = mutableSetOf()
        }
        if (vertices.add(second)) {
            edges[second] = mutableSetOf()
        }
        edges[first]!!.add(second)
        weights[first to second] = wage
        if (!directed) {
            edges[second]!!.add(first)
            weights[second to first] = wage
        }
    }

    companion object {
        fun <T> from(edges: List<Pair<T, T>>): Graph<T> {
            val graph = Graph<T>()
            edges.forEach { (first, second) ->
                graph.addEdge(first, second)
            }
            return graph
        }
    }
}
