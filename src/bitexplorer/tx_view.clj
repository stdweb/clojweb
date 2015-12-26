(ns bitexplorer.tx-view
  (:use [hiccup core page] 
        bitexplorer.model 
        bitexplorer.html-utils
        bitexplorer.sharedview 
        bitexplorer.utils
        bitexplorer.txlist-view
        )
  
  (:require [clj-http.client :as http])
  (:require [clojure.string :as string])
  
  )


(defn get-tx-item-href [item-data]
  (let [
        account ( item-data "ADDRESS")
        offset ( item-data "OFFSETACCOUNT")
        tx ( item-data "TX")
        block (item-data "blockId")
       
        
        account-href (hash2href "account" account  )
        offset-href (hash2href "account" offset  )
        tx-href  (hash2href "tx" tx)
        block-href    [:a {:href (str "/block/" block)} block]
        
        ]
     (assoc item-data "ADDRESS" account-href "TX" tx-href "BLOCK" block-href "OFFSETACCOUNT" offset-href)
    ))

;(defn getAmountStyle [amount] 
;  (let [
;        isneg (= "-" (subs (str amount " ") 0 1))
;        style {:style (if isneg "text-align:right;color:red" "") }
;        ]
;    style
;    ;[:td style amount]
;                                 
;    ) )

(defn get-tx-item-html [item-data]
  (let [data (get-tx-item-href item-data)
        depth (parse-int (data "DEPTH"))
        ]
    [:tr ;(map-tag :td (select-values data [ "BLOCK" "BLOCKTIMESTAMP" "TX" "OFFSETACCOUNT"  "AMOUNT" "FEE" "ENTRYTYPE"   ]))
     
     [:td {:style "rowspan:2"} (data "BLOCK")]
     [:td (data "BLOCKTIMESTAMP")]
     
     [:td (if (zero? (or depth 0))  ( data "TX") "")]
     ;[:td depth]
     [:td (data "entryType")]
     [:td (td-style data "offset" ) (data "OFFSETACCOUNT")]
     
     [:td (td-style data "floor") (floor (data "RECEIVED"))]
     [:td (td-style data "fract") (fract (data "RECEIVED"))]
     [:td (td-style data "floor") (floor (data "SENT"))]
     [:td (td-style data "fract") (fract (data "SENT"))]
     
     [:td (data "depth")]
     
     ;[:td (data "FEE")]
     ;[:td (getAmountStyle (data "GROSSAMOUNT")) (data "GROSSAMOUNT")]
     [:td (td-style data "floor") (floor(data "BALANCE"))]
     [:td (td-style data "fract") (fract(data "BALANCE"))]
     [:td (data "gasUsed")]
     ])
  )

(defn create-tx-table2 [accid attribs]
  (list
;tx ,address ,amount ,block ,blocktimestamp,depth ,gasused ,fee ,entryType   
   [:div.blocklist  
    [:table
     [:tr 
      [:th "Block"] 
      [:th  "Date" ] 
      [:th "Tx"] 
      [:th "Entry type"] 
      [:th "Offset Acc"] 
      [:th {:class "th-colspan" :colspan "2"} "Received (Finney)"] 
      [:th {:class "th-colspan" :colspan "2"} "Sent (Finney)"]
     
      
      
      [:th "Depth"]
      
       ;[:th "Fee"] 
      
      ;[:th "GrossAmount"]
      [:th {:class "th-colspan" :colspan "2"} "Balance"]
      [:th "Gas used"]
      ]
      
                          (map #(get-tx-item-html %) (get-tx-data accid))    
                          ]]))


;(defn blocklist [topblockid]
;  (htmlpage
;   
;    [:body
;     
;      (create-block-table topblockid)
;      ]))

(defn tx-html [txid]
  ( let []
    (htmlpage 
     [:body 
     
      ;(map2htmltable (get-block-data blockid) {:class "blockheader"})
    
      (create-tx-item txid {:class "blockdetails"})
      ]
     )))