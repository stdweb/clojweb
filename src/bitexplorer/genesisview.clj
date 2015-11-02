(ns bitexplorer.genesisview
  (:use [hiccup core page] 
        bitexplorer.model 
        bitexplorer.html-utils
        bitexplorer.sharedview 
        bitexplorer.tx-view
        bitexplorer.utils
        bitexplorer.navigation
        )
  
  (:require [clj-http.client :as http])
  (:require [clojure.string :as string])
  )

(defn view []
  (htmlpage
    [:p "Genesis view"]
    )
  )

