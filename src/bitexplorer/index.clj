(ns bitexplorer.index
  (:use [hiccup core page] 
        bitexplorer.model 
        bitexplorer.utils
        bitexplorer.html-utils
        bitexplorer.sharedview [hiccup form ]
        bitexplorer.navigation
        )
  
  (:require [clj-http.client :as http])
  (:require [clojure.string :as string])
  )





;(def style {:style "border: 1px solid black"})

(defn get-blocklist-item-href [item-data]
  (let [
        hash     ( item-data "hash_str")
        coinbase (or  (item-data "coinbase_str") "0000000000")
        height   ( item-data "height")
        reward-str (str  (item-data "reward"))
        fee-str (str  (item-data "fee"))
        
        hash-href (hash2href "block" hash  )
        coinbase-href  [:a {:href (str "/account/" coinbase)} (str (subs coinbase 0 10) "...")]
        
        height-href    [:a {:href (str "/block/" height)} height]
        
        gas-used (if (= (item-data "gasUsed") 0) "" (item-data "gasUsed") )
        tx-count (if (= (item-data "txCount") 0) "" (item-data "txCount") )
        ]
     (assoc item-data 
            "hash_str" hash-href 
            "coinbase" coinbase-href 
            "height"   height-href
            "gasUsed" gas-used
            "txCount" tx-count
            );"reward" reward-str "fee" fee-str
     ;item-data
    ))

(defn get-blocklist-item-html [item-data]
  (let [data (get-blocklist-item-href item-data)
        ]
  ;  [:tr (map-tag :td (select-values data [ "height" "hash" "difficulty" "coinbase"  "timestamp" "tx#" "gasused" "reward" "txfee"]))]
    [:tr
     [:td (data "id")]
     [:td (data "hash_str")]
     ;[:td (data "difficulty")]
     [:td (data "coinbase")]
     [:td (data "blockDateTime")]
     [:td (data "txCount")]
     [:td (data "gasUsed")]
     [:td (data "blockSize")]
     [:td (td-style data "floor") (floor (data "reward_str"))]
     [:td (td-style data "fract") (fract (data "reward_str"))]
     
     [:td (td-style data "floor") (floor (data "fee_str"))]
     [:td (td-style data "fract") (fract (data "fee_str"))]
     ]
    
    )  )



(defn create-block-table [topblockid]
  (list
   ;{:class "th-colspan" :colspan "2"}
   [:div.blocklist  [:table 
                                          ;[:tr  (map-tag :th [ "Height" "Hash" "Difficulty" "Miner" "GasUsed" "Time" "# Tx" "Reward"])]
                                          [:tr [:th "Height"] [:th "Hash"] 
                                           ;[:th "Difficulty"] 
                                           [:th "Miner"]  [:th {:class "time"}  "Time"] [:th "# Tx"]
                                           [:th "GasUsed"]
                                           [:th "Size"]
                                           [:th {:class "th-colspan" :colspan "2"} "Reward"] [:th {:class "th-colspan" :colspan "2"} "TxFee"]]
                                          (map #(get-blocklist-item-html %) (get-blocklist-data topblockid))    
                                          ]]))

;(defn navig-form [topblockid]
;[:div {:id "navigate-block"  :style "float:left"}
; (form-to  {:enctype "multipart/form-data"} [:post "/goto"]
;        (label "top-block" "Search Block")
;        ;(text-field :blockid (get-bestblock))
;        (text-field :blockid topblockid)
;        (submit-button {:name "btngotoblock" :value "Go to" :class "btn"} "Goto")
;        (submit-button {:name "btngotoblock" :value "Top" :class "btn" } "Goto")
;        (submit-button {:name "btngotoblock" :value " < " :class "btn" } "Goto")
;        (submit-button {:name "btngotoblock" :value " > " :class "btn" } "Goto")
;        )]
;
;)


(defn blocklist [topblockid]
  (htmlpage
   (list [:head (navig-form topblockid)] [:br " "]
    [:body
      (create-block-table topblockid)
      ]))
  )


(defn index-page []
  (blocklist)
  )

;(defn txlist []
;  (htmlpage
;   
;    [:body
;     
;      [:h2 "transaction list table!"]]
;    
;    ))
