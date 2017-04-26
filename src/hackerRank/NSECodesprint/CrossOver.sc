import scala.io.Source

def shortTermMovingAvg(prices: Array[Int]) = {
  prices.sliding(60).map(_.sum / 60.0)
}

def longTermMovingAvg(prices: Array[Int]) = {
  prices.sliding(300).map(_.sum / 300.0)
}

def combine(shortTermMovingAvg: Iterator[Double], longTermMovingAvg: Iterator[Double]) = {
  shortTermMovingAvg.drop(240).zip(longTermMovingAvg).zipWithIndex.map(pair =>
    (pair._2 + 300, pair._1))
}

def isCrossOver(previous: (Double, Double), current: (Double, Double)) = {
  val (previousShortTerm, previousLongTerm) = previous
  val (currentShortTerm, currentLongTerm) = current
  if (previousShortTerm > previousLongTerm && currentShortTerm <= currentLongTerm) {
    true
  } else if (previousShortTerm < previousLongTerm && currentShortTerm >= currentLongTerm) {
    true
  } else if (previousShortTerm == previousLongTerm && currentShortTerm != currentLongTerm) {
    true
  } else {
    false
  }
}

def round(input: Double): Double = {
  Math.round(input * 100.0) / 100.0
}

def crossOver(shortTermMovingAvg: Iterator[Double], longTermMovingAvg: Iterator[Double]) = {
  val combined = combine(shortTermMovingAvg, longTermMovingAvg)

  combined.sliding(2).map(pair => (pair.head, pair.last)).filter {
    case (previous, current) =>
      isCrossOver(previous._2, current._2)
  }.map(_._2)
}

def solve(prices: Array[Int]) = {
  crossOver(shortTermMovingAvg(prices), longTermMovingAvg(prices)).map(pair =>
    (pair._1, f"${round(pair._2._1)}%1.2f", f"${round(pair._2._2)}%1.2f"))
}

def main(args: Array[String]) {
  val sc = new java.util.Scanner(System.in)
  val n = sc.nextInt()
  val p = new Array[Int](n)
  for (p_i <- 0 until n) {
    p(p_i) = sc.nextInt()
  }
  println(solve(p).map(_.productIterator.mkString(" ")).mkString("\n"))
}


def aa (s:String) ={

}

val input ="1000\n25495 25495 25495 25500 25500 25495 25495 25495 25495 25495 25500 25500 25500 25495 25500 25500 25495 25500 25500 25495 25500 25495 25495 25495 25500 25500 25500 25500 25500 25500 25495 25495 25500 25495 25495 25495 25500 25500 25500 25495 25490 25490 25490 25490 25495 25490 25490 25490 25490 25495 25490 25490 25490 25485 25470 25465 25465 25465 25475 25465 25465 25475 25485 25470 25470 25485 25485 25485 25475 25490 25485 25485 25490 25490 25490 25480 25475 25475 25475 25480 25480 25475 25475 25475 25475 25475 25470 25475 25475 25475 25480 25480 25480 25475 25480 25480 25480 25480 25475 25480 25470 25475 25470 25475 25475 25465 25465 25455 25455 25455 25450 25460 25465 25465 25450 25450 25460 25450 25445 25450 25440 25440 25435 25445 25445 25440 25440 25445 25445 25440 25440 25440 25440 25440 25445 25445 25440 25450 25440 25440 25465 25465 25440 25465 25465 25465 25460 25460 25460 25460 25450 25450 25450 25450 25460 25460 25450 25450 25460 25465 25465 25465 25465 25465 25460 25460 25460 25465 25465 25465 25475 25470 25475 25470 25475 25470 25470 25480 25480 25480 25475 25475 25485 25475 25475 25475 25475 25475 25475 25475 25470 25470 25470 25465 25465 25465 25470 25470 25470 25470 25475 25480 25480 25485 25480 25475 25475 25490 25480 25485 25480 25480 25480 25480 25480 25480 25480 25480 25485 25485 25485 25485 25485 25485 25480 25495 25480 25480 25490 25490 25490 25485 25485 25485 25480 25480 25490 25475 25475 25480 25480 25480 25480 25480 25480 25475 25475 25475 25475 25475 25465 25465 25465 25470 25470 25470 25470 25475 25485 25485 25475 25485 25485 25480 25480 25490 25490 25490 25490 25490 25480 25480 25490 25485 25480 25480 25490 25495 25495 25495 25480 25480 25480 25480 25495 25480 25500 25495 25495 25500 25495 25495 25495 25500 25495 25490 25495 25500 25500 25485 25485 25500 25500 25505 25500 25500 25500 25505 25505 25505 25505 25505 25505 25505 25505 25505 25510 25510 25510 25510 25515 25515 25515 25515 25515 25510 25515 25515 25515 25515 25510 25515 25515 25510 25515 25510 25515 25515 25515 25515 25510 25515 25515 25520 25520 25515 25515 25520 25520 25525 25520 25520 25520 25520 25525 25525 25520 25520 25520 25520 25520 25520 25525 25520 25525 25520 25520 25520 25520 25520 25520 25520 25520 25525 25525 25520 25520 25520 25520 25520 25520 25520 25520 25520 25520 25520 25515 25515 25515 25515 25515 25515 25515 25515 25515 25515 25515 25515 25515 25515 25515 25515 25515 25515 25520 25520 25520 25520 25525 25520 25520 25520 25525 25525 25525 25520 25520 25525 25525 25525 25520 25525 25525 25525 25525 25520 25520 25520 25520 25520 25520 25525 25525 25525 25530 25530 25525 25530 25525 25525 25525 25515 25515 25515 25520 25520 25520 25520 25520 25505 25505 25520 25520 25515 25515 25505 25505 25505 25505 25505 25505 25505 25505 25505 25505 25505 25505 25505 25505 25505 25505 25505 25505 25505 25505 25505 25505 25505 25505 25505 25505 25510 25510 25510 25510 25505 25505 25510 25510 25510 25505 25505 25505 25505 25505 25505 25505 25505 25505 25505 25510 25510 25505 25510 25505 25505 25510 25510 25505 25505 25510 25510 25510 25505 25505 25505 25505 25505 25505 25505 25505 25505 25505 25510 25510 25505 25505 25505 25505 25510 25505 25505 25505 25505 25510 25505 25505 25510 25510 25510 25505 25505 25505 25505 25505 25505 25505 25505 25510 25510 25505 25505 25505 25505 25510 25505 25510 25510 25510 25510 25505 25505 25505 25505 25510 25505 25505 25505 25505 25505 25505 25505 25505 25505 25505 25510 25505 25505 25510 25510 25505 25505 25505 25510 25510 25510 25505 25505 25505 25510 25510 25510 25505 25505 25505 25505 25505 25505 25505 25505 25510 25510 25510 25510 25505 25500 25500 25505 25505 25500 25500 25500 25500 25500 25500 25500 25500 25500 25500 25510 25505 25505 25505 25510 25510 25505 25505 25505 25505 25505 25500 25500 25500 25500 25500 25505 25505 25505 25510 25510 25510 25505 25510 25510 25510 25500 25510 25510 25505 25505 25500 25500 25505 25500 25500 25500 25505 25500 25500 25500 25500 25500 25500 25500 25500 25500 25500 25500 25505 25500 25500 25500 25500 25500 25500 25500 25500 25505 25505 25505 25500 25500 25500 25505 25505 25500 25500 25500 25500 25500 25500 25500 25500 25500 25485 25500 25500 25500 25500 25505 25505 25500 25500 25510 25510 25510 25510 25510 25510 25510 25510 25510 25510 25510 25510 25510 25510 25510 25510 25510 25510 25510 25510 25510 25515 25510 25510 25515 25515 25515 25510 25510 25510 25510 25505 25505 25505 25505 25505 25505 25505 25510 25510 25510 25510 25505 25505 25505 25505 25505 25505 25505 25505 25500 25505 25500 25500 25500 25505 25505 25500 25505 25505 25505 25505 25500 25500 25500 25500 25500 25505 25500 25505 25500 25500 25500 25505 25505 25505 25505 25500 25505 25505 25500 25500 25500 25500 25500 25505 25505 25500 25500 25500 25500 25500 25500 25500 25500 25500 25500 25505 25500 25505 25505 25505 25495 25495 25495 25495 25495 25495 25500 25500 25495 25495 25505 25505 25505 25505 25495 25495 25500 25500 25500 25500 25495 25495 25495 25500 25500 25495 25495 25495 25495 25495 25500 25500 25500 25500 25500 25495 25495 25495 25495 25495 25495 25495 25500 25500 25500 25505 25500 25505 25500 25505 25500 25500 25500 25500 25500 25500 25500 25500 25500 25505 25495 25495 25495 25495 25495 25490 25490 25490 25490 25500 25500 25490 25500 25500 25490 25490 25490 25490 25500 25500 25490 25500 25500 25500 25490 25490 25500 25500 25500 25490 25490 25490 25490 25485 25485 25485 25485 25485 25490 25490 25485 25485 25485 25485 25490 25485 25485 25485 25485 25485 25485 25485 25485 25485 25485 25485 25485 25490 25490 25485 25485 25485 25485 25485 25485 25480 25480 25480 25475 25475 25470 25470 25470 25470 25470 25465 25465 25460 25460 25460 25470 25460 25460 25460 25460 25460 25460 25460 25460 25460 25460 25460 25460 25460 25460 25460 25460 25460 25465 25450 25450 25450 25450 25450 25450 25450 25450 25450 25460 25460 25450 25450 25450 25455 25455 25455 25455 25455 25455 25465 25465 25465 25465 25465 25465 25465 25465 25465 25470 25470 25455 25455 25470 25470 25465"
