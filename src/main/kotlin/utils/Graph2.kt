package utils

data class Graph2<T>(
    val vertices: MutableSet<T> = mutableSetOf(),
    val edges: MutableMap<T, MutableSet<T>> = mutableMapOf(),
    val weights: MutableMap<Pair<T, T>, Int> = mutableMapOf(),
) {
    fun addEdge(first: T, second: T, wage: Int) {
        if (vertices.add(first)) {
            edges[first] = mutableSetOf()
        }

        if (vertices.add(second)) {
            edges[second] = mutableSetOf()
        }
        edges[first]!!.add(second)
        //edges[second]!!.add(first)
        weights[first to second] = wage
        //weights[second to first] = wage
    }

    fun addEdgeIfNotNull(first: T?, second: T?, wage: Int = 1) {
        first?.let {
            second?.let {
                addEdge(first, second, wage)
            }
        }
    }

    fun dijkstra(start: T): Map<T, T?> {
        vertices.forEach { weights[it to it] = 0 }
        val visitedVertices: MutableSet<T> = mutableSetOf()

        val delta = vertices.associateWith { Int.MAX_VALUE }.toMutableMap()
        delta[start] = 0

        val previous: MutableMap<T, T?> = vertices.associateWith { null }.toMutableMap()

        while (visitedVertices != vertices) {
            val v: T = delta
                .filter { !visitedVertices.contains(it.key) }
                .minBy { it.value }
                .key

            edges.getValue(v).minus(visitedVertices).forEach { neighbor ->
                val newPath = delta.getValue(v) + weights.getValue(v to neighbor)

                if (newPath < delta.getValue(neighbor)) {
                    delta[neighbor] = newPath
                    previous[neighbor] = v
                }
            }

            visitedVertices.add(v)
        }

        return previous.toMap()
    }

    fun distances(start: T): Map<T, Int> {
        val distance = mutableMapOf<T, Int>()
        val visited = mutableSetOf<T>()

        vertices.forEach { vertex -> distance[vertex] = Int.MAX_VALUE }
        distance[start] = 0

        while (true) {
            val current = distance.filter { !visited.contains(it.key) }.minByOrNull { it.value }?.key ?: break

            visited.add(current)
            edges[current]?.forEach { neighbor ->
                val newDist = distance[current]!! + weights[Pair(current, neighbor)]!!
                if (newDist < distance[neighbor]!!) {
                    distance[neighbor] = newDist
                }
            }
        }

        return distance
    }

    fun shortestPath(start: T, end: T): List<T> {
        val shortestPaths = dijkstra(start)

        val pathList = mutableListOf<T>()
        var current = end

        while (shortestPaths[current] != null) {
            pathList.add(current)
            current = shortestPaths[current]!!
        }

        pathList.add(start)
        return pathList.reversed()
    }
}
