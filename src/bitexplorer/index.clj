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
        hash     ( item-data "hash")
        coinbase ( item-data "coinbase")
        height   ( item-data "height")
        
        ;hash-href      [:a {:href (str "/block/" hash)} (str (subs hash 0 10) "...")]
        hash-href (hash2href "block" hash  )
        coinbase-href  [:a {:href (str "/account/" coinbase)} (str (subs coinbase 0 10) "...")]
        ;coinbase-href  (hash2href "account" hash  )
        height-href    [:a {:href (str "/block/" height)} height]
        ]
     (assoc item-data "hash" hash-href "coinbase" coinbase-href "height" height-href)
    ))

(defn get-blocklist-item-html [item-data]
  (let [data (get-blocklist-item-href item-data)
        ]
  ;  [:tr (map-tag :td (select-values data [ "height" "hash" "difficulty" "coinbase"  "timestamp" "tx#" "gasused" "reward" "txfee"]))]
    [:tr
     [:td (data "height")]
     [:td (data "hash")]
     [:td (data "difficulty")]
     [:td (data "coinbase")]
     [:td (data "timestamp")]
     [:td (data "txcount")]
     [:td (data "gasused")]
     ;[:td (data "reward")]
     [:td (td-style data "floor") (floor (data "reward"))]
     [:td (td-style data "fract") (fract (data "reward"))]
     
     [:td (td-style data "floor") (floor (data "txfee"))]
     [:td (td-style data "fract") (fract (data "txfee"))]
     ]
    
    )  )



(defn create-block-table [topblockid]
  (list
   ;{:class "th-colspan" :colspan "2"}
   [:div.blocklist  [:table 
                                          ;[:tr  (map-tag :th [ "Height" "Hash" "Difficulty" "Miner" "GasUsed" "Time" "# Tx" "Reward"])]
                                          [:tr [:th "Height"] [:th "Hash"] [:th "Difficulty"] [:th "Miner"]  [:th {:class "time"}  "Time"] [:th "# Tx"]
                                           [:th "GasUsed"] [:th {:class "th-colspan" :colspan "2"} "Reward"] [:th {:class "th-colspan" :colspan "2"} "TxFee"]]
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
      ])))


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
