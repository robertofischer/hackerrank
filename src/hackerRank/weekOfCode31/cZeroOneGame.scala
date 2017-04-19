package hackerRank.weekOfCode31

import java.io.{ByteArrayInputStream, IOException, PrintWriter}
import java.util
import java.util.InputMismatchException

import scala.collection.generic.{CanBuildFrom, GenericCompanion}
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.language.higherKinds

/**
  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  * THE SOFTWARE.
  *
  * @author A. Roberto Fischer <a.robertofischer@gmail.com> on 4/17/2017
  */
object cZeroOneGame {
  private val INPUT = ""

  def solve(): Unit = {
    val g = nextInt()
    var a0 = 0
    while (a0 < g) {
      val n = nextInt()
      val builder = ArrayBuffer.empty[Int]
      for (_ <- 0 until n) {
        builder += nextInt()
      }
      // If Alice wins, print 'Alice' on a new line; otherwise, print 'Bob'
      out.println(solve(builder))
      a0 += 1
    }
  }

  //  def isWin(game: String) = {
  //    if (game.length % 2 == 0) 0 else 1
  //  }
  //
  //  def solve(str: String) = {
  //    val games = str.split("11+").filter(_.nonEmpty)
  //    val result = games.view.map { game =>
  //      var result = game
  //      if (game.head == '1') result = result.drop(1)
  //      if (game.last == '1') result = result.dropRight(1)
  //      result
  //    }.filter(_.length > 2).map(isWin).fold(0)(_ ^ _)
  //    if (result == 0) "Bob" else "Alice"
  //  }

  def solve(input: mutable.Buffer[Int]): String = {
    if (input.length < 3) return "Bob"
    input.append(1, 1, 1)
    input.prepend(1, 1, 1)
    for (i <- 3 until input.length - 3) {
      if (input(i) == 1 && input(i - 1) == 0 && input(i + 1) == 0) {
        input(i) = 0
      }
      if (input(i) == 0 && input(i - 1) == 1 && input(i + 1) == 1 && input(i + 2) == 1
        && input(i - 2) == 1) {
        input(i) = 1
      }
      if (input(i) == 0 && input(i - 1) == 1 && input(i + 1) == 0 && input(i + 2) == 1
        && input(i - 2) == 1 && input(i + 3) == 1) {
        input(i) = 1
      }
      if (input(i) == 0 && input(i - 1) == 0 && input(i + 1) == 1 && input(i + 2) == 1
        && input(i - 2) == 1) {
        input(i) = 1
      }
    }
    if (input.count(_ == 0) % 2 == 0) "Bob" else "Alice"
  }

  //------------------------------------------------------------------------------------------//
  // Input-Output                                                                 
  //------------------------------------------------------------------------------------------//
  var in: java.io.InputStream = _
  var out: java.io.PrintWriter = _

  @throws[Exception]
  def main(args: Array[String]): Unit = {
    run()
  }

  @throws[Exception]
  def run(): Unit = {
    in = if (INPUT.isEmpty) System.in else new ByteArrayInputStream(INPUT.getBytes)
    out = new PrintWriter(System.out)

    val s = System.currentTimeMillis
    solve()
    out.flush()
    if (!INPUT.isEmpty) print(System.currentTimeMillis - s + "ms")
  }

  private val inputBuffer = new Array[Byte](1024)
  var lenBuffer = 0
  var ptrBuffer = 0

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

  private def isSpaceChar(c: Int) = !(c >= 33 && c <= 126)

  private def skip = {
    var b = 0
    while ( {
      b = readByte()
      b != -1 && isSpaceChar(b)
    }) {}
    b
  }

  private def nextDouble: Double = nextString.toDouble

  private def nextChar: Char = skip.toChar

  private def nextString: String = {
    var b = skip
    val sb = new java.lang.StringBuilder
    while (!isSpaceChar(b)) { // when nextLine, (isSpaceChar(b) && b != ' ')
      sb.appendCodePoint(b)
      b = readByte()
    }
    sb.toString
  }

  private def nextString(n: Int): Array[Char] = {
    val buf = new Array[Char](n)
    var b = skip
    var p = 0
    while (p < n && !isSpaceChar(b)) {
      buf({
        p += 1
        p - 1
      }) = b.toChar
      b = readByte()
    }
    if (n == p) buf else util.Arrays.copyOf(buf, p)
  }

  private def nextMultiLine(n: Int, m: Int): Array[Array[Char]] = {
    val map = new Array[Array[Char]](n)
    var i = 0
    while (i < n) {
      map(i) = nextString(m)
      i += 1
    }
    map
  }

  private def nextIntArray[Coll](n: Int): Array[Int] = {
    val a = new Array[Int](n)
    for (i <- 0 until n) {
      a(i) = nextInt()
    }
    a
  }

  private def nextLongArray(n: Int): Array[Long] = {
    val a = new Array[Long](n)
    for (i <- 0 until n) {
      a(i) = nextLong()
    }
    a
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
    throw new IOException("Read Long")
  }

  private def print(o: AnyRef*): Unit = {
    System.out.println(java.util.Arrays.deepToString(o.toArray)
    )
  }
}