// Import Spark and GraphX
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.graphx.{Graph, Edge, VertexId}
import org.apache.spark.rdd.RDD

object DijkstraGraphX {

  def main(args: Array[String]): Unit = {
    // Validate input
    if (args.length != 2) {
      println("Usage: DijkstraGraphX <input_file> <source_vertex>")
      sys.exit(1)
    }

    val inputPath = args(0)
    val sourceId = args(1).toLong

    // Set up Spark context
    val conf = new SparkConf().setAppName("Dijkstra with GraphX")
    val sc = new SparkContext(conf)

    // Read and parse input
    val lines = sc.textFile(inputPath)
    val header = lines.first()
    val edges = lines
      .filter(_ != header)
      .map { line =>
        val parts = line.split(" ")
        Edge(parts(0).toLong, parts(1).toLong, parts(2).toInt)
      }

    val vertices: RDD[(VertexId, Double)] = edges
      .flatMap(e => Seq((e.srcId, Double.PositiveInfinity), (e.dstId, Double.PositiveInfinity)))
      .distinct()

    // Build the graph
    val graph = Graph(vertices, edges)

    // Initialize the graph: source distance = 0, others = INF
    val initialGraph = graph.mapVertices((id, _) => if (id == sourceId) 0.0 else Double.PositiveInfinity)

    // Define Pregel algorithm
    val sssp = initialGraph.pregel(Double.PositiveInfinity)(
      (id, dist, newDist) => math.min(dist, newDist),
      triplet => {
        if (triplet.srcAttr + triplet.attr < triplet.dstAttr) {
          Iterator((triplet.dstId, triplet.srcAttr + triplet.attr))
        } else {
          Iterator.empty
        }
      },
      (a, b) => math.min(a, b)
    )

    // Collect and print results
    val results = sssp.vertices.collect()
    println(s"Shortest distances from node $sourceId:")
    results.foreach { case (vertexId, distance) =>
      if (distance == Double.PositiveInfinity)
        println(s"Node $vertexId: INF")
      else
        println(f"Node $vertexId: ${distance.toInt}")
    }

    sc.stop()
  }
}