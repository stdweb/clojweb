(defproject bitexplorer "0.1.0"
  :description "Example Compojure project"
  :dependencies [
                 [org.clojure/clojure "1.6.0"]
                 [compojure "1.1.1"]
                 [hiccup "1.0.0"]
                 [clj-http "LATEST"] 
                 ]
  :plugins [[lein-ring "0.7.1"] [lein2-eclipse "2.0.0"]]
  :ring {:handler bitexplorer.routes/app}
)