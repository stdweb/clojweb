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
             (list  ["Block hash" "hash_str"] 
                    ["Parent hash" "parent_str"] 
                    ["Time" "blockDateTime"] 
                    ["Uncle count" "unclesCount"]
                    ["Tx count" "txCount"]
                    ["Gas used" "gasUsed"]
                    ["difficulty"]
                    ["Block size" "blockSize"]
                    ["State root hash" "stateroot_str"]
                    ["Receipt root hash" "receiptroot_str"]
                    ["Tx trie root hash" "txroot_str"]
                    

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
  
  (let [data (get-block-data blockid)] 
    (htmlpage 
     [:body 
      [:h2 "Block# " (data "id")]
      ;map2htmltable 
      (blockHead-htmltable  data {:class "blockheader"})
     ; [:hr]
      (create-tx-table blockid {:class "blockdetails"})
      ]))
  )