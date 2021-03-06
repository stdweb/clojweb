(ns bitexplorer.txlist-view
  (:use [hiccup core page] bitexplorer.model bitexplorer.utils 
        bitexplorer.sharedview bitexplorer.html-utils)
  
  (:require [clj-http.client :as http])
  (:require [clojure.string :as string])
  
  )

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn get-txlist-item-href [item-data]
  (let [
        ;depth (parse-int (item-data "depth"))
        depth (item-data "depth")
        txhash     (if (zero? depth)  ( item-data "hash_str") "")
        sender     ( item-data "addr_str")
        receiver ( item-data "offsetAddr_str")
        ;address ( item-data "address")
        
        txhash-href (hash2href "tx" txhash )
        sender-href (hash2href "account" sender)
        receiver-href (hash2href "account" receiver)
        
        ]
    item-data
     (assoc item-data "sender" sender-href "receiver" receiver-href  "hash" txhash-href)
     ;(assoc item-data "address" address-href "height" block-href "hash" hash-href "sent" sent "received" received)
     
    ))

(defn get-txlist-item-html [item-data]
  (let [
        data (get-txlist-item-href item-data)
        gasUsed (item-data "gasUsed")
        ]
    ;[:tr (map-tag :td (select-values data [ "hash" "DEPTH"   "sender" "receiver" "AMOUNT" "FEE" "GASUSED" "ENTRYTYPE"  ]))])
  
    [:tr
     [:td (td-style data "hash") (data "hash")]
     [:td (td-style data "entryType")(data "entryType")]
     [:td (td-style data "sender")(data "sender")]
     [:td (td-style data "receiver")(data "receiver")]
     [:td (td-style data "floor")(floor (data "amount_str"))]
     [:td (td-style data "fract") (fract (data "amount_str"))]
     [:td (td-style data "floor-no-red")(floor (data "fee_str"))]
     [:td (td-style data "fract-no-red")(fract (data "fee_str"))]
     [:td (if (zero? gasUsed) "" gasUsed)]
     ;[:td (data "GROSSAMOUNT")]
     ]))
    
    ;[:tr (map-tag :td (select-values data [ "hash" "txno"  "address" "received" "sent"  "gasused" "fee"]))]))

(defn create-tx-table [blockid attribs]
  (list
   
   [:h2 "block entries:"]
   [:table attribs
    ;[:tr  (map-tag :th [ "Hash" "Tx type" "Sender" "Receiver" "Value" "" "Fee" "GasUsed"  ])]
    [:tr
     [:th "Hash"] [:th {:class "txtype"} "Tx type"] [:th "Sender"] [:th "Receiver"] [:th {:class "th-colspan" :colspan "2"} "Amount"] 
     [:th {:class "th-colspan" :colspan "2"} "Fee"] [:th "Gas Used"] ;[:th "Gross"]
     ]
    
    ;[:tr [:th "Hash"] [:th "Tx#"]  [:th "Address"] [:th "Received"] [:th "Sent"] [:th "GasUsed"] [:th "Fee"]]
    
    (map #(get-txlist-item-html %) (get-txlist-data blockid)) 
    ]   
   )
  )

(defn create-tx-item [txid attribs]
  (let [data  (get-txitem-data txid)]
    (list
   
    [:table {:class "blockheader"} 
     [ :tr [:td {:class "blockheader-key"} "tx# "] [:td {:class "blockheader-key"} txid]]
    [ :tr [:td {:class "blockheader-key"} "Block: "] [:td {:class "blockheader-key"} ((first data) "blockId")]]
    [ :tr [:td {:class "blockheader-key"} "Time: "] [:td {:class "blockheader-key"} ((first data) "blockDateTime")]]]
    [:p]
   ; [:h2 (str "Time" (data "BLOCKTIMESTAMP"))]
    [:table attribs
     ;[:tr  (map-tag :th [ "Hash" "Tx type" "Sender" "Receiver" "Value" "" "Fee" "GasUsed"  ])]
     [:tr
      [:th "Hash"] [:th {:class "txtype"} "Tx type"] [:th "Sender"] [:th "Receiver"] [:th {:class "th-colspan" :colspan "2"} "Amount"] 
      [:th {:class "th-colspan" :colspan "2"} "Fee"] [:th "Gas Used"] ;[:th "Gross"]
      ]
    
     ;[:tr [:th "Hash"] [:th "Tx#"]  [:th "Address"] [:th "Received"] [:th "Sent"] [:th "GasUsed"] [:th "Fee"]]
    
     (map #(get-txlist-item-html %) data ) 
     ]   
    ))
  )

