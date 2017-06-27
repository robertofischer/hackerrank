package HackerRank.Training.NumberTheory

import java.io.{ByteArrayInputStream, IOException, PrintWriter}
import java.util.InputMismatchException

import scala.collection.generic.CanBuildFrom
import scala.collection.mutable
import scala.language.higherKinds
import scala.math.BigInt
import scala.util.control.TailCalls._

/**
  * Copyright (c) 2017 A. Roberto Fischer
  *
  * @author A. Roberto Fischer <a.robertofischer@gmail.com> on 6/25/2017
  */
private object FibonacciGCD {

  private val INPUT = ""

  //------------------------------------------------------------------------------------------//
  // Solution                                                                
  //------------------------------------------------------------------------------------------//
  private def solve(): Unit = {
    val fibonacciMatrix: Matrix = Matrix(Vector.tabulate(2, 2)((row, col) =>
      if (row == 1 && col == 1) 0 else 1
    ))

    val n = nextInt()
    //use that gcd(f_m, f_n) == f_gcd(m, n)
    println(
      fibonacciMatrix.power(
        exponent = next[BigInt, Set](BigInt(nextLong()), n).reduce { (a, b) => gcd(a, b) },
        mod = BigInt(1000000007)
      )(0)(1))
  }

  private final case class Matrix(matrix: Vector[Vector[BigInt]]) {
    val m: Int = matrix.size
    val n: Int = matrix.map(_.size).head

    lazy val identity = Matrix(Vector.tabulate(m, n)((row, col) =>
      if (row == col) 1 else 0
    ))

    def row(i: Int): Vector[BigInt] = matrix(i)

    def column(j: Int): Vector[BigInt] = matrix.map(_ (j))

    def mod(c: BigInt): Matrix = {
      Matrix(matrix.map(_.map(_ % c)))
    }

    def *(b: Matrix): Matrix = {
      Matrix(Vector.tabulate(m, b.n)((i, j) =>
        (this.row(i), b.column(j)).zipped.map(_ * _).sum))
    }

    def power(exponent: BigInt, mod: BigInt, a: Matrix = this): Matrix = {
      if (exponent == 0) {
        identity
      } else if (exponent % 2 == 1) {
        (a * power(exponent - 1, mod)) mod mod
      } else {
        val d = power(exponent / 2, mod)
        (d * d) mod mod
      }
    }

    def apply(i: Int)(j: Int): BigInt = {
      matrix(i)(j)
    }
  }

  private final object fibonacci extends Memo[BigInt, BigInt, (BigInt, BigInt)] {

    //  F(2n-1) = F(n)^2 + F(n-1)^2
    //  F(2n) = (2F(n-1) + F(n))*F(n)
    def _fibonacci(n: BigInt): TailRec[(BigInt, BigInt)] = {
      if (n == 0) {
        done(cache.getOrElseUpdate(n, (0, 1)))
      } else {
        val (a, b) = tailcall(_fibonacci(n / 2)).result
        val c = (2 * b - a) * a
        val d = a * a + b * b
        if (n % 2 == 0) {
          done(cache.getOrElseUpdate(n, (c, d)))
        } else {
          done(cache.getOrElseUpdate(n, (d, c + d)))
        }
      }
    }

    override def apply(v1: BigInt): BigInt = {
      _fibonacci(v1).result._1
    }
  }

  private final object gcd extends ((BigInt, BigInt) ==> BigInt) {

    def _gcd(a: BigInt, b: BigInt): TailRec[BigInt] = {
      if (b == 0) {
        done(cache.getOrElseUpdate((a, 0), a))
      } else {
        done(cache.getOrElseUpdate((b, a % b), tailcall(_gcd(b, a % b)).result))
      }
    }

    override def apply(v1: (BigInt, BigInt)): BigInt = {
      _gcd(v1._1, v1._2).result
    }
  }

  trait Memo[I, O, M] extends (I => O) {
    private type Input = I
    private type Output = O
    private type Memory = M
    val cache: mutable.Map[I, M] = mutable.Map.empty[I, M]

    override def apply(v1: I): O
  }

  type ==>[I, O] = Memo[I, O, O]


  //------------------------------------------------------------------------------------------//
  // Input-Output
  //------------------------------------------------------------------------------------------//
  private var in: java.io.InputStream = _
  private var out: java.io.PrintWriter = _

  private def println(x: Any) = out.println(x)

  @throws[Exception]
  def main(args: Array[String]): Unit = {
    run()
  }

  @throws[Exception]
  private def run(): Unit = {
    in = if (INPUT.isEmpty) System.in else new ByteArrayInputStream(INPUT.getBytes)
    out = new PrintWriter(System.out)

    val s = System.currentTimeMillis
    solve()
    out.flush()
    if (!INPUT.isEmpty) System.out.println(System.currentTimeMillis - s + "ms")
  }

  private def next[T, Coll[_]](reader: => T, n: Int)
                              (implicit cbf: CanBuildFrom[Coll[T], T, Coll[T]]): Coll[T] = {
    val builder = cbf()
    builder.sizeHint(n)
    for (_ <- 0 until n) {
      builder += reader
    }
    builder.result()
  }

  private def nextInt(): Int = {
    var num = 0
    var b = 0
    var minus = false
    while ( {
      b = readByte()
      b != -1 && !((b >= '0' && b <= '9') || b == '-')
    }) {}
    if (b == '-') {
      minus = true
      b = readByte()
    }
    while (true) {
      if (b >= '0' && b <= '9') {
        num = num * 10 + (b - '0')
      } else {
        if (minus) return -num else return num
      }
      b = readByte()
    }
    throw new IOException("Read Int")
  }

  private def nextLong(): Long = {
    var num = 0L
    var b = 0
    var minus = false
    while ( {
      b = readByte()
      b != -1 && !((b >= '0' && b <= '9') || b == '-')
    }) {}
    if (b == '-') {
      minus = true
      b = readByte()
    }
    while (true) {
      if (b >= '0' && b <= '9') {
        num = num * 10 + (b - '0')
      } else {
        if (minus) return -num else return num
      }
      b = readByte()
    }
    throw new IOException("Read Long")
  }

  private val inputBuffer = new Array[Byte](1024)
  private var lenBuffer = 0
  private var ptrBuffer = 0

  private def readByte(): Int = {
    if (lenBuffer == -1) throw new InputMismatchException
    if (ptrBuffer >= lenBuffer) {
      ptrBuffer = 0
      try {
        lenBuffer = in.read(inputBuffer)
      } catch {
        case _: IOException =>
          throw new InputMismatchException
      }
      if (lenBuffer <= 0) return -1
    }
    inputBuffer({
      ptrBuffer += 1
      ptrBuffer - 1
    })
  }

}