(ns bitexplorer.blockview
  (:use [hiccup core page] 
        bitexplorer.model 
        bitexplorer.html-utils
        bitexplorer.sharedview 
        bitexplorer.txlist-view)
  
  (:require [clj-http.client :as http])
  (:require [clojure.string :as string])
  
  )



(defn blockHead-htmltable [m attribs]

  (into [:table attribs ] 
        (map #( into [] [:tr [:td {:class "blockheader-key"} (first %)] [:td {:class "blockheader-val"} (m (last %))]])
             (list  ["Block hash" "hash"] 
                    ["Parent hash" "parenthash"] 
                    ["Time" "timestamp"] 
                    ["Uncle count" "uncles"]
                    ["Tx count" "txcount"]
                    ["Gas used" "gasused"]
                    ["difficulty"]
                    ["State root hash" "stateroot"]
                    ["Receipt root hash" "receiptroot"]
                    ["Tx trie root hash" "txtrieroot"]
                    

                    ))))
;  (into [:table attribs
;         ;[:tr  [:th  { :colspan "2"} "Block info" ]  ]
;         ]
;        ;(map #(block-header-row % ( m %)) (keys m))
;        
;       ;[:tr [:td {:class "blockheader-key"} "height"] [:td {:class "blockheader-val"} (m "height")]])
;  
;  )

(defn block-html [blockid] 
  
  (htmlpage 

     
    [:body 
     [:h2 "Block# " blockid]
     (
       ;map2htmltable 
       blockHead-htmltable
      (get-block-data blockid) {:class "blockheader"})
    ; [:hr]
     (create-tx-table blockid {:class "blockdetails"})
     ]
    
    
    
    ))