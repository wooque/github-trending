(defproject github-trending "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [enlive "1.1.6"]]
  :main ^:skip-aot github-trending.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
