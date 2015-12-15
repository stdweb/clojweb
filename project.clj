(defproject bitexplorer "0.1.0"
  :description "Example Compojure project"
  :dependencies [
                 [org.clojure/clojure "1.6.0"]
                 [compojure "1.1.1"]
                 [hiccup "1.0.0"]
                 [clj-http "LATEST"] 
                 [ring.middleware.logger "0.5.0"]
                 [org.clojure/data.json "0.2.6"]
;                 [org.clojure/tools.logging "0.3.1"]
;                 [log4j/log4j "1.2.17" :exclusions [javax.mail/mail
;                                                 javax.jms/jms
;                                                 com.sun.jmdk/jmxtools
;                                                 com.sun.jmx/jmxri]]
                 ]
  :plugins [[lein-ring "0.7.1"] [lein2-eclipse "2.0.0"]]
  :ring {:handler bitexplorer.routes/app}
)