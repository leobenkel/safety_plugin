package com.leobenkel.safetyplugin.Utils

import com.leobenkel.safetyplugin.Modules.Dependency
import com.leobenkel.safetyplugin.ParentTest
import sbt._
import sbt.util.Level

class ImplicitModuleToStringTest extends ParentTest {
  import ImplicitModuleToString._

  test("Test ModuleID to prettyString") {
    val org = "com.org"
    val name = "artifact-name"
    val revision = "0.1"
    val m: ModuleID = org % name % revision

    val prettyString = m.prettyString

    assert(prettyString.contains(org))
    assert(prettyString.contains(name))
    assert(prettyString.contains(revision))
    assert(prettyString.contains("%"))
  }

  test("Test Seq[ModuleID] to prettyString") {
    val org = "com.org"
    val name = "artifact-name"
    val revisions = (1 until 5).map(v => s"0.$v")
    val m: Seq[ModuleID] = revisions.map(r => org % name % r)
    val header = "this is a header"
    m.prettyString(
      new LoggerExtended() {
        override def fail(message: String): Unit = ()

        override def isSoftError: Boolean = true

        override def setSoftError(softError: Boolean): LoggerExtended = this

        override def separatorInfo(title: String): Unit = ()

        override def separatorDebug(title: String): Unit = ()

        override def separator(
          level: Level.Value,
          title: String
        ): Unit = {
          ()
        }

        override def setLevel(level: Level.Value): LoggerExtended = this

        override def trace(t: => Throwable): Unit = ()

        override def success(message: => String): Unit = ()

        override def log(
          level:        Level.Value,
          prettyString: => String
        ): Unit = {
          assertEquals(Level.Debug, level)
          assert(prettyString.contains(header))
          assert(prettyString.contains(org))
          assert(prettyString.contains(name))
          assert(revisions.exists(prettyString.contains))
        }
      },
      header
    )
  }

  test("Test Seq[Dependency] to prettyString") {
    val org = "com.org"
    val name = "artifact-name"
    val revisions = (1 until 5).map(v => s"0.$v")
    val m: Seq[Dependency] = revisions.map(r => Dependency(org % name % r))
    val header = "this is a header"
    m.prettyString(
      new LoggerExtended() {
        override def fail(message: String): Unit = ()

        override def isSoftError: Boolean = true

        override def setSoftError(softError: Boolean): LoggerExtended = this

        override def separatorInfo(title: String): Unit = ()

        override def separatorDebug(title: String): Unit = ()

        override def separator(
          level: Level.Value,
          title: String
        ): Unit = {
          ()
        }

        override def setLevel(level: Level.Value): LoggerExtended = this

        override def trace(t: => Throwable): Unit = ()

        override def success(message: => String): Unit = ()

        override def log(
          level:        Level.Value,
          prettyString: => String
        ): Unit = {
          assertEquals(Level.Debug, level)
          assert(prettyString.contains(header))
          assert(prettyString.contains(org))
          assert(prettyString.contains(name))
          assert(revisions.exists(prettyString.contains))
        }
      },
      header
    )
  }
}