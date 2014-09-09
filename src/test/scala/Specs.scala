import org.specs2.Specification
import runner.Runner

class Specs extends Specification {

  implicit def checkTuple(x: String) = {
    val r = x.split( """\s*=>\s*""")
    new A(r(0).trim, r(1).trim)
  }

  override def is = s2"""
 This is a specification to check working of simplifier
 The regex should match
    a** => a*               ${"a** => a*".m}
    aa* => a+               ${"aa* => a+".m}
    a*a => a+               ${"a*a => a+".m}
    a*a* => a*              ${"a*a* => a*".m}
    aa+ => a{2,}            ${"aa+ => a{2,}".m}
    a+a => a{2,}            ${"a+a => a{2,}".m}
    a+a+ => a{2,}           ${"a+a+ => a{2,}".m}
    a*a+ => a+              ${"a*a+ => a+".m}
    a+a* => a+              ${"a+a* => a+ ".m}
    a | a* => a*            ${"a | a* => a*".m}
    a* | a => a*            ${"a* | a => a*".m}
    a+ | a => a+            ${"a+ | a => a+".m}
    a | a+ => a+            ${"a | a+ => a+".m}
    a?|a*=>a*               ${"a?|a*=>a*".m}
    a?|a+=>a+               ${"a?|a+=>a+".m}
    a?|a?=>a?               ${"a?|a?=>a?".m}
    a*|a*=>a*               ${"a*|a*=>a*".m}
    a*|a?=>a*               ${"a*|a?=>a*".m}
    a*|a?=>a*               ${"a*|a?=>a*".m}
    a+|a*=>a*               ${"a+|a*=>a*".m}
    a+|a?=>a+               ${"a+|a?=>a+".m}
    a+|a+=>a+               ${"a+|a+=>a+".m}
    ab|ac => a(b|c)         ${"ab|ac => a(b|c)".m}
    ac|bc => (a|b)c         ${"ac|bc => (a|b)c".m}
    (aa|a)* => a*           ${"(aa|a)* => a*".m}
    (a|b*)* => (a|b)*       ${"(a|b*)* => (a|b)*".m}
    (a*b*c*)* => (a|b|c)*   ${"(a*b*c*)* => (a|b|c)*".m}
    a|(b|c) => c|a|b        ${"a|(b|c) => c|a|b".m}
    a(bc)=>abc              ${"a(bc)=>abc".m}
    (ab)c=>abc              ${"(ab)c=>abc".m}
    a(ba)c=>abac            ${"a(ba)c=>abac".m}
    a|b|a => b|a            ${"a|b|a => b|a".m}
    a+a{4,} => a{5,}        ${"a+a{4,} => a{5,}".m}
    a{4,}a+ => a{5,}        ${"a{4,}a+ => a{5,}".m}
    a+a{4,5} => a{5,}       ${"a+a{4,5} => a{5,}".m}
    a{4,5}a+ => a{5,}       ${"a{4,5}a+ => a{5,}".m}
    a+a{4} => a{5,}         ${"a+a{4} => a{5,}".m}
    a{4}a+ => a{5,}         ${"a{4}a+ => a{5,}".m}
    a*a{4} => a{4,}         ${"a*a{4} => a{4,}".m}
    a{4}a* => a{4,}         ${"a{4}a* => a{4,}".m}
    a{0,}a* => a*           ${"a{0,}a* => a*".m}
    a{1,}a* => a+           ${"a{1,}a* => a+".m}
    a{0,}a+ => a+           ${"a{0,}a+ => a+".m}
    a*a{0,} => a*           ${"a*a{0,} => a*".m}
    a*a{1,} => a+           ${"a*a{1,} => a+".m}
    a+a{0,} => a+           ${"a+a{0,} => a+".m}
    a{0,0}a? => a?          ${"a{0,0}a? => a?".m}
    a{0,1}a? => a{0,2}      ${"a{0,1}a? => a{0,2}".m}
    a{2,5}a? => a{2,6}      ${"a{2,5}a? => a{2,6}".m}
    a?a{0,0} => a?          ${"a?a{0,0} => a?".m}
    a?a{0,1} => a{0,2}      ${"a?a{0,1} => a{0,2}".m}
    a?a{2,5} => a{2,6}      ${"a?a{2,5} => a{2,6}".m}
    a?a* => a*              ${"a?a* => a*".m}
    a*a? => a*              ${"a*a? => a*".m}
    a?a+ => a+              ${"a?a+ => a+".m}
    a+a? => a+              ${"a+a? => a+ ".m}
    a?a? => a{0,2}          ${"a?a? => a{0,2}".m}
    a?a{0,} => a*           ${"a?a{0,} => a*".m}
    a?a{1,} => a+           ${"a?a{1,} => a+".m}
    a?a{2,} => a{2,}        ${"a?a{2,} => a{2,}".m}
    a?a{0,6} => a{0,7}      ${"a?a{0,6} => a{0,7}".m}
    a{0,}a? => a*           ${"a{0,}a? => a*".m}
    a{1,}a? => a+           ${"a{1,}a? => a+".m}
    a{2,}a? => a{2,}        ${"a{2,}a? => a{2,}".m}
    a{0,6}a? => a{0,7}      ${"a{0,6}a? => a{0,7}".m}
    a{0,}a{5,} => a{5,}       ${"a{0,}a{5,} => a{5,}".m}
    a{0,}a{6,10} => a{6,10}   ${"a{0,}a{6,10} => a{6,}".m}
    a{5,}a{0,} => a{5,}       ${"a{5,}a{0,} => a{5,}".m}
    a{6,10}a{0,} => a{6,10}   ${"a{6,10}a{0,} => a{6,}".m}
    a{4,7}a{2,5} => a{6,12}   ${"a{4,7}a{2,5} => a{6,12}".m}
    a{3,4}|a{1,5} => a{1,5}   ${"a{3,4}|a{1,5} => a{1,5}".m}
    a{1,5}|a{3,4} => a{1,5}   ${"a{1,5}|a{3,4} => a{1,5}".m}
    a{1,5}|a{1,5} => a{1,5}   ${"a{1,5}|a{1,5} => a{1,5}".m}
    a{1,5}|a* => a*           ${"a{1,5}|a* => a*".m}
    a*|a{3,4} => a*           ${"a*|a{3,4} => a*".m}
    a{1,5}|a+ => a+           ${"a{1,5}|a+ => a+".m}
    a+|a{1,} => a+            ${"a+|a{1,} => a+".m}
                            """

  class A(left: String, right: String) {
    def m = Runner.simple(left).toRegex must beEqualTo(right)
  }

}