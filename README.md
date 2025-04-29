# Dijkstra's Algorithm Implementation with Apache Spark GraphX

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

A high-performance implementation of Dijkstra's shortest path algorithm using Apache Spark's GraphX framework, designed to process large-scale graph data efficiently in a distributed computing environment.

## Overview

This project demonstrates how to leverage Apache Spark's GraphX library to implement Dijkstra's algorithm for finding the shortest paths from a source node to all other nodes in a weighted graph. The implementation is optimized for parallel processing and can handle large graphs distributed across a cluster.

## Features

- **Distributed Processing**: Utilizes Spark's distributed computing capabilities
- **GraphX Integration**: Leverages Spark's graph processing library
- **Scalable Architecture**: Can handle graphs with millions of nodes and edges
- **Azure VM Deployment**: Ready for cloud deployment on Microsoft Azure

## Requirements

- Apache Spark 3.5.5+
- Scala 2.12+
- SBT (Scala Build Tool)
- Microsoft Azure VM (for cloud deployment)

## Input Format

The input graph should be in edge list format:
```
source_vertex destination_vertex weight
```

Example:
```
0 1 7
0 2 3
1 3 9
2 4 4
3 4 6
1 4 2
```

## Quick Start

### Local Development

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/dijkstra-graphx.git
   cd dijkstra-graphx
   ```

2. Build the project:
   ```bash
   sbt clean package
   ```

3. Run locally:
   ```bash
   spark-submit --class DijkstraGraphX --master local[*] \
       target/scala-2.12/dijkstragraphx_2.12-0.1.jar weighted_graph.txt 0
   ```

### Azure VM Deployment

1. Configure Spark on your Azure VM:
   ```bash
   # Edit Spark configuration
   echo "export SPARK_MASTER_HOST=<your-public-ip>" >> $SPARK_HOME/conf/spark-env.sh
   echo "export SPARK_LOCAL_IP=0.0.0.0" >> $SPARK_HOME/conf/spark-env.sh
   
   # Restart Spark services
   $SPARK_HOME/sbin/stop-all.sh
   $SPARK_HOME/sbin/start-master.sh
   $SPARK_HOME/sbin/start-worker.sh spark://<your-public-ip>:7077
   ```

2. Submit the job:
   ```bash
   spark-submit --class DijkstraGraphX --master spark://<your-public-ip>:7077 \
       target/scala-2.12/dijkstragraphx_2.12-0.1.jar weighted_graph.txt 0
   ```

## Output

The algorithm produces the shortest path distances from the source node to all other nodes:

```
Shortest distances from node 0:
Node 0: 0
Node 1: 7
Node 2: 3
Node 3: 10
Node 4: 7
...
```

For unreachable nodes, it will display "INF".

## Implementation Details

The implementation uses Spark's Pregel API (based on the Bulk Synchronous Parallel model) to efficiently propagate distance updates across the graph:

1. Initialize distances: source = 0, all others = ∞
2. In each superstep, vertices receive distance updates from their neighbors
3. If a shorter path is found, update distance and propagate to neighbors
4. Continue until no more updates occur

## Performance Considerations

- The algorithm's performance scales with the number of edges and vertices
- For very large graphs, increase the available memory for Spark executors
- Consider using a larger cluster for graphs with millions of nodes

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Apache Spark GraphX team for their excellent graph processing library
- Microsoft Azure for providing cloud infrastructure

## Contact

For questions or feedback, please open an issue on this repository or contact [your-email@example.com](mailto:your-email@example.com).
