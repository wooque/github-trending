(defproject github-trending "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [enlive "1.1.5"]
                 [com.draines/postal "1.11.2"]]
  :main ^:skip-aot github-trending.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
