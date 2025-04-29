README: Dijkstra's Algorithm with GraphX on Spark

Project Overview

This project implements Dijkstra's shortest path algorithm using Apache Spark GraphX. It computes the shortest distances from a given source node to all other nodes in a weighted graph.

Requirements

Apache Spark installed on Azure VMs

Scala 2.11+ (compatible with Spark)

sbt (Scala build tool)

Files

DijkstraGraphX.scala: Main source code.

weighted_graph.txt: Input graph file (edge list format).

Setup Instructions

Upload files to your Azure VM.

Compile the code:

sbt package

This will generate a JAR file under target/scala-2.11/.

Configure Spark Master and Worker:

Edit $SPARK_HOME/conf/spark-env.sh and add:

export SPARK_MASTER_HOST=<your-public-ip>
export SPARK_LOCAL_IP=0.0.0.0

Restart Spark services:

$SPARK_HOME/sbin/stop-master.sh
$SPARK_HOME/sbin/stop-worker.sh
$SPARK_HOME/sbin/start-master.sh
$SPARK_HOME/sbin/start-worker.sh spark://<your-public-ip>:7077

Submit the job using Spark:

spark-submit --class DijkstraGraphX --master spark://<your-public-ip>:7077 target/scala-2.11/dijkstra_2.11-0.1.jar weighted_graph.txt 0

Replace <your-public-ip> and 0 (source node) as needed.

Output Format

The program prints:

Shortest distances from node 0:
Node 0: 0
Node 1: 7
Node 2: 3
Node 3: 10
Node 4: 7
...

For unreachable nodes, it will display INF.# Dijkstra-Shortest-Path
